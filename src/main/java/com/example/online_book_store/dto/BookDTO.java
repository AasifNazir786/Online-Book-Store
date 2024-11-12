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
    
    public BookDTO() {
    }

    public BookDTO(int bookId, String bookTitle, int bookStock, double bookPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
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

    @Override
    public String toString() {
        return "BookDTO [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookStock=" + bookStock + ", bookPrice="
                + bookPrice + "]";
    }

    
}
