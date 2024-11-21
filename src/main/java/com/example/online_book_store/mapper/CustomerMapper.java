package com.example.online_book_store.mapper;

import com.example.online_book_store.model.BookOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.CustomerDTO;
import com.example.online_book_store.model.Customer;

import java.util.List;

@Mapper(componentModel="spring", uses = {OrderMapper.class})
public interface CustomerMapper {

    @Mapping(source = "orders", target = "orders")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target="orders", ignore=true)
    Customer toEntity(CustomerDTO customerDTO);

    // Custom method to map orders separately
    default Customer toEntityWithOrders(CustomerDTO customerDTO, List<BookOrder> orders) {
        Customer customer = toEntity(customerDTO);
        if (customer != null) {
            customer.setOrders(orders);
        }
        return customer;
    }
}
