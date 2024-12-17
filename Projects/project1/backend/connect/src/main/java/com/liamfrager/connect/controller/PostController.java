package com.liamfrager.connect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.Post;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidPostContentException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.service.PostService;
import com.liamfrager.connect.repository.UserRepository;

/**
 * The REST controller that exposes the post endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class PostController {
    private PostService postService;

    /**
     * Constructor for the post controller.
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/posts</code> <code>POST</code> endpoint.
     * @param post The body of the request containing the post data to be added.
     * @throws InvalidUserException 
     * @throws InvalidPostContentException 
     */
    @PostMapping("/posts")
    private ResponseEntity<Post> postPost(@RequestBody Post post) throws InvalidPostContentException, InvalidUserException {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.postPost(post));
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
    private ResponseEntity<Post> getPostByID(@PathVariable long id) {
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
    private ResponseEntity<Integer> deletePostByID(@PathVariable long id) {
        int deletedRows = postService.deletePostByID(id);
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>PATCH</code> endpoint.
     * @param id The ID of the post to be updated.
     * @param post The body of the request containing the post data to be updated.
     * @throws InvalidPostIDException 
     * @throws InvalidPostContentException 
     */
    @PatchMapping("/posts/{id}")
    private ResponseEntity<Integer> patchPostByID(@PathVariable long id, @RequestBody Post post) throws InvalidPostContentException, InvalidPostIDException {
        return ResponseEntity.ok(postService.patchPostByID(id, post));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidPostContentException</code>,
     * <code>InvalidPostIDException</code>,
     * <code>InvalidUserException</code>.
     */
    @ExceptionHandler({
        InvalidPostContentException.class,
        InvalidPostIDException.class,
        InvalidUserException.class,
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
