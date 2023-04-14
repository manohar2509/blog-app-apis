package com.blog.blogappapis.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blog.blogappapis.services.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceimpl implements JWTService{
    public static final String SECRET = "48404D635166546A576E5A7234753778214125442A462D4A614E645267556B58";

    @Override
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
    private interface ClaimsResolver<T>{
        public T resolve(Claims claims);
    }
    @Override
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    @Override
    public Boolean validateToken(String token, String username){
        final String extractedUsername = extractUserName(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    
    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    @Override
    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    private Key getSignKey() {
        byte[] decodedKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
   

}
    

