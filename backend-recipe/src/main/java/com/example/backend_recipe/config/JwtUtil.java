package com.example.backend_recipe.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Key getSigningKey(){return SECRET_KEY;}

    public String generateToken(String email, Long customerId){
        long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
        return Jwts.builder()
                .setSubject(email)
                .claim("customerId", customerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long extractCustomerId(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return  claims.get("customerId", Long.class);
    }

    public Date extractExpiration(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
