package com.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    PostDto {
    private Long postId;
    @NotNull
    @Size(min = 5, message = "Title should be minimum 5 character")
    private String postTitle;
    @NotNull
    @Size(min = 5, message = "Content should be minimum 5 character")
    private String postContent;
  private List<CommentDto> comments;
}