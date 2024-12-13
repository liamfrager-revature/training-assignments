package com.liamfrager.connect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liamfrager.connect.entity.Comment;
import com.liamfrager.connect.entity.User;
import com.liamfrager.connect.exception.InvalidCommentContentException;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.service.CommentService;

/**
 * The REST controller that exposes the comment endpoints for the API.
 */
@RestController
public class CommentController {
    private CommentService commentService;

    /**
     * Constructor for the comment controller.
     */
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/posts/{id}/comments</code> <code>POST</code> endpoint.
     * @param comment The body of the request containing the comment data to be added.
     * @throws InvalidUserException 
     * @throws InvalidCommentContentException 
     */
    @PostMapping("/posts/{id}/comments")
    private ResponseEntity<Comment> postComment(@RequestBody Comment comment, @PathVariable long id) throws InvalidCommentContentException, InvalidUserException {
        return ResponseEntity.ok(commentService.postComment(comment));
    }

    /**
     * Handler for the <code>/posts/{id}/comments</code> <code>GET</code> endpoint.
     */
    @GetMapping("/posts/{id}/comments")
    private ResponseEntity<List<Comment>> getAllComments(@PathVariable long id) {
        return ResponseEntity.ok(commentService.getAllCommentsByPostID(id));
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>DELETE</code> endpoint.
     * @param id The ID of the comment to be deleted.
     */
    @DeleteMapping("/comments/{id}")
    private ResponseEntity<Integer> deleteCommentByID(@PathVariable long id) {
        int deletedRows = commentService.deleteCommentByID(id);
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    /**
     * Handler for the <code>/posts/{id}</code> <code>PATCH</code> endpoint.
     * @param id The ID of the comment to be updated.
     * @param comment The body of the request containing the comment data to be updated.
     * @throws InvalidCommentIDException 
     * @throws InvalidCommentContentException 
     */
    @PatchMapping("/comments/{id}")
    private ResponseEntity<Integer> patchCommentByID(@PathVariable long id, @RequestBody Comment comment) throws InvalidCommentContentException, InvalidCommentIDException {
        return ResponseEntity.ok(commentService.patchCommentByID(id, comment));
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidCommentContentException</code>,
     * <code>InvalidCommentIDException</code>,
     * <code>InvalidUserException</code>.
     */
    @ExceptionHandler({
        InvalidCommentContentException.class,
        InvalidCommentIDException.class,
        InvalidUserException.class,
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
