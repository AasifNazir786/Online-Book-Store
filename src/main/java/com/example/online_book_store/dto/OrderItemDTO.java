package com.example.online_book_store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long bookId;
    @NotNull
    @Positive
    private Integer quantity;
    
    public OrderItemDTO() {
    }

    public OrderItemDTO(Long bookId, Long id, Long orderId, Integer quantity) {
        this.bookId = bookId;
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO [id=" + id + ", orderId=" + orderId + ", bookId=" + bookId + ", quantity=" + quantity
                + "]";
    }
}
