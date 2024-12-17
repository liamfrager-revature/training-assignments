package com.liamfrager.connect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.service.AuthService;

/**
 * The REST controller that exposes the user endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AuthController {
    private AuthService authService;

    /**
     * Constructor for the user controller.
     */
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/users</code> <code>POST</code> endpoint.
     * @param user The body of the request containing the user to be registered.
     */
    @PostMapping("/auth/register")
    private ResponseEntity<User> register(@RequestBody User user) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    }

    /**
     * Handler for the <code>/users/login</code> <code>POST</code> endpoint.
     * @param user The body of the request containing the user to be logged in.
     */
    @PostMapping("/auth/login")
    private ResponseEntity<User> login(@RequestBody User user) throws InvalidLoginException {
        return ResponseEntity.ok(authService.login(user));
    }


    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

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
