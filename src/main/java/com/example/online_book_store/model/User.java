package com.example.online_book_store.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.online_book_store.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.ToString;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="user_full_name",nullable=false)
    @Size(min = 2, message = "Full name must be at least 2 characters long")
    private String fullName;

    @Column(name = "username", nullable = false, unique = true)
    @Size(min = 5, message = "Username should be at least 5 characters long")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])(?=.*[A-Za-z]).{8,}$",
            message = "Password must contain at least one letter, one digit, and one special character (@$!%*?&)"
    )
    @ToString.Exclude
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "user_address")
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true)
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @ToString.Exclude
    private String phoneNumber;

    @Column(name = "reset_token", unique = true)
    @ToString.Exclude
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Cart> cartList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User() {
    }

    public User(Long id, @Size(min = 2, message = "Full name must be at least 2 characters long") String fullName,
            @Size(min = 5, message = "Username should be at least 5 characters long") String username,
            @Size(min = 8, message = "Password should be at least 8 characters long") @Pattern(regexp = "^(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$", message = "Password should contain at least one special character and one digit") String password,
            @Email(message = "Invalid email format") String email, String address,
            @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") String phoneNumber,
            String resetToken, LocalDateTime resetTokenExpiry, List<Order> orders, List<Cart> cartList, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.resetToken = resetToken;
        this.resetTokenExpiry = resetTokenExpiry;
        this.orders = orders;
        this.cartList = cartList;
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

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", fullName=" + fullName + ", username=" + username + ", email=" + email
                + ", address=" + address + ", resetTokenExpiry=" + resetTokenExpiry + ", role=" + role + "]";
    }
}
