package com.example.online_book_store.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class BookDTO {

    private Long id;

    @NotBlank(message = "Book title is required")
    private String bookTitle;

    @PositiveOrZero
    private int bookStock;

    @Positive(message = "Price must be positive")
    private double bookPrice;

    @NotBlank
    private String genre;

    private String imageUrl;

    @NotNull
    private LocalDate publicationDate;

    private Long authorId;

    public BookDTO() {
    }

    public BookDTO(Long id, String bookTitle,
                    int bookStock, String genre,
                    double bookPrice, String imageUrl,
                    LocalDate publicationDate, Long authorId) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.genre = genre;
        this.bookPrice = bookPrice;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookStock=" + bookStock +
                ", bookPrice=" + bookPrice +
                ", genre='" + genre + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publicationDate=" + publicationDate +
                ", authorId=" + authorId +
                '}';
    }
}
