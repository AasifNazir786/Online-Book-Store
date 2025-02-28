package com.example.online_book_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class BookWithAuthorDTO {
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

    private String authorName;

    public BookWithAuthorDTO() {
    }

    public BookWithAuthorDTO(Long id, @NotBlank(message = "Book title is required") String bookTitle,
            @PositiveOrZero int bookStock, @Positive(message = "Price must be positive") double bookPrice,
            @NotBlank String genre, String imageUrl, String authorName) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
        this.genre = genre;
        this.imageUrl = imageUrl;
        this.authorName = authorName;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookStock() {
        return bookStock;
    }

    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "BookWithAuthorDTO [id=" + id + ", bookTitle=" + bookTitle + ", bookStock=" + bookStock + ", bookPrice="
                + bookPrice + ", genre=" + genre + ", imageUrl=" + imageUrl + ", authorName=" + authorName + "]";
    }
}
