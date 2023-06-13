package com.blogapp.service;

import com.blogapp.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);

    CommentDto readComment(Long postId, Long commentId);

    void removeCommet(Long postId, Long commentId);

    CommentDto modifyComment(CommentDto commentDto, Long commentId, Long postId);

}
