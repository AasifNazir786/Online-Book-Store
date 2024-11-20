package com.example.online_book_store.service_repository;

import java.util.List;

import com.example.online_book_store.dto.OrderDTO;

public interface OrderRepo extends BaseRepository<OrderDTO>{

    List<OrderDTO> getAllCustomers();

    OrderDTO addCustomer(OrderDTO orderDTO);

    OrderDTO getCustomerById(int id);

    OrderDTO updateCustomer(int id, OrderDTO orderDTO);

    void deleteCustomer(int id);
    
}
