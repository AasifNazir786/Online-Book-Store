package com.example.online_book_store.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class AuthorDTO {

    private Long id;

    @NotBlank
    private String authorName;

    private String authorBiography;

    private String nationality;

    private String awards;

    private List<BookDTO> books = new ArrayList<>();

    public AuthorDTO() {}

    public AuthorDTO(Long id, String authorName,
                    String authorBiography,
                    String nationality, String awards,
                    List<BookDTO> books) {
        this.id = id;
        this.authorName = authorName;
        this.authorBiography = authorBiography;
        this.nationality = nationality;
        this.awards = awards;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(@NotBlank String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorBiography() {
        return authorBiography;
    }

    public void setAuthorBiography(String authorBiography) {
        this.authorBiography = authorBiography;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", authorBiography='" + authorBiography + '\'' +
                ", nationality='" + nationality + '\'' +
                ", awards='" + awards + '\'' +
                ", books=" + books +
                '}';
    }
}
