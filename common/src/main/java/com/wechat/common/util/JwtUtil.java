package com.wechat.common.util;


import com.wechat.common.enums.Role;
import io.jsonwebtoken.*;



import java.util.Date;

import static java.security.KeyRep.Type.SECRET;

public class JwtUtil {

    private static final String SECRET_KEY = "my-secret-key";
    private static final long EXPIRATION = 3600_000; // 1 小时

    // 生成 Token
    public static String generateToken(String username, String role, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1小时
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 注意参数顺序
                .compact();
    }




    // 解析 Token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}
