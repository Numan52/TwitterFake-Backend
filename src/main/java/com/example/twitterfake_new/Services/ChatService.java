package com.example.twitterfake_new.Services;

import com.example.twitterfake_new.Dtos.ChatDto;
import com.example.twitterfake_new.Dtos.MessageDto;
import com.example.twitterfake_new.Models.Chat;
import com.example.twitterfake_new.Models.Message;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Dtos.UserDto;
import com.example.twitterfake_new.Repositories.ChatRepository;
import com.example.twitterfake_new.Repositories.MessageRepository;
import com.example.twitterfake_new.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChatService {
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }


    public Chat findById(Long id) {
        if (id == null) {
            return null;
        }
        return chatRepository.findById(id).orElse(null);
    }


    public List<UserDto> getContacts(Long currentUserId) {
        List<UserDto> users = new ArrayList<>();
        List<Chat> chats = chatRepository.findChatsByUser(currentUserId);

        for (Chat chat : chats) {
            User userOne = chat.getUser1();
            User userTwo = chat.getUser2();
            if (!userOne.getId().equals(currentUserId)) {
                users.add(new UserDto(userOne.getId(), userOne.getUsername(), userOne.getImageData(), chat.getId()));
            } else {
                users.add(new UserDto(userTwo.getId(), userTwo.getUsername(), userTwo.getImageData(), chat.getId()));
            }
        }

        return users;
    }


    public ChatDto getChatById(Long chatId) throws NoSuchElementException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NoSuchElementException("Chat not found"));
        List<Message> messages = messageRepository.findMessagesByChatId(chat.getId());

        return new ChatDto(
                chat.getId(),
                toMessageDtos(messages),
                toMessageDto(chat.getLastMessage()),
                chat.getUser1().getId(),
                chat.getUser2().getId(),
                chat.getCreatedAt()
        );
    }


    public Chat createChat(User sender, Long receiverId, Message message, LocalDateTime time) throws NoSuchElementException {
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new NoSuchElementException("User not found"));

        Chat chat = new Chat(
                time,
                sender,
                receiver,
                null
        );
        chat = chatRepository.save(chat);

        message.setChat(chat);
        message = messageRepository.save(message);

        chat.setLastMessage(message);

        return chatRepository.save(chat);
    }


    public MessageDto toMessageDto(Message message) {
        Long chatId = message.getChat() != null ? message.getChat().getId() : null;
        return new MessageDto(
                message.getId(),
                chatId,
                message.getSender().getId(),
                message.getContent(),
                message.getCreatedAt()
        );
    }


    public List<MessageDto> toMessageDtos(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            messageDtos.add(toMessageDto(message));
        }
        return messageDtos;
    }


    public Message updateChat(Chat chat, Message message) {
        message = messageRepository.save(message);
        chat.setLastMessage(message);
        chatRepository.save(chat);

        return message;
    }
}
