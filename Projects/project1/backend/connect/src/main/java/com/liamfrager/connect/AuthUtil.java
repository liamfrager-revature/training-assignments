package com.liamfrager.connect;

import java.security.Key;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.repository.UserRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class AuthUtil implements ApplicationContextAware {

    private static UserRepository userRepository;

    @SuppressWarnings("unused")
    @Autowired
    private ApplicationContext applicationContext;

    // Set the userRepository after the application context is set
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        userRepository = applicationContext.getBean(UserRepository.class);
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(Long userID) {
        return Jwts.builder()
                .setSubject(Long.toString(userID))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }

    public static Long extractID(String token) {
        return Long.parseLong(extractClaims(token).getSubject());
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token, Long id) {
        return id.equals(extractID(token)) && !isTokenExpired(token);
    }

    private static Claims extractClaims(String token) {
        if (token.startsWith("Bearer "))
            token = token.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static User getUserFromToken(String token) throws InvalidUserException {
        Long userID = extractID(token);
        return userRepository.findById(userID).orElseThrow(() -> new InvalidUserException(userID));
    }

    public static Boolean isValidToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (SignatureException ex) {
            return false;
        }
    }
}
