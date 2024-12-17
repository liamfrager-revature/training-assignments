package com.liamfrager.connect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.entity.Like;
import com.liamfrager.connect.dto.LikeDTO;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.service.LikeService;

/**
 * The REST controller that exposes the like endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class LikeController {
    private LikeService likeService;

    /**
     * Constructor for the post controller.
     */
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    // --------------
    // ROUTE HANDLERS
    // --------------

    /**
     * Handler for the <code>/likes</code> <code>POST</code> endpoint.
     * @param post The body of the request containing the like data to be added.
     * @throws InvalidUserException 
     * @throws InvalidLikeContentException 
     */
    @PostMapping("/likes")
    private ResponseEntity<Like> postLike(@RequestBody Like like) throws InvalidLikeException {
        return ResponseEntity.ok(likeService.postLike(like));
    }

    /**
     * Handler for the <code>/post/{postID}/likes</code> <code>GET</code> endpoint.
     * @param postID The ID of the post whose like data will be returned.
     * @throws InvalidPostIDException
     */
    @GetMapping("/post/{postID}/likes")
    private ResponseEntity<LikeDTO> getPostLikes(@PathVariable long postID) throws InvalidPostIDException {
        return ResponseEntity.ok(likeService.getLikesFromPost(postID));
    }

    /**
     * Handler for the <code>/comment/{commentID}/likes</code> <code>GET</code> endpoint.
     * @param commentID The ID of the comment whose like data will be returned.
     * @throws InvalidPostIDException
     */
    @GetMapping("/comment/{commentID}/likes")
    private ResponseEntity<LikeDTO> getCommentLikes(@PathVariable long commentID) throws InvalidPostIDException {
        return ResponseEntity.ok(likeService.getLikesFromComment(commentID));
    }

     /**
     * Handler for the <code>/likes/{postID}/{userID</code> <code>DELETE</code> endpoint.
     * @param id The ID of the like to be deleted.
     */
    @DeleteMapping("/likes/{id}")
    private ResponseEntity<Integer> deleteLike(@PathVariable long id) throws InvalidLikeException {
        int deletedRows = likeService.deleteLike(id);
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    // ------------------
    // EXCEPTION HANDLERS
    // ------------------

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidPostIDException</code>,
     * <code>InvalidLikeException</code>.
     */
    @ExceptionHandler({
        InvalidLikeException.class,
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }
}
