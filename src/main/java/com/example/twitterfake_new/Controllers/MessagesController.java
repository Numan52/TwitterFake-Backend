package com.example.twitterfake_new.Controllers;

import com.example.twitterfake_new.Dtos.ChatDto;
import com.example.twitterfake_new.Dtos.MessageDto;
import com.example.twitterfake_new.Dtos.SendMessageDto;
import com.example.twitterfake_new.Models.Chat;
import com.example.twitterfake_new.Models.Message;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Dtos.UserDto;
import com.example.twitterfake_new.Services.ChatService;
import com.example.twitterfake_new.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MessagesController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/api/chats/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User sender = userService.findUserByUsername(username);
            Chat chat = chatService.findById(sendMessageDto.getChatId());
            System.out.println(sendMessageDto.getMessageContent());
            LocalDateTime time = LocalDateTime.now();
            Message message = new Message(
                    chat,
                    sender,
                    sendMessageDto.getMessageContent(),
                    time
            );
            MessageDto messageDto = chatService.toMessageDto(message);

            if (chat == null) {
                Chat createdChat = chatService.createChat(sender, sendMessageDto.getReceiverId(), message, time);
                messageDto.setChatId(createdChat.getId());
                return ResponseEntity.ok().body(messageDto);
            }

            Message createdMessage = chatService.updateChat(chat, message);
            messageDto.setMessageId(createdMessage.getId());

            return ResponseEntity.ok().body(messageDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }



    @Transactional
    @GetMapping("/api/chats/getContacts")
    public ResponseEntity<?> getContacts() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(username);

            List<UserDto> users = chatService.getContacts(user.getId());
            return ResponseEntity.ok(users);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @GetMapping("/api/chats/getChat")
    public ResponseEntity<?> getChat(@RequestParam Long chatId) {
        try {
            ChatDto chatDto = chatService.getChatById(chatId);
            return ResponseEntity.ok(chatDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }




}
