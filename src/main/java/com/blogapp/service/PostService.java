package com.blogapp.service;

import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> readPost();

    PostDto getById(long postId);

    void deleteById(Long postId);

    PostDto updateById(PostDto postDto, Long postid);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
