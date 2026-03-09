package com.pranav.carbontracker.controller;

import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> register(@Valid @RequestBody User user){

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody User user){
//
//        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
//
//        if(dbUser.isPresent() &&
//                passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())){
//
//            String token = jwtUtil.generateToken(user.getEmail());
//
//            return Map.of("token", token);
//        }
//
//        // throw new RuntimeException("Invalid credentials");
//        return Map.of("error", "Invalid credentials");
//    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User loginRequest){

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return Map.of("token", token);
    }
}