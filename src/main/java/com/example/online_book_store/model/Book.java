package com.example.online_book_store.model;

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

@Entity
@Table(name = "books")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_stock")
    private int bookStock;

    @Column(name = "book_price")
    private double bookPrice;

    @Column(name = "genre")
    private String genre;

    @Column(name = "publication_date")
    private String publicationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Book() {
    }

    public Book(int bookId, String bookTitle, int bookStock, double bookPrice, String genre, String publicationDate,
            Author author) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookStock=" + bookStock + ", bookPrice="
                + bookPrice + ", genre=" + genre + ", publicationDate=" + publicationDate + "]";
    }
}