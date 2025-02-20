package com.example.online_book_store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

public class LoginUser {
    
    @NotBlank
    private String username;

    @NotBlank
    @ToString.Exclude
    private String password;

    public LoginUser() {
    }

    public LoginUser(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUser [username=" + username + "]";
    }
}
