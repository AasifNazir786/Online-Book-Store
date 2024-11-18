package com.example.online_book_store.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private String bookTitle;
    private LocalDate orderDate;
    private int quantity;
    private CustomerDTO customer;
    private List<String> booksName;

    public OrderDTO() {}

    public OrderDTO(int orderId, String bookTitle, LocalDate orderDate, int quantity, CustomerDTO customer,
            List<String> booksName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.customer = customer;
        this.booksName = booksName;
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

    public List<String> getBooks() {return booksName;}

    public void setBooks(List<String> books) {this.booksName = books;}

    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId + ", bookTitle=" + bookTitle + ", orderDate=" + orderDate + ", quantity="
                + quantity + ", customer=" + customer + ", books=" + booksName + "]";
    }
    
}
