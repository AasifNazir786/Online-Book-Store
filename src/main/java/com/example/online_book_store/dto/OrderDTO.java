package com.example.online_book_store.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private String bookTitle;
    private LocalDate orderDate;
    private int quantity;
    private int customerId;
    private List<Integer> booksIDs;

    public OrderDTO() {}

    public OrderDTO(int orderId, String bookTitle, LocalDate orderDate, int quantity, int customerId,
            List<Integer> booksIDs) {
        this.orderId = orderId;
        this.bookTitle = bookTitle;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.customerId = customerId;
        this.booksIDs = booksIDs;
    }

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId;}

    public String getBookTitle() {return bookTitle;}

    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}

    public LocalDate getOrderDate() { return orderDate;}

    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate;}

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getBooksIDs() {
        return booksIDs;
    }

    public void setBooksIDs(List<Integer> booksIDs) {
        this.booksIDs = booksIDs;
    }

    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId + ", bookTitle=" + bookTitle + ", orderDate=" + orderDate + ", quantity="
                + quantity + ", customerId=" + customerId + ", booksIDs=" + booksIDs + "]";
    }
    
}
