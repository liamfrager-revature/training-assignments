package com.liamfrager.connect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.Account;
import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.exception.*;
import com.liamfrager.connect.service.AccountService;
import com.liamfrager.connect.service.PostService;

/**
 * The REST controller that exposes the endpoints for the social media API.
 */
@RestController
public class APIController {
    private PostService postService;
    private AccountService accountService;

    /**
     * Constructor for the social media controller.
     */
    public APIController(PostService postService, AccountService accountService){
        this.postService = postService;
        this.accountService = accountService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/register</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be registered.
     */
    @PostMapping("/register")
    private ResponseEntity<Account> register(@RequestBody Account account) throws InvalidUsernameException, InvalidPasswordException, UserAlreadyExistsException {
        return ResponseEntity.ok(accountService.register(account));
    }

    /**
     * Handler for the <code>/login</code> <code>POST</code> endpoint.
     * @param account The body of the request containing the account to be logged in.
     */
    @PostMapping("/login")
    private ResponseEntity<Account> login(@RequestBody Account account) throws InvalidLoginException {
        return ResponseEntity.ok(accountService.login(account));
    }

    /**
     * Handler for the <code>/posts</code> <code>POST</code> endpoint.
     * @param post The body of the request containing the post data to be added.
     */
    @PostMapping("/posts")
    private ResponseEntity<Post> postPost(@RequestBody Post post) throws InvalidPostTextException , InvalidUserIDException {
        return ResponseEntity.ok(postService.postPost(post));
    }

    /**
     * Handler for the <code>/posts</code> <code>GET</code> endpoint.
     */
    @GetMapping("/posts")
    private ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>GET</code> endpoint.
     * @param id The ID of the post to be returned.
     */
    @GetMapping("/posts/{id}")
    private ResponseEntity<Post> getPostByID(@PathVariable int id) {
        Post post = postService.getPostByID(id);
        if (post == null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.ok(post);
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>DELETE</code> endpoint.
     * @param id The ID of the post to be deleted.
     */
    @DeleteMapping("/posts/{id}")
    private ResponseEntity<Integer> deletePostByID(@PathVariable int id) {
        int deletedRows = postService.deletePostByID(id);
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>PATCH</code> endpoint.
     * @param id The ID of the post to be updated.
     * @param post The body of the request containing the post data to be updated.
     */
    @PatchMapping("/posts/{id}")
    private ResponseEntity<Integer> patchPostByID(@PathVariable int id, @RequestBody Post post) throws InvalidPostTextException, InvalidPostIDException {
        return ResponseEntity.ok(postService.patchPostByID(id, post));
    }

    /**
     * Handler for the <code>/accounts/{account_id}/posts</code> <code>GET</code> endpoint.
     * @param account_id The ID of the account of whose messsages will be returned.
     */
    @GetMapping("/accounts/{account_id}/posts")
    private ResponseEntity<List<Post>> getAllPostsByAccountID(@PathVariable int account_id) {
        return ResponseEntity.ok(postService.getAllPostsByAccountID(account_id));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>401 Unauthorized</code>.
     * Exception handler for: <code>InvalidLoginException</code>.
     */
    @ExceptionHandler(InvalidLoginException.class)
    private ResponseEntity<Void> invalidLoginExceptionHandler() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidPostTextException</code>,
     * <code>InvalidPostIDException</code>,
     * <code>InvalidUserIDException</code>,
     * <code>InvalidUsernameException</code>,
     * <code>InvalidPasswordException</code>.
     */
    @ExceptionHandler({
        InvalidPostTextException.class,
        InvalidPostIDException.class,
        InvalidUserIDException.class,
        InvalidUsernameException.class,
        InvalidPasswordException.class
    })
    private ResponseEntity<Void> badRequestExceptionHandler() {
        return ResponseEntity.badRequest().build();
    }

    /**
     * <code>409 Conflict</code>.
     * Exception handler for: <code>UserAlreadyExistsException</code>.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<Void> userAlreadyExistsExceptionHandler() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
