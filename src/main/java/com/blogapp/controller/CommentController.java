package com.blogapp.controller;

import com.blogapp.payload.CommentDto;
import com.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    //http://localhost:8080/api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> newComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        CommentDto returnCommentDto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(returnCommentDto, HttpStatus.CREATED);
    }

        //http://localhost:8080/api/post/{postId}/comments/{id}
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> retriveComment(@PathVariable("postId") Long postId , @PathVariable("commentId") Long commentId){
        CommentDto readCommentDto = commentService.readComment(postId, commentId);
        return new ResponseEntity<>(readCommentDto, HttpStatus.OK);
    }

    // http://localhost:8080/api/post/{postId}/comments/{commentId}
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        commentService.removeCommet(postId, commentId);
        return new ResponseEntity<>("Comment delete successfully", HttpStatus.OK);
    }

    // http://localhost:8080/api/post/{postId}/comments/{commentId}
    @PutMapping("post/{postId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        CommentDto updatedComment = commentService.modifyComment(commentDto, commentId, postId);
        return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
    }
}
