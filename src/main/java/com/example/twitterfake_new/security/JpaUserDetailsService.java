package com.example.twitterfake_new.security;

import com.example.twitterfake_new.Models.User;
import com.example.twitterfake_new.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username: " + username);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        System.out.println("hellolol");
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Username does not exist: " + username));

        return userOptional.map(user -> new MyUserDetails(user)).get();
    }
}
