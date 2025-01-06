package com.example.twitterfake_new.Controllers;

import com.example.twitterfake_new.Models.AuthenticationRequest;
import com.example.twitterfake_new.Models.AuthenticationResponse;
import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Services.UserService;
import com.example.twitterfake_new.security.JpaUserDetailsService;
import com.example.twitterfake_new.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("register");
        String username = authenticationRequest.getUsername();
        User user = userService.findUserByUsername(username);
        System.out.println("register");
        if (user != null) {
            System.out.println("user exists");
            return ResponseEntity.status(400).body("A user with this username already exists.");
        } else {
            userService.saveUser(new User(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            return ResponseEntity.status(200).body("Registration Successful");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            System.out.println("Incorrect username or passoword: " +  e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password.");
        }

        try {
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("No user with this username found");
        }
    }


}
