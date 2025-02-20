package com.example.online_book_store.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SimpleUserDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;

    public SimpleUserDTO() {}

    public SimpleUserDTO(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}