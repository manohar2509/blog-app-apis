package com.blog.blogappapis.services;

import java.util.Date;
import java.util.Map;

public interface JWTService {

    String generateToken(String username);

    String createToken(Map<String,Object> claims, String username);

    String extractUserName(String token);

    Date extractExpiration(String token);

    Boolean isTokenExpired(String token);

    Boolean validateToken(String token, String username);

    



    
}
