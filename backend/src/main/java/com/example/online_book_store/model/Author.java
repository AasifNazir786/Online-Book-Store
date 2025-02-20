package com.example.online_book_store.model;

import java.util.ArrayList;
import java.util.List;

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
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @NotBlank
    @Column(name = "author_name")
    private String authorName;

    @Column(name="author_bio", nullable=false)
    private String authorBiography;

    @NotBlank
    @Column(name = "nationality")
    private String nationality;

    @Column(name = "awards")
    private String awards;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch=FetchType.LAZY, orphanRemoval=true)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(Long id, String authorName, String authorBiography, String awards, String nationality, List<Book> books) {
        this.id = id;
        this.authorName = authorName;
        this.authorBiography = authorBiography;
        this.awards = awards;
        this.nationality = nationality;
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

    public @NotBlank String getNationality() {
        return nationality;
    }

    public void setNationality(@NotBlank String nationality) {
        this.nationality = nationality;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", authorBiography='" + authorBiography + '\'' +
                ", nationality='" + nationality + '\'' +
                ", awards='" + awards + '\'' +
                ", books=" + books +
                '}';
    }
}