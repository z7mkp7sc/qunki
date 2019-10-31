package com.wjj.wm.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * @ClassName MD5Util
 * @Description MD5加解密工具
 * @Author weng_jun_ji_
 * @Date 2019/9/27 15:19
 * @Vervsion 1.0
 */
@Slf4j
public class MD5Util {

    public static final  String KEY = "qk2019";

    /**
     * @Description:md5加密
     * @Date&Author 2019/9/27 by weng_jun_ji_
     * @Param [text 需要加密的明文, key]
     * @Return 加密后的密文
     **/
    public static String md5Encode(String text, String key) {
        return DigestUtils.md5Hex(text + key);
    }

    //带秘钥的md5验证,用于登录密码验证
    public static boolean md5verify(String text, String key, String encodeContent) {
        return md5Encode(text, key).equals(encodeContent);
    }


}
