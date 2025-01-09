package com.example.twitterfake_new.Dtos;

import com.example.twitterfake_new.Models.Message;

import java.time.LocalDateTime;
import java.util.List;

public class ChatDto {
    private Long chatId;
    private List<MessageDto> messages;
    private MessageDto lastMessage;
    private Long userOneId;
    private Long userTwoId;
    private LocalDateTime createdAt;

    public ChatDto(Long chatId, List<MessageDto> messages, MessageDto lastMessage, Long userOneId, Long userTwoId, LocalDateTime createdAt) {
        this.chatId = chatId;
        this.messages = messages;
        this.lastMessage = lastMessage;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.createdAt = createdAt;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public MessageDto getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDto lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(Long userOneId) {
        this.userOneId = userOneId;
    }

    public Long getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(Long userTwoId) {
        this.userTwoId = userTwoId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
