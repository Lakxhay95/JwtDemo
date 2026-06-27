package com.jfs.JWTDemo.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY_STRING = "Wildcraft#1234@djfnwkdcspojdcawopdjkmxcsdfjbwel.fnlisuerbgiulasd";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    public String generateToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 mins
                .signWith(SECRET_KEY)
                .compact();
        System.out.println("Generate Token: " + token);

        // sample token -
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4MjU0Nzk5NiwiZXhwIjoxNzgyNTQ5Nzk2fQ.HLvQm77P-GhSPn46fgBsFEuZLlsj2XVN_-cFnM5473QurLSrFdI--6kTbhpsZvIAd-tsP6J6uap6HJXNZIKSRg
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired " + e.getMessage());
            return false;
        } catch (SignatureException s) {
            System.out.println("Signature Mismatch " + s.getMessage());
            return false;
        } catch(Exception e) {
            System.out.println("Token not validated " + e.getMessage());
            return false;
        }
    }
}
