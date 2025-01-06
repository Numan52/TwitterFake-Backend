package com.example.twitterfake_new.Controllers;

import com.example.twitterfake_new.Models.Post;
import com.example.twitterfake_new.Models.PostDto;
import com.example.twitterfake_new.Models.PostRequest;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Repositories.PostRepository;
import com.example.twitterfake_new.Services.PostService;
import com.example.twitterfake_new.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/api/postTweet")
    public ResponseEntity<?> postTweet(@RequestBody PostRequest postRequest) {
        try {
            Post savedPost = postService.savePost(postRequest);

            System.out.println(savedPost);
            PostDto savedPostDto = postService.convertToDto(savedPost);
            return ResponseEntity.ok(savedPostDto);
        } catch (Exception e) {
            System.out.println("Error while posting tweet: " + e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error posting tweet");
        }
    }

    @Transactional
    @GetMapping("/api/getTweets")
    public Page<PostDto> getAllPosts(@RequestParam int page, @RequestParam int size) {
        Page<PostDto> posts = postService.getPosts(page, size);

        return posts;
    }



    @Transactional
    @GetMapping("/api/user-posts")
    public Page<PostDto> getUserPosts(@RequestParam String username, @RequestParam int page) {
        User user = userService.findUserByUsername(username);

        Page<PostDto> posts = postService.getUserPosts(user.getId(), page, 10);

        return posts;
    }

    @Transactional
    @GetMapping("/api/liked-posts")
    public Page<PostDto> getLikedPosts(@RequestParam String username, @RequestParam int page) {
        User user = userService.findUserByUsername(username);

        Page<PostDto> posts = postService.getLikedPosts(user, page, 10);

        return posts;
    }

    @Transactional
    @GetMapping("/api/user-responses")
    public Page<PostDto> getUserResponses(@RequestParam String username, @RequestParam int page) {
        User user = userService.findUserByUsername(username);

        Page<PostDto> posts = postService.getPostsRespondedTo(user.getId(), page, 10);

        return posts;
    }

    @Transactional
    @PutMapping("/api/likeTweet")
    public ResponseEntity<?> likeTweet(@RequestParam long postId, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findPostById(postId);
        if (post != null) {
            User user = userService.findUserByUsername(userDetails.getUsername());
            post.getLikedBy().add(user);
            Post savedPost = postRepository.save(post);
            return ResponseEntity.ok(postService.convertToDto(savedPost));
        } else {
            return ResponseEntity.status(404).body("Post or User not found");
        }
    }

    @Transactional
    @PutMapping("/api/unlikeTweet")
    public ResponseEntity<?> unlikeTweet(@RequestParam long postId, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findPostById(postId);
        if (post != null) {
            User user = userService.findUserByUsername(userDetails.getUsername());
            post.getLikedBy().remove(user);
            Post savedPost = postRepository.save(post);
            return ResponseEntity.ok(postService.convertToDto(savedPost));
        } else {
            return ResponseEntity.status(404).body("Post or User not found");
        }
    }
}
