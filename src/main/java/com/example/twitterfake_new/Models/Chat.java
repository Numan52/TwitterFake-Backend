package com.example.twitterfake_new.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_one_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_two_id")
    private User user2;

    @OneToOne()
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;

    public Chat(LocalDateTime createdAt, User user1, User user2, Message lastMessage) {
        this.createdAt = createdAt;
        this.user1 = user1;
        this.user2 = user2;
        this.lastMessage = lastMessage;
    }

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
