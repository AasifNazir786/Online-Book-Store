package com.example.online_book_store.dto;

import com.example.online_book_store.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.ToString;

public class RegisterUser {
    
    private Long id;

    @NotBlank
    @Size(min = 2, message = "Full name must be at least 2 characters long")
    private String fullName;

    @NotBlank
    @Size(min = 5, message = "Username should be at least 5 characters long")
    private String username;
    
    @NotBlank
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]+$",
            message = "Password should contain at least one special character and one digit")
    @ToString.Exclude
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @ToString.Exclude
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public RegisterUser() {
    }

    public RegisterUser(Long id,
            @NotBlank @Size(min = 2, message = "Full name must be at least 2 characters long") String fullName,
            @NotBlank @Size(min = 5, message = "Username should be at least 5 characters long") String username,
            @NotBlank @Size(min = 8, message = "Password should be at least 8 characters long") @Pattern(regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$", message = "Password should contain at least one special character and one digit") String password,
            @Email(message = "Invalid email format") String email,
            @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") String phoneNumber, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "RegisterUser [id=" + id + ", fullName=" + fullName + ", username=" + username + ", email=" + email
                + ", role=" + role + "]";
    }
}
