package com.example.online_book_store.dto;

import java.util.List;

public class CustomerDTO {
    private int customerId;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String phoneNumber;
    private List<OrderDTO> orders;
    
    public CustomerDTO() {}

    public CustomerDTO(int customerId, String customerName, String customerEmail, String customerAddress, String phoneNumber,
            List<OrderDTO> orders) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

    public String getCustomerName() {return customerName;}

    public void setCustomerName(String customerName) {this.customerName = customerName;}

    public String getCustomerEmail() {return customerEmail;}

    public void setCustomerEmail(String customerEmail) {this.customerEmail = customerEmail;}

    public String getCustomerAddress() {return customerAddress;}

    public void setCustomerAddress(String customerAddress) {this.customerAddress = customerAddress;}
    

    public List<OrderDTO> getOrders() {return orders;}

    public void setOrders(List<OrderDTO> orders) {this.orders = orders;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    @Override
    public String toString() {
        return "CustomerDTO [" +

                "id=" + customerId +
                ", customerName=" + customerName +
                ", customerEmail=" + customerEmail +
                ", customerAddress=" + customerAddress +
                ", orders=" + orders +
                ", phoneNumber=" + phoneNumber +

                "]";
    }
}
