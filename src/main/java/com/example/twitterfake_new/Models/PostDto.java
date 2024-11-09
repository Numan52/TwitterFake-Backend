package com.example.twitterfake_new.Models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class PostDto {
    private String content;
    private Long id;
    private Long parentPostId;
    private List<PostDto> responses;
    private String author;
    private Set<Long> likedBy;
    private LocalDateTime createdAt;

    public PostDto(String content, Long id, Long parentPostId, List<PostDto> responses, String author, LocalDateTime createdAt, Set<Long> likedBy) {
        this.content = content;
        this.id = id;
        this.parentPostId = parentPostId;
        this.responses = responses;
        this.author = author;
        this.createdAt = createdAt;
        this.likedBy = likedBy;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Long> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<Long> likedBy) {
        this.likedBy = likedBy;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public List<PostDto> getResponses() {
        return responses;
    }

    public void setResponses(List<PostDto> responses) {
        this.responses = responses;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
