package com.example.twitterfake_new.Dtos;

public class UserDto {
    private Long userId;

    private String username;

    private byte[] imageData;
    private Long chatId;

    public UserDto(Long id, String username, byte[] imageData, Long chatId) {
        this.chatId = chatId;
        this.userId = id;
        this.username = username;
        this.imageData = imageData;
    }


    public UserDto(Long id, String username, byte[] imageData) {
        this.userId = id;
        this.username = username;
        this.imageData = imageData;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
