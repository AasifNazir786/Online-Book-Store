package com.example.online_book_store.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private String bookTitle;
    private LocalDate orderDate;
    private int quantity;
    private CustomerDTO customer;
    private List<BookDTO> books;

    public OrderDTO() {}

    public OrderDTO(int orderId, String bookTitle, LocalDate orderDate, int quantity, CustomerDTO customer,
            List<BookDTO> books) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.customer = customer;
        this.books = books;
    }

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId;}

    public String getBookTitle() {return bookTitle;}

    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}

    public LocalDate getOrderDate() { return orderDate;}

    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate;}

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public CustomerDTO getCustomer() { return customer; }

    public void setCustomer(CustomerDTO customer) {this.customer = customer;}

    public List<BookDTO> getBooks() {return books;}

    public void setBooks(List<BookDTO> books) {this.books = books;}

    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId + ", bookTitle=" + bookTitle + ", orderDate=" + orderDate + ", quantity="
                + quantity + ", customer=" + customer + ", books=" + books + "]";
    }
    
}
