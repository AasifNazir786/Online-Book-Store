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
import com.example.online_book_store.dto.UserWithToken;
import com.example.online_book_store.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser dto){
        UserWithToken token = userService.registerUser(dto);
        if(token.getToken() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUser user){
        System.out.println("login method is called");
        UserWithToken userWithToken = userService.verifyUser(user);
        if(userWithToken.getToken() == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userWithToken, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logoutUser(){
        userService.logoutUser();
    }
}
