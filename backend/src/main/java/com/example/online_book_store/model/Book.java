package com.example.online_book_store.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_title", nullable = false)
    @NotBlank(message = "Book title is required")
    private String bookTitle;

    @Column(name = "book_stock", nullable = false)
    @PositiveOrZero
    private int bookStock;

    @Column(name = "book_price", nullable = false)
    @Positive(message = "Price must be positive")
    private double bookPrice;

    @NotBlank
    @Column(name = "genre")
    private String genre;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable=false)
    @JsonBackReference
    private Author author;

    public Book() {
    }

    public Book(Long id, String bookTitle, int bookStock,
                String genre, double bookPrice, String imageUrl,
                LocalDate publicationDate, Author author) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.genre = genre;
        this.bookPrice = bookPrice;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Book title is required") String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(@NotBlank(message = "Book title is required") String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @PositiveOrZero
    public int getBookStock() {
        return bookStock;
    }

    public void setBookStock(@PositiveOrZero int bookStock) {
        this.bookStock = bookStock;
    }

    @Positive(message = "Price must be positive")
    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(@Positive(message = "Price must be positive") double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public @NotBlank String getGenre() {
        return genre;
    }

    public void setGenre(@NotBlank String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(@NotNull LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookStock=" + bookStock +
                ", bookPrice=" + bookPrice +
                ", genre='" + genre + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publicationDate=" + publicationDate +
                ", author=" + author +
                '}';
    }
}