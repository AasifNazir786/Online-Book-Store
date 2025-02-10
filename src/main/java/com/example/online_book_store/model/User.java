package com.example.online_book_store.model;

import java.util.List;

import com.example.online_book_store.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "customer_name", nullable=false)
    private String fullName;

    @Column(name="username", nullable=false)
    @Pattern(regexp="", message="username should be at least 5 characters long")
    @Size(min=5)
    private String username;

    @Column(name="password", nullable=false)
    @Pattern(regexp="", message="password should be at least 8 characters long and contain at least one special character and one digit number")
    private String password;

    @Column(name = "email", nullable=false, unique=true)
    private String email;

    @Column(name = "user_address", nullable=false)
    private String address;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<BookOrder> orders;

    @OneToMany(mappedBy="user", orphanRemoval=true)
    private List<Cart> cartList;

    private Role role = Role.USER;
}