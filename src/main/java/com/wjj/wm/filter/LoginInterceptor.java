package com.wjj.wm.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wjj.wm.common.annotation.PassToken;
import com.wjj.wm.common.annotation.UserLoginToken;
import com.wjj.wm.common.utils.QkException;
import com.wjj.wm.pojo.QkUser;
import com.wjj.wm.service.QkUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ClassName LoginInterceptor
 * @Description Login用户权限拦截器
 * @Author weng_jun_ji_
 * @Date 2019/9/26 11:28
 * @Vervsion 1.0
 */
@Component
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private QkUserService qkUserService;

    /**
     * @Description: 预处理回调, 实现拦截逻辑
     * @Date&Author 2019/9/26 by weng_jun_ji_
     * @Param [request, response, handler 响应的处理器,即Controller]
     * @Return
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url = request.getRequestURI();
        log.info("url: {}", url);

        //其他路径->token验证
        String token = request.getHeader("token");
        //如果不是映射到方法上则放行
        if (!(handler instanceof HandlerMethod)) return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) return true;  //默认为true
        }

        //检查是否有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            //获取UserLoginToken注解
            UserLoginToken tokenAnnotation = method.getAnnotation(UserLoginToken.class);
            if (tokenAnnotation.required()) {
                /**
                 * 认证
                 **/
                if (StringUtils.isEmpty(token)){
                    throw new QkException("token is empty");
                }
                //获取token中的id
                String userId;
                try {
                    DecodedJWT decode = JWT.decode(token);
                    userId = decode.getAudience().get(0);
                } catch (JWTDecodeException jex) {
                    log.error(jex.getMessage(), jex);
                    throw new QkException("authentication failed");
                }
                //检查用户是否存在
                QkUser user = qkUserService.selectById(userId);
                if (null == user) throw new RuntimeException("user doesn't exist");
                //验证token
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    verifier.verify(token);
                } catch (JWTVerificationException jvex) {
                    log.error(jvex.getMessage(), jvex);
                    throw new QkException("token expired");
                }
                return true;
            }
        }

        return true;
    }
}
