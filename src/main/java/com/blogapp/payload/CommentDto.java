package com.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    @NotNull
    @Size(min = 1, message = "Name should not less than one character.")
    private String commentName;
    @NotNull
    private String commentEmail;
    @NotNull
    private String commentBody;
    private Long postId;
}
