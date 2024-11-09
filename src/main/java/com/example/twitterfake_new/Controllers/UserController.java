package com.example.twitterfake_new.Controllers;

import com.example.twitterfake_new.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/getUserId")
    public ResponseEntity<?> getUserIdByUsername(@RequestParam String username) {
        try {
            Long userId = userService.findUserByUsername(username).getId();
            return ResponseEntity.ok().body(userId);
        } catch (Exception e) {
            System.out.println("Error while getting userId: " + e);
            return ResponseEntity.status(404).body("Error while getting userId");
        }
    }
}
