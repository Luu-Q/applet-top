package com.applet.zuul.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * Created by luning on 2020-03-21
 */
public class JWTUtiles {

    private static String secret = "mysecret";

    /**
     * 生成加密后的token
     *
     * @return 加密后的token
     */
    public static String encryptToken(final String time) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + 1000L);//24L * 60L * 3600L *
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("time", time)
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    // mysecret是用来加密数字签名的密钥。
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

    /**
     * 先验证token是否被伪造，然后解码token。
     *
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public static DecodedJWT decryptToken(final String token) {
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC256加密算法。
            // mysecret是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwt;
    }

    public static void main(String[] args) {
        // 生成token
        String token = encryptToken("张超");
        // 打印token
        System.out.println("token: " + token);

        // 解密token
        DecodedJWT jwt= decryptToken(token);
        System.out.println("issuer: " + jwt.getIssuer());
        System.out.println("username: " + jwt.getClaim("time").asString());
        System.out.println("过期时间：      " + jwt.getExpiresAt());

    }
}
