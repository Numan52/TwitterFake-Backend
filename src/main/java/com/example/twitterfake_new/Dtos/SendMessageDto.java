package com.example.twitterfake_new.Dtos;

public class SendMessageDto {
    private Long chatId;
    private Long receiverId;
    private String messageContent;


    public SendMessageDto(Long chatId, Long receiverId, String messageContent) {
        this.chatId = chatId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
    }


    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
