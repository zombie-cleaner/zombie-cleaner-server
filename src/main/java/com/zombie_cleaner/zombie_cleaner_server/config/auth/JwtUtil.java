package com.zombie_cleaner.zombie_cleaner_server.config.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static String SECRET;
    private static int TOKEN_EXPIRATION_HOURS;
    private static Key key;

    @Value("${auth.security.exipration.hours}")
    public void setTokenExpirationHours(int expirationHours){
        JwtUtil.TOKEN_EXPIRATION_HOURS = expirationHours;
    }

    @Value("${auth.security.secret.key}")
    public void setSecret(String secret){
        JwtUtil.SECRET = secret;
        JwtUtil.key = Keys.hmacShaKeyFor(JwtUtil.SECRET.getBytes());
    }

//    generate token given the email
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (TOKEN_EXPIRATION_HOURS * 3600 * 1000)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

//    extract email given the token
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
