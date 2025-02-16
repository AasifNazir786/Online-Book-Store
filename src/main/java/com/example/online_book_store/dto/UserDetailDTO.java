package com.example.online_book_store.dto;

import com.example.online_book_store.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.ToString;

public class UserDetailDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, message = "Full name must be at least 2 characters long")
    private String fullName;

    @NotBlank
    @Size(min = 5, message = "Username should be at least 5 characters long")
    private String username;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;
    private String address;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @ToString.Exclude
    private String phoneNumber;

    private Role role;

    public UserDetailDTO() {}

    public UserDetailDTO(Long id, String fullName, String username, String email, String address, String phoneNumber, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
