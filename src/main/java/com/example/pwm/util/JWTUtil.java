package com.example.pwm.util;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {
    
    private static String key = "1234567890123456789012345678901234567890";
    
    public static String generateToken(Map<String, Object> valueMap, int min){
        SecretKey key = null;
        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

        String jwtStr = Jwts.builder()
        .setHeader(Map.of("typ", "JWT"))
        .setClaims(valueMap)
        .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
        .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
        .signWith(key)
        .compact();

        return jwtStr;
    }

    public static Map<String, Object> validateToken(String Token){
        Map<String, Object> claim = null;

        try{
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTFF-8"));

            claim = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(Token)
            .getBody();
        }catch( MalformedJwtException malformedJwtException){
            throw new CustomJWTException("Malformed");
        }catch( ExpiredJwtException expiredJwtException){
            throw new CustomJWTException("Expired");
        }catch(InvalidClaimException exClaimException){
            throw new CustomJWTException("Invalid");
        }catch(JwtException jwtException ){
            throw new CustomJWTException("JWTError");
        }catch(Exception e){
            throw new CustomJWTException("Error");
        }
        return claim;
    }
}
