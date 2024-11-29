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
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.model.Customer;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.repository.CustomerRepository;
import com.example.online_book_store.service_repository.CustomerRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService implements CustomerRepo{
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) throws Exception {

        if(customerDTO == null){
            throw new IllegalArgumentException("customer is null");
        }
        Customer customer = customerMapper.toEntity(customerDTO);

        if(customerDTO.getOrders() != null){

            List<BookOrder> orders = customerDTO.getOrders()
                .stream()
                .map(orderDTO -> {

                    BookOrder order = orderMapper.toEntity(orderDTO);
                    order.setCustomer(customer);

                    if(orderDTO.getBookIDs() != null){

                        List<Book> books = bookRepository.findAllById(orderDTO.getBookIDs());
                        if (books.size() != orderDTO.getBookIDs().size()) {
                            try {
                                throw new Exception("Some books not found for IDs: " + orderDTO.getBookIDs());
                            } catch (Exception ex) {
                                ex.getMessage();
                            }
                        }
                        order.setBooks(books);
                    }
                    return order;
                })
                .collect(Collectors.toList());

            customer.setOrders(orders);
        }
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);

        if (savedCustomer.getOrders() != null) {
            List<OrderDTO> orderDTOs = savedCustomer.getOrders()
                .stream()
                .map(order -> {

                    OrderDTO orderDTO = orderMapper.toDTO(order);
                    orderDTO.setCustomerId(order.getCustomer().getCustomerId());

                    List<Integer> bookIDs = order.getBooks()
                        .stream()
                        .map(Book::getBookId)
                        .collect(Collectors.toList());

                    orderDTO.setBookIDs(bookIDs);

                    return orderDTO;
                })
                .collect(Collectors.toList());
    
            savedCustomerDTO.setOrders(orderDTOs);
        }
        return savedCustomerDTO;
    }

    @Override
    public List<CustomerDTO> getAll() {

        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
            .map(customer -> {
                CustomerDTO customerDTO = customerMapper.toDTO(customer);

                List<OrderDTO> orderDTOs = customer.getOrders() != null

                    ? customer.getOrders().stream()
                        .map(order -> {

                            OrderDTO orderDTO = orderMapper.toDTO(order);
                            orderDTO.setCustomerId(order.getCustomer().getCustomerId());

                            List<Integer> bookIDs = order.getBooks() != null

                                ? order.getBooks().stream()
                                    .map(Book::getBookId)
                                    .collect(Collectors.toList())

                                : new ArrayList<>();

                            orderDTO.setBookIDs(bookIDs);

                            return orderDTO;
                        })
                        .collect(Collectors.toList())
                    : new ArrayList<>();

                customerDTO.setOrders(orderDTOs);

                return customerDTO;
            })
            .collect(Collectors.toList());
    }


    @Override
    public CustomerDTO getById(int id) {

        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        CustomerDTO customerDTO = customerMapper.toDTO(customer);

        List<OrderDTO> orderDTOs = customer.getOrders() != null

            ? customer.getOrders().stream()
                .map(order -> {

                    OrderDTO orderDTO = orderMapper.toDTO(order);
                    orderDTO.setCustomerId(order.getCustomer().getCustomerId());

                    List<Integer> bookIDs = order.getBooks() != null

                        ? order.getBooks()

                            .stream()
                            .map(Book::getBookId)
                            .collect(Collectors.toList())

                        : new ArrayList<>();

                    orderDTO.setBookIDs(bookIDs);

                    return orderDTO;
                })
                .collect(Collectors.toList())

            : new ArrayList<>();

        customerDTO.setOrders(orderDTOs);

        return customerDTO;
    }

    @Override
    public CustomerDTO updateById(int id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerEmail(customerDTO.getCustomerEmail());
        customer.setCustomerAddress(customerDTO.getCustomerAddress());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        if (customerDTO.getOrders() != null) {
        
            List<BookOrder> customerOrders = customer.getOrders();
            if (customerOrders == null) {
                customerOrders = new ArrayList<>();
                customer.setOrders(customerOrders);
            } else {
                customerOrders.clear();
            }

            List<Integer> allBookIds = new ArrayList<>();
            for (OrderDTO orderDTO : customerDTO.getOrders()) {
                allBookIds.addAll(orderDTO.getBookIDs());
            }

            List<Book> books = bookRepository.findAllById(allBookIds);

            List<Integer> foundBookIds = new ArrayList<>();
            for (Book book : books) {
                foundBookIds.add(book.getBookId());
            }
            for (Integer bookId : allBookIds) {
                if (!foundBookIds.contains(bookId)) {
                    throw new EntityNotFoundException("Book not found with ID: " + bookId);
                }
            }

            for (OrderDTO orderDTO : customerDTO.getOrders()) {
                BookOrder bookOrder = orderMapper.toEntity(orderDTO);
                bookOrder.setCustomer(customer);

                List<Book> orderBooks = new ArrayList<>();
                for (Integer bookId : orderDTO.getBookIDs()) {
                    for (Book book : books) {
                        if (book.getBookId() == bookId) {
                            orderBooks.add(book);
                            break;
                        }
                    }
                }
                bookOrder.setBooks(orderBooks);

                customerOrders.add(bookOrder);
            }
        }

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