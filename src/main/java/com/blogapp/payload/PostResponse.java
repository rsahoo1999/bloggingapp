package com.blogapp.payload;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private int totalNoElement;
    private int totalPage;
    private boolean last;
}
