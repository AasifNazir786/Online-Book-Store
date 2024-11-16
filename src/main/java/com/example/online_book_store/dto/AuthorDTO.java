package com.example.online_book_store.dto;

import java.util.List;

public class AuthorDTO {
    private int authorId;
    private String authorName;
    private String authorBiography;
    private String nationality;
    private String awards;
    private List<BookDTO> books;

    public AuthorDTO() {
    }

    public AuthorDTO(int authorId, String authorName, String authorBiography, String nationality, String awards,
            List<BookDTO> books) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorBiography = authorBiography;
        this.nationality = nationality;
        this.awards = awards;
        this.books = books;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
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
        return "AuthorDTO [authorId=" + authorId + ", authorName=" + authorName + ", authorBiography=" + authorBiography
                + ", nationality=" + nationality + ", awards=" + awards + ", books=" + books + "]";
    }
    
}
