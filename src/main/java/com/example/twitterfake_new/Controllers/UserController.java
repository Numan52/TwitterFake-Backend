package com.example.twitterfake_new.Controllers;

import com.example.twitterfake_new.Exceptions.UserNotFoundException;
import com.example.twitterfake_new.Dtos.AuthenticationResponse;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Dtos.UserDto;
import com.example.twitterfake_new.Services.ChatService;
import com.example.twitterfake_new.Services.UserService;
import com.example.twitterfake_new.security.JpaUserDetailsService;
import com.example.twitterfake_new.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private ChatService chatService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/getUserId")
    public ResponseEntity<?> getUserIdByUsername(@RequestParam String username) {
        try {
            Long userId = userService.findUserByUsername(username).getId();
            return ResponseEntity.ok().body(userId);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error while getting userId: " + e);
            return ResponseEntity.status(500).body("Error while getting userId");
        }
    }

    @Transactional
    @GetMapping("/api/getUser")
    public ResponseEntity<?> getUser(@RequestParam Long userId) {
        try {
            User user = userService.findById(userId);

            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getImageData());
            return ResponseEntity.ok().body(userDto);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



    @Transactional
    @PutMapping("/api/updateUser")
    public ResponseEntity<?> updateUser(
            @RequestParam("oldUsername") String oldUsername,
            @RequestParam("newUsername") String newUsername,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            User user = userService.findUserByUsername(oldUsername);
            userService.updateUser(user, image, newUsername);
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(newUsername);

            String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error while updating user: " + e.getMessage());
        }
    }


    @Transactional
    @GetMapping("/api/userImage")
    public ResponseEntity<?> getUserImage(@RequestParam("userId") Long userId) {
        try {
            User user = userService.findById(userId);
            Map<String, Object> json = new HashMap<>();
            json.put("imageData", user.getImageData());

            return ResponseEntity.ok(json);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error getting user image: " + e.getMessage());
        }
    }
}
