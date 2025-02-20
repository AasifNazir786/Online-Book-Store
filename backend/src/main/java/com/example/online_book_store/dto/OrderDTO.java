package com.example.online_book_store.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.online_book_store.enums.Status;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderedDateTime;
    private Long userId;
    private Double totalPrice;
    private Status status;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {}

    public OrderDTO(Long id, LocalDateTime orderedDateTime, Long userId, Double totalPrice, Status status,
            List<OrderItemDTO> orderItems) {
        this.id = id;
        this.orderedDateTime = orderedDateTime;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderedDateTime() {
        return orderedDateTime;
    }

    public void setOrderedDateTime(LocalDateTime orderedDateTime) {
        this.orderedDateTime = orderedDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderDTO [id=" + id + ", orderedDateTime=" + orderedDateTime + ", userId=" + userId + ", totalPrice="
                + totalPrice + ", status=" + status + ", orderItems=" + orderItems + "]";
    }

    
}
