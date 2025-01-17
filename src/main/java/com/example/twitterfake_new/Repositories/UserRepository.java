package com.example.twitterfake_new.Repositories;

import com.example.twitterfake_new.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    User findByUsername(String username);



}
