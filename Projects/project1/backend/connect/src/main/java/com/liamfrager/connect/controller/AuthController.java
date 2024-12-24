package com.liamfrager.connect.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.service.AuthService;

/**
 * The REST controller that exposes the auth endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AuthController {
    private AuthService authService;

    /**
     * Constructor for the auth controller.
     */
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * Handler for the <code>/auth/register</code> <code>POST</code> endpoint.
     */
    @PostMapping("/auth/register")
    private ResponseEntity<User> register(@RequestBody User user) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        User registeredUser = authService.register(user);
        String token = AuthUtil.generateToken(registeredUser.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(registeredUser);
    }

    /**
     * Handler for the <code>/auth/login</code> <code>POST</code> endpoint.
     */
    @PostMapping("/auth/login")
    private ResponseEntity<User> login(@RequestBody User user) throws InvalidLoginException {
        User authenticatedUser = authService.login(user);
        String token = AuthUtil.generateToken(authenticatedUser.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(authenticatedUser);
    }

    /**
     * Handler for the <code>/auth</code> <code>GET</code> endpoint.
     */
    @GetMapping("/auth")
    private ResponseEntity<Boolean> isValidToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(AuthUtil.isValidToken(token));
    }

    /**
     * <code>401 Unauthorized</code>.
     * Exception handler for: <code>InvalidLoginException</code>.
     */
    @ExceptionHandler(InvalidLoginException.class)
    private ResponseEntity<Exception> invalidLoginExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidUserException</code>,
     * <code>InvalidUsernameException</code>,
     * <code>InvalidPasswordException</code>.
     */
    @ExceptionHandler({
        InvalidUserException.class,
        InvalidUsernameException.class,
        InvalidPasswordException.class
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }

    /**
     * <code>409 Conflict</code>.
     * Exception handler for: <code>UserAlreadyExistsException</code>.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<Exception> userAlreadyExistsExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
    }
}
