package com.example.twitterfake_new.Services;

import com.example.twitterfake_new.Models.Post;
import com.example.twitterfake_new.Dtos.PostDto;
import com.example.twitterfake_new.Dtos.PostRequest;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Repositories.PostRepository;
import com.example.twitterfake_new.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PostService {


    private PostRepository postRepository;

    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostDto convertToDto(Post post) {
        List<PostDto> responses = new ArrayList<>();

        if (post.getResponses() != null) {
            responses = post.getResponses().stream()
                    .sorted((response1, response2 ) -> response2.getCreatedAt().compareTo(response1.getCreatedAt()))
                    .map(response -> new PostDto(
                            response.getContent(),
                            response.getId(),
                            response.getUser().getId(),
                            response.getParentPost() != null ? response.getParentPost().getId() : null,
                            null,
                            response.getUser().getUsername(),
                            response.getCreatedAt(),
                            post.getLikedBy() != null ? response.getLikedBy().stream().map(user -> {
                                return user.getId();
                            }).collect(Collectors.toSet()) : Set.of()
                    )).toList();
        }

        return new PostDto(
                post.getContent(),
                post.getId(),
                post.getUser().getId(),
                post.getParentPost() != null ? post.getParentPost().getId() : null,
                responses,
                post.getUser().getUsername(),
                post.getCreatedAt(),
                post.getLikedBy() != null ? post.getLikedBy().stream().map(user -> {
                    return user.getId();
                }).collect(Collectors.toSet()) : Set.of()
        );
    }

    public Post savePost(PostRequest postRequest) {
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found while posting tweet"));
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());

        if (postRequest.getParentPostId() != null) {
            Post parentPost = postRepository.findById(postRequest.getParentPostId())
                    .orElseThrow(() -> new RuntimeException("Parent post not found"));
            post.setParentPost(parentPost);
        }
        return postRepository.save(post);
    }

    public Page<PostDto> getPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findByParentPostIdIsNull(pageRequest)
                .map(post -> convertToDto(post));
    }

    public Page<PostDto> getUserPosts(long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAllByUserIdAndParentPostNull(userId, pageRequest)
                .map(post -> convertToDto(post));
    }

    public Page<PostDto> getLikedPosts(User user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAllByLikedBy(Set.of(user), pageRequest)
                .map(post -> convertToDto(post));
    }



    public Page<PostDto> getPostsRespondedTo(long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        postRepository.findParentPostsRespondedByUser(userId, pageRequest)
                .forEach(post -> System.out.println(post.getParentPost().getId()));
        ;
        List<Post> parentPosts = postRepository.findParentPostsRespondedByUser(userId, pageRequest).stream()
                .map(post -> post.getParentPost())
                .distinct()
                .collect(Collectors.toList());

        List<PostDto> postDtos = parentPosts.stream()
                .map(post -> convertToDto(post))
                .collect(Collectors.toList());

        return new PageImpl<>(postDtos, pageRequest, postDtos.size());
    }
}
