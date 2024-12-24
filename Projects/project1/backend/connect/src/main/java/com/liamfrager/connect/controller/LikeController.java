package com.liamfrager.connect.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.liamfrager.connect.AuthUtil;
import com.liamfrager.connect.exception.InvalidCommentIDException;
import com.liamfrager.connect.exception.InvalidLikeException;
import com.liamfrager.connect.exception.InvalidPostIDException;
import com.liamfrager.connect.exception.InvalidUserException;
import com.liamfrager.connect.exception.UnauthorizedUserException;
import com.liamfrager.connect.service.LikeService;

/**
 * The REST controller that exposes the like endpoints for the API.
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class LikeController {
    private LikeService likeService;

    /**
     * Constructor for the like controller.
     */
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    /**
     * Handler for the <code>/likes</code> <code>POST</code> endpoint.
     */
    @PostMapping("/likes")
    private ResponseEntity<Long> postLike(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> likeRequest) throws InvalidLikeException, InvalidPostIDException, InvalidCommentIDException, InvalidUserException {
        return ResponseEntity.ok(likeService.postLike(likeRequest, AuthUtil.getUserFromToken(token)).getId());
    }

     /**
     * Handler for the <code>/likes/{id}</code> <code>DELETE</code> endpoint.
     */
    @DeleteMapping("/likes/{id}")
    private ResponseEntity<Integer> deleteLike(@RequestHeader("Authorization") String token, @PathVariable long id) throws InvalidLikeException, UnauthorizedUserException {
        int deletedRows = likeService.deleteLike(id, AuthUtil.extractID(token));
        if (deletedRows > 0)
            return ResponseEntity.ok(deletedRows);
        return ResponseEntity.ok().build();
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>InvalidPostIDException</code>,
     * <code>InvalidCommentIDException</code>,
     * <code>InvalidLikeException</code>.
     */
    @ExceptionHandler({
        InvalidPostIDException.class,
        InvalidCommentIDException.class,
        InvalidLikeException.class,
    })
    private ResponseEntity<Exception> badRequestExceptionHandler(Exception ex) {
        return ResponseEntity.badRequest().body(ex);
    }

    /**
     * <code>400 Bad Request</code>.
     * Exception handler for:
     * <code>UnauthorizedUserException</code>.
     */
    @ExceptionHandler({
        UnauthorizedUserException.class
    })
    private ResponseEntity<Exception> unauthorizedExcpetionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex);
    }
}
