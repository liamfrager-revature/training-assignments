package com.liamfrager.connect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.AuthUtil;
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
    @GetMapping("/users/{userID}/following")
    private ResponseEntity<List<User>> getAllFollowingByUserID(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getAllFollowingByUserID(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose followers will be returned.
     */
    @GetMapping("/users/{userID}/followers")
    private ResponseEntity<List<User>> getAllFollowersByUserID(@PathVariable long userID) throws InvalidUserException {
        return ResponseEntity.ok(userService.getAllFollowersByUserID(userID));
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>GET</code> endpoint.
     * @param userID The ID of the user of whose followers will be returned.
     */
    @GetMapping("/users/{userID}/follow")
    private ResponseEntity<Boolean> isFollowing(@RequestHeader("Authorization") String token, @PathVariable long userID) {
        return ResponseEntity.ok(userService.isFollowing(AuthUtil.extractID(token), userID));
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>GET</code> endpoint.
     * @param userID The ID of the user to follow.
     */
    @PostMapping("/users/{userID}/follow")
    private ResponseEntity<Void> followUser(@RequestHeader("Authorization") String token, @PathVariable long userID) throws InvalidUserException, InvalidFollowException {
        userService.followUser(AuthUtil.extractID(token), userID);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/users/{userID}/followers</code> <code>DELETE</code> endpoint.
     * @param userID The ID of the user to unfollow.
     */
    @DeleteMapping("/users/{userID}/follow")
    private ResponseEntity<Void> unfollowUser(@RequestHeader("Authorization") String token, @PathVariable long userID) throws InvalidUserException, InvalidFollowException {
        userService.unfollowUser(AuthUtil.extractID(token), userID);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/users/pfp</code> <code>PUT</code> endpoint.
     */
    @PutMapping("/users/pfp")
    private ResponseEntity<Void> setPfp(@RequestHeader("Authorization") String token, @RequestBody byte[] pfp) throws Exception {
        userService.setPfp(AuthUtil.extractID(token), pfp);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/users</code> <code>PUT</code> endpoint.
     */
    @PutMapping("/users")
    private ResponseEntity<Integer> updateUser(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) throws UserAlreadyExistsException {
        return ResponseEntity.ok(userService.updateUser(AuthUtil.extractID(token), updatedUser));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidUserException</code>,
     * <code>InvalidFollowException</code>.
     */
    @ExceptionHandler({
        InvalidUserException.class,
        InvalidFollowException.class
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
