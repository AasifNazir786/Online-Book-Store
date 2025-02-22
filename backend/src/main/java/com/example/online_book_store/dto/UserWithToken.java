package com.example.online_book_store.dto;

public class UserWithToken {
    
    private String token;
    private UserDetailDTO user;

    public UserWithToken() {}

    public UserWithToken(String token, UserDetailDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetailDTO getUser() {
        return user;
    }

    public void setUser(UserDetailDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserWithToken{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
