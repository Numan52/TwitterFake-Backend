package com.example.twitterfake_new.Services;

import com.example.twitterfake_new.Exceptions.UserNotFoundException;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@Service
public class UserService {
    public UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id " + id + " not found")
        );
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user, MultipartFile imageFile, String username)  throws IOException, IllegalStateException {
        if (imageFile != null) {
            user.setImageData(imageFile.getBytes());
        }
        if (username != null ) {
            if (userRepository.findByUsername(username) != null && !user.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username " + username + " already exists");
            }
            user.setUsername(username);
        }

        userRepository.save(user);
    }
}
