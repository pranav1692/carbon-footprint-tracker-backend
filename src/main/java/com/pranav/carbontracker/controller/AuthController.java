package com.pranav.carbontracker.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.pranav.carbontracker.model.User;
import com.pranav.carbontracker.repository.UserRepository;
import com.pranav.carbontracker.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user){
//
//        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
//
//        if(dbUser.isPresent()){
//
//            if(passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())){
//                return "Login successful";
//            }
//        }
//
//        return "Invalid credentials";
//    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user){

        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());

        if(dbUser.isPresent() &&
                passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())){

            String token = jwtUtil.generateToken(user.getEmail());

            return Map.of("token", token);
        }

        // throw new RuntimeException("Invalid credentials");
        return Map.of("error", "Invalid credentials");
    }
}