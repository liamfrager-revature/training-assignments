package com.liamfrager.connect.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.service.UserService;
import com.liamfrager.connect.service.PostService;

/**
 * The REST controller that exposes the user endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class UserController {
    private PostService postService;
    private UserService userService;

    /**
     * Constructor for the user controller.
     */
    public UserController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/login</code> <code>GET</code> endpoint.
     * @param userID The ID of the user whose data will be returned.
     */
    @GetMapping("/users/{userID}")
    private ResponseEntity<User> getUser(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getUser(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/posts</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose messsages will be returned.
     */
    @GetMapping("/users/{userID}/posts")
    private ResponseEntity<List<Post>> getAllPostsByUserID(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(postService.getAllPostsByUserID(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/friends</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose friends will be returned.
     */
    @GetMapping("/users/{userID}/friends")
    private ResponseEntity<Set<User>> getAllFriendsByUserID(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getAllFriendsByUserID(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose followers will be returned.
     */
    @GetMapping("/users/{userID}/followers")
    private ResponseEntity<Set<User>> getAllFollowersByUserID(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getAllFollowersByUserID(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose followers will be returned.
     */
    @PostMapping("/users/{userID}/follow")
    private ResponseEntity<Set<User>> followUser(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getAllFollowersByUserID(userID));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidUserException</code>.
     */
    @ExceptionHandler({
        InvalidUserException.class
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
