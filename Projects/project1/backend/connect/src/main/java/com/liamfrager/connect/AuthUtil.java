package com.liamfrager.connect;

import java.security.Key;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

public class AuthUtil {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set subject
                .setIssuedAt(new Date()) // Issued at
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expires in 10 hours
                .signWith(key) // Use HMAC-SHA key
                .compact();
    }

    // Extract the username (subject) from the token
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if the token is expired
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Validate the token
    public static boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // Extract claims from a token
    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
