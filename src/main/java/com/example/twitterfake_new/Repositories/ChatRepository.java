package com.example.twitterfake_new.Repositories;

import com.example.twitterfake_new.Models.Chat;
import com.example.twitterfake_new.Models.Message;
import com.example.twitterfake_new.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(
            "SELECT c " +
            "FROM Chat c " +
            "WHERE c.user1.id = :userId OR c.user2.id = :userId"
    )
    List<Chat> findChatsByUser(Long userId);


}
