package com.example.online_book_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_book_store.dto.LoginUser;
import com.example.online_book_store.dto.RegisterUser;
import com.example.online_book_store.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser dto){
        String token = userService.registerUser(dto);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUser user){
        String token = userService.verifyUser(user);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/logout")
    public void logoutUser(){
        userService.logoutUser();
    }
}
