package com.example.twitterfake_new.Repositories;

import com.example.twitterfake_new.Models.Chat;
import com.example.twitterfake_new.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByChatId(Long chatId);
}
