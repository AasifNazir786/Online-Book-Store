/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.online_book_store.dto;

public class BookDTO {
    private int bookId;
    private String bookTitle;
    private int bookStock;
    private double bookPrice;
    private String genre;
    private String publicationDate;
    private String authorName;

    public BookDTO() {
    }

    public BookDTO(int bookId, String bookTitle, int bookStock, double bookPrice, String genre, String publicationDate,
            String authorName) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.authorName = authorName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "BookDTO [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookStock=" + bookStock + ", bookPrice="
                + bookPrice + ", genre=" + genre + ", publicationDate=" + publicationDate + ", authorName=" + authorName
                + "]";
    }
}
