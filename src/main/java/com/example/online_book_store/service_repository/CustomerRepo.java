package com.example.online_book_store.service_repository;

import java.util.List;

import com.example.online_book_store.dto.CustomerDTO;

public interface CustomerRepo extends BaseRepository<CustomerDTO>{

    List<CustomerDTO> getAllCustomers();

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomerById(int id);

    CustomerDTO updateCustomer(int id, CustomerDTO customerDTO);

    void deleteCustomer(int id);

}
