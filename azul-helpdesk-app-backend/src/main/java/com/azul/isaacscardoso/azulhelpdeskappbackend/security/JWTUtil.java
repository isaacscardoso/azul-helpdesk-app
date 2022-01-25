package com.azul.isaacscardoso.azulhelpdeskappbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + this.expirationTime))
                .signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date currentTime = new Date(System.currentTimeMillis());
            return username != null && expirationDate != null && currentTime.before(expirationDate);
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
