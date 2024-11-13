package com.example.online_book_store.dto;

import java.util.List;

public class CustomerDTO {
    private int id;
    private String customerName, customerEmail, customerAddress;
    private List<OrderDTO> orders;
    
    public CustomerDTO() {}

    public CustomerDTO(int id, String customerName, String customerEmail, String customerAddress,
            List<OrderDTO> orders) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.orders = orders;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getCustomerName() {return customerName;}

    public void setCustomerName(String customerName) {this.customerName = customerName;}

    public String getCustomerEmail() {return customerEmail;}

    public void setCustomerEmail(String customerEmail) {this.customerEmail = customerEmail;}

    public String getCustomerAddress() {return customerAddress;}

    public void setCustomerAddress(String customerAddress) {this.customerAddress = customerAddress;}

    public List<OrderDTO> getOrders() {return orders;}

    public void setOrders(List<OrderDTO> orders) {this.orders = orders;}

    @Override
    public String toString() {
        return "CustomerDTO [id=" + id + ", customerName=" + customerName + ", customerEmail=" + customerEmail
                + ", customerAddress=" + customerAddress + ", orders=" + orders + "]";
    }
}
