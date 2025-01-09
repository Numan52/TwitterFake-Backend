package com.example.twitterfake_new.Dtos;

public class PostRequest {
    private Long userId;
    private String content;
    private Long parentPostId;


    public PostRequest(Long userId, String content, Long ParentPostId) {
        this.userId = userId;
        this.content = content;
        this.parentPostId = ParentPostId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Long parentPostId) {
        this.parentPostId = parentPostId;
    }
}
