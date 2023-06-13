package com.blogapp.service.serviceImpl;

import com.blogapp.entity.Post;
import com.blogapp.exception.ResourcesNotFoundException;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post savePost = postRepository.save(mapToEntity(postDto));
        return mapToDto(savePost);
    }

    @Override
    public List<PostDto> readPost() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
        return mapToDto(post);
    }

    @Override
    public void deleteById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postId)
        );
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto updateById(PostDto postDto, Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new ResourcesNotFoundException("Post", "id", postid)
        );
        Post updateDetails = postRepository.save(mapToEntity(postDto));
        return mapToDto(updateDetails);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> allPost = postRepository.findAll(pageable);
        List<Post> listOfPost = allPost.getContent();
        List<PostDto> collect = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collect);
        postResponse.setPageNo(allPost.getNumber());
        postResponse.setPageSize(allPost.getSize());
        postResponse.setTotalNoElement((int) allPost.getTotalElements());
        postResponse.setTotalPage(allPost.getTotalPages());
        postResponse.setLast(allPost.isLast());
        return postResponse;
    }


    public PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
