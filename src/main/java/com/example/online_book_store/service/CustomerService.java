package com.example.online_book_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.Customer;
import com.example.online_book_store.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Customer getById(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null); // Return null if not found
    }

    // Create or update a customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer by ID
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}