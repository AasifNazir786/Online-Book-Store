package com.example.online_book_store.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int authorId;
    private String authorName;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author() {}

    public Author(String authorName) {
        this.authorName = authorName;
    }

    public int getId() {
        return authorId;
    }

    public void setId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return authorName;
    }

    public void setName(String name) {
        this.authorName = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}