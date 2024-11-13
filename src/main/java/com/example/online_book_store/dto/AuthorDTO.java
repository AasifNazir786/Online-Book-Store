package com.example.online_book_store.dto;

import java.util.List;

public class AuthorDTO {
    private int authorId;
    private String authorName;
    private String authorBio;
    private List<BookDTO> books;
    
    public AuthorDTO() {}

    public AuthorDTO(String authorBio, int authorId, String authorName, List<BookDTO> books) {
        this.authorBio = authorBio;
        this.authorId = authorId;
        this.authorName = authorName;
        this.books = books;
    }

    public int getAuthorId() {return authorId;}

    public void setAuthorId(int authorId) {this.authorId = authorId;}

    public String getAuthorName() {return authorName;}

    public void setAuthorName(String authorName) {this.authorName = authorName;}

    public List<BookDTO> getBooks() {return books;}

    public void setBooks(List<BookDTO> books) {this.books = books;}

    public String getAuthorBio() {return authorBio;}

    public void setAuthorBio(String authorBio) {this.authorBio = authorBio;}

    @Override
    public String toString() {
        return "AuthorDTO [authorId=" + authorId + ", authorName=" + authorName + ", authorBio=" + authorBio
                + ", books=" + books + "]";
    }
}
