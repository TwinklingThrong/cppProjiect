package com.cpp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class LingPai {

    // 使用与测试类中一致的秘钥
    private static final String SECRET_KEY = "aXRoZWltYQ==";
    
    // 令牌过期时间，12小时
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000; // 12 hours in milliseconds

    /**
     * 生成令牌.
     *
     * @param dataMap 包含在令牌中的用户信息
     * @return 生成的JWT令牌
     */
    public static String generateToken(Map<String, Object> dataMap) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(dataMap)
                .setExpiration(expiryDate)
                .compact();
    }

    /**
     * 解析令牌.
     *
     * @param token 要解析的JWT令牌
     * @return 包含令牌声明的Claims对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}