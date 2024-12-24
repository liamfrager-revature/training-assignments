package com.liamfrager.connect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.repository.PostRepository;
import com.liamfrager.connect.service.CommentService;

/**
 * The REST controller that exposes the comment endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class CommentController {
    private final CommentService commentService;
    private final PostRepository postRepository;

    /**
     * Constructor for the comment controller.
     */
    public CommentController(CommentService commentService, PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    /**
     * Handler for the <code>/posts/{id}/comments</code> <code>POST</code> endpoint.
     */
    @PostMapping("/posts/{id}/comments")
    private ResponseEntity<Comment> postComment(@RequestHeader("Authorization") String token, @RequestBody Comment comment, @PathVariable long id) throws InvalidCommentContentException, InvalidUserException, InvalidPostIDException {
        comment.setUser(AuthUtil.getUserFromToken(token));
        comment.setPost(postRepository.findById(comment.getPost().getId()).orElseThrow(() -> new InvalidPostIDException(comment.getPost().getId())));
        return ResponseEntity.ok(commentService.postComment(comment));
    }

    /**
     * Handler for the <code>/posts/{id}/comments</code> <code>GET</code> endpoint.
     */
    @GetMapping("/posts/{id}/comments")
    private ResponseEntity<List<Comment>> getAllComments(@RequestHeader("Authorization") String token, @PathVariable long id) {
        return ResponseEntity.ok(commentService.getAllCommentsByPostID(id, AuthUtil.extractID(token)));
    }

    /**
     * Handler for the <code>/comments/{id}</code> <code>DELETE</code> endpoint.
     */
    @DeleteMapping("/comments/{id}")
    private ResponseEntity<Integer> deleteCommentByID(@PathVariable long id) {
        int deletedRows = commentService.deleteCommentByID(id);
        return ResponseEntity.ok(deletedRows);
    }

    /**
     * Handler for the <code>/comments/{id}</code> <code>Put</code> endpoint.
     */
    @PutMapping("/comments/{id}")
    private ResponseEntity<Integer> updateCommentByID(@PathVariable long id, @RequestBody Comment comment) throws InvalidCommentContentException, InvalidCommentIDException {
        return ResponseEntity.ok(commentService.updateCommentByID(id, comment));
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidCommentContentException</code>,
     * <code>InvalidCommentIDException</code>,
     * <code>InvalidPostIDException</code>,
     * <code>InvalidUserException</code>.
     */
    @ExceptionHandler({
        InvalidCommentContentException.class,
        InvalidCommentIDException.class,
        InvalidPostIDException.class,
        InvalidUserException.class,
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
