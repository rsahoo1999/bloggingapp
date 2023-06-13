package com.blogapp.service.serviceImpl;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.exception.BlogAPIException;
import com.blogapp.exception.ResourcesNotFoundException;
import com.blogapp.payload.CommentDto;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    // Method for create comment.
    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        // Check the
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
         Comment comment = mapToEntity(commentDto);
         comment.setPost(post);
         Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public CommentDto readComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourcesNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getPostId().equals(post.getPostId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post ");
        }
        return mapToDto(comment);
    }

    @Override
    public void removeCommet(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourcesNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getPostId().equals(post.getPostId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto modifyComment(CommentDto commentDto, Long commentId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourcesNotFoundException("Comment", "id", commentId)
        );
        if(!comment.getPost().getPostId().equals(post.getPostId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment id does not match with the post id");
        }
        comment.setCommentName(commentDto.getCommentName());
        comment.setCommentEmail(commentDto.getCommentEmail());
        comment.setCommentBody(commentDto.getCommentBody());

        Comment undatedComment = commentRepository.save(comment);

        return mapToDto(undatedComment);
    }
    //Conversion from Entity to DTO
    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }
    //Conversion from DTO to entity
    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
