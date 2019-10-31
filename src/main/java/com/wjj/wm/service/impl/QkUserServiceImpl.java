package com.wjj.wm.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.wjj.wm.common.utils.AESUtil;
import com.wjj.wm.common.utils.MD5Util;
import com.wjj.wm.common.utils.QkException;
import com.wjj.wm.common.utils.SnowFlakeIdWorker;
import com.wjj.wm.mapper.QkUserMapper;
import com.wjj.wm.mapper.QkUserOrganizationMapper;
import com.wjj.wm.pojo.QkUser;
import com.wjj.wm.service.QkUserService;
import com.wjj.wm.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName QkUserServiceImpl
 * @Description
 * @Author weng_jun_ji_
 * @Date 2019/9/26 18:16
 * @Vervsion 1.0
 */
@Service
@Slf4j
public class QkUserServiceImpl implements QkUserService {

    @Autowired
    private SnowFlakeIdWorker idWorker;
    @Autowired
    private QkUserMapper qkUserMapper;
    @Autowired
    private QkUserOrganizationMapper qkUserOrganizationMapper;

    //注册用户
    @Override
    public void register(QkUser user) {
        String organizationId = user.getOrganizationId();
        String username = user.getUsername();
        String password = user.getPassword();
        //校验合法性(略)
        if (qkUserOrganizationMapper.selectByOid(organizationId) < 1) throw new QkException("找不到该组织");
        if (qkUserMapper.selectByUsername(username) != null) throw new QkException("用户名已存在");

        //密码加密
        //AES
        String AES = AESUtil.AESEncode(password);
        //MD5
        String AES_MD5 = MD5Util.md5Encode(AES, MD5Util.KEY);
        user.setPassword(AES_MD5);

        user.setId(String.valueOf(idWorker.nextId()));
        user.setLoginName("当前登录账号");
        user.setCreator("当前登录的用户ID");
        user.setCreateTime(new Date());
        qkUserMapper.insert(user);
    }

    //登录
    @Override
    public LoginVo login(QkUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new QkException("username or password is null");
        //校验用户名
        QkUser qkUser = qkUserMapper.selectByUsername(username);
        if (null == qkUser) throw new QkException("用户不存在");
        //校验密码
        String aes = AESUtil.AESEncode(password);
        if (aes == null) throw new QkException("密码验证失败");
        String encodeContent = qkUser.getPassword();
        if (!MD5Util.md5verify(aes, MD5Util.KEY, encodeContent)) throw new QkException("密码错误");
        //用户名密码验证后 -> 生成token
        String token = getToken(qkUser);
        return new LoginVo(token, username);
    }

    @Override
    public QkUser selectById(String userId) {
        return qkUserMapper.selectById(userId);
    }

    //生成token
    private String getToken(QkUser qkUser) {
        //签发时间
        Date atDate = new Date();
        //过期时间 30分钟
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);
        Date expireTime = nowTime.getTime();

        String token;
        try {
            String id = qkUser.getId();
            String username = qkUser.getUsername();
            String password = qkUser.getPassword();
            //使用HMAC256生成token, 密钥是用户的密码,唯一密钥存到服务端
            token = JWT.create().withAudience(id, username).withExpiresAt(expireTime).withIssuedAt(atDate).sign(Algorithm.HMAC256(password));
            return token;
        } catch (IllegalArgumentException | JWTCreationException e) {
            log.error(e.getMessage(), e);
            throw new QkException(e.getMessage());
        }
    }

}
