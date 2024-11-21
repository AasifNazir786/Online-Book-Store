package com.example.online_book_store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.CustomerDTO;
import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.mapper.CustomerMapper;
import com.example.online_book_store.mapper.OrderMapper;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.model.Customer;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.repository.CustomerRepository;
import com.example.online_book_store.service_repository.CustomerRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService implements CustomerRepo{
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
public CustomerDTO create(CustomerDTO customerDTO) {
    if (customerDTO == null) {
        throw new IllegalArgumentException("CustomerDTO cannot be null");
    }

    Customer customer = customerMapper.toEntity(customerDTO);

    if (customerDTO.getOrders() != null && !customerDTO.getOrders().isEmpty()) {
        List<BookOrder> orderList = new ArrayList<>();

        for (OrderDTO orderDTO : customerDTO.getOrders()) {
            BookOrder bookOrder = orderMapper.toEntity(orderDTO);
            bookOrder.setCustomer(customer);

            List<Integer> bookIDs = orderDTO.getBookIDs();
            if (bookIDs != null && !bookIDs.isEmpty()) {
                List<Book> books = new ArrayList<>();
                for (int bookId : bookIDs) {
                    Book book = bookRepository.findById(bookId)
                            .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));
                    
                    Author author = authorRepository.findById(book.getAuthor().getAuthorId())
                            .orElseThrow(() -> new IllegalArgumentException("Author with ID " + book.getAuthor().getAuthorId() + " not found"));
                    book.setAuthor(author);
                    
                    books.add(book);
                }
                bookOrder.setBooks(books); 
            }

            orderList.add(bookOrder);
        }

        customer.setOrders(orderList); 
    }

    Customer savedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(savedCustomer);
}

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDTO> customerDTOs = customers.stream()
            .map(customer -> {
                CustomerDTO customerDTO = customerMapper.toDTO(customer);

                List<OrderDTO> orderDTOs = customer.getOrders().stream()
                        .map(order -> orderMapper.toDTO(order))
                        .collect(Collectors.toList());

                customerDTO.setOrders(orderDTOs);

                return customerDTO;
            })
            .collect(Collectors.toList());

        return customerDTOs;
    }

    @Override
    public CustomerDTO getById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Customer not found with id: " + id));

        CustomerDTO customerDTO = customerMapper.toDTO(customer);

        List<OrderDTO> orderDTOs = customer.getOrders().stream()
                .map(order -> orderMapper.toDTO(order))
                .collect(Collectors.toList());

        customerDTO.setOrders(orderDTOs);

        return customerDTO;
    }

    @Override
public CustomerDTO updateById(int id, CustomerDTO customerDTO) {
    Customer customer = customerRepository.findById(id).orElse(null);

    if (customer == null) {
        // Create a new customer if not found
        customer = new Customer();
        customer.setCustomerId(id); // Explicitly set the ID if necessary, depending on your database setup
    }

    // Update customer fields
    customer.setCustomerName(customerDTO.getCustomerName());
    customer.setCustomerEmail(customerDTO.getCustomerEmail());
    customer.setCustomerAddress(customerDTO.getCustomerAddress());
    customer.setPhoneNumber(customerDTO.getPhoneNumber());

    if (customerDTO.getOrders() != null) {
        // Handle orders
        List<BookOrder> customerOrders = customer.getOrders();

        if (customerOrders == null) {
            customerOrders = new ArrayList<>();
            customer.setOrders(customerOrders);
        } else {
            customerOrders.clear(); // Clear existing orders
        }

        // Add updated orders
        for (OrderDTO orderDTO : customerDTO.getOrders()) {
            BookOrder bookOrder = orderMapper.toEntity(orderDTO);
            bookOrder.setCustomer(customer);

            // Set books for the order
            List<Book> books = new ArrayList<>();
            for (Integer bookId : orderDTO.getBookIDs()) {
                Book book = bookRepository.findById(bookId).orElseThrow(() ->
                    new EntityNotFoundException("Book not found with id: " + bookId));
                books.add(book);
            }
            bookOrder.setBooks(books);

            customerOrders.add(bookOrder); // Add to the orders collection
        }
    }

    // Save and return the customer
    Customer savedCustomer = customerRepository.save(customer);
    return customerMapper.toDTO(savedCustomer);
}


    

    @Override
    public void delete(int id) {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Customer not found with id: " + id));

        customerRepository.delete(existingCustomer);
    }
}