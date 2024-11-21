package com.example.online_book_store.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private LocalDate orderDate;
    private int quantity;
    private int customerId;
    private List<Integer> bookIDs;

    public OrderDTO() {}

    public OrderDTO(int orderId, LocalDate orderDate, int quantity, int customerId,
            List<Integer> bookIDs) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.customerId = customerId;
        this.bookIDs = bookIDs;
    }

    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId;}

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

    public List<Integer> getBookIDs() {
        return bookIDs;
    }

    public void setBookIDs(List<Integer> bookIDs) {
        this.bookIDs = bookIDs;
    }

    @Override
    public String toString() {
        return "OrderDTO [orderId=" + orderId + ", orderDate=" + orderDate + ", quantity="
                + quantity + ", customerId=" + customerId + ", bookIDs=" + bookIDs + "]";
    }
    
}
