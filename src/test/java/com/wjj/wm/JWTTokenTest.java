package com.wjj.wm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wjj.wm.pojo.QkUser;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JWTTokenTest
 * @Description TODO
 * @Author weng_jun_ji_
 * @Date 2019/9/29 14:33
 * @Vervsion 1.0
 */
public class JWTTokenTest {

    //生成token
    private static String getToken(QkUser qkUser) {
        //签发时间
        Date atDate = new Date();
        //过期时间 1分钟
        Calendar nowTime= Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 1);
        Date expireTime = nowTime.getTime();

        String id = qkUser.getId();
        String username = qkUser.getUsername();
        String password = qkUser.getPassword();
        //使用HMAC256生成token, 密钥是用户的密码,唯一密钥存到服务端
        return JWT.create().withAudience(id, username).withExpiresAt(expireTime).withIssuedAt(atDate).sign(Algorithm.HMAC256(password));
    }

    @Test
    public void main() {
        System.out.println("===============================生成token===============================");
        QkUser user = new QkUser();
        user.setId("4313412431");
        user.setPassword("as32j1hfl324lls");
        user.setUsername("ab1231312df1");
        String token = getToken(user);
        System.out.println(token);

        System.out.println("===============================校验token===============================");
        DecodedJWT decode = JWT.decode(token);
        List<String> audience = decode.getAudience();
        String userId = audience.get(0);
        String username = audience.get(1);
        System.out.println(userId);
        System.out.println(username);

        System.out.println(audience);
        String payload = decode.getPayload();
        System.out.println(payload);
        Map<String, Claim> claims = decode.getClaims();
        System.out.println(claims);

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        DecodedJWT verify = verifier.verify(token);

    }
}
