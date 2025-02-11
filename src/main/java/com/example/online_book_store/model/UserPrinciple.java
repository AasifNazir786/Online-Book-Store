package com.example.online_book_store.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {

    private final User user;

    public UserPrinciple(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Change logic if account expiry needs to be handled
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic if account locking is required
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify if password expiration needs to be handled
    }

    @Override
    public boolean isEnabled() {
        return true; // Change if user activation logic is required
    }
}
