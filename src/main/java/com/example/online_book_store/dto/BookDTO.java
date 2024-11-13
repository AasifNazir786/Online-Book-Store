/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.online_book_store.dto;

public class BookDTO {
    private int bookId;
    private String bookTitle, authorName;
    private int bookStock;
    private double bookPrice;
    
    public BookDTO() {}

    public BookDTO(int bookId, String bookTitle, String authorName, int bookStock, double bookPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
    }

    public int getBookId() {return bookId;}

    public void setBookId(int bookId) {this.bookId = bookId;}

    public String getBookTitle() {return bookTitle;}

    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}

    public int getBookStock() {return bookStock;}

    public void setBookStock(int bookStock) {this.bookStock = bookStock;}

    public double getBookPrice() {return bookPrice;}

    public void setBookPrice(double bookPrice) {this.bookPrice = bookPrice;}

    public String getAuthorName() {return authorName;}

    public void setAuthorName(String authorName) {this.authorName = authorName;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BookDTO{");
        sb.append("bookId=").append(bookId);
        sb.append(", bookTitle=").append(bookTitle);
        sb.append(", AuthorName=").append(authorName);
        sb.append(", bookStock=").append(bookStock);
        sb.append(", bookPrice=").append(bookPrice);
        sb.append('}');
        return sb.toString();
    }
}
