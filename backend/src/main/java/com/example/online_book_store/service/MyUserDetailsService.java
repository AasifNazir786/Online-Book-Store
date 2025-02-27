package com.example.online_book_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.User;
import com.example.online_book_store.model.UserPrinciple;
import com.example.online_book_store.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("==================" + username);
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("user not found with username "+ username));

        return new UserPrinciple(user);
    }
    
}
