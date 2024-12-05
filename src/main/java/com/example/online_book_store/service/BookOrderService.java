package com.example.online_book_store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.mapper.OrderMapper;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.model.Customer;
import com.example.online_book_store.repository.BookOrderRepository;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.repository.CustomerRepository;
import com.example.online_book_store.service_repository.OrderRepo;

import jakarta.persistence.EntityNotFoundException;


@Service
public class BookOrderService implements OrderRepo{

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public OrderDTO create(OrderDTO dto) {

        BookOrder bookOrder = orderMapper.toEntity(dto);
        if(dto.getCustomerId() != 0)
            bookOrder.setCustomer(validateAndRetrieveCustomer(dto.getCustomerId()));

        if(dto.getBookIDs() != null || !dto.getBookIDs().isEmpty())
            bookOrder.setBooks(validateAndRetrieveBooks(dto.getBookIDs()));

        BookOrder savedOrder = bookOrderRepository.save(bookOrder);
        return mapOrderToDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAll() {
        return bookOrderRepository.findAll()
                .stream()
                .map(this::mapOrderToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getById(int id) {

        BookOrder bookOrder = bookOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        return mapOrderToDTO(bookOrder);
    }


    @Override
    public OrderDTO updateById(int id, OrderDTO dto) {

        BookOrder existingOrder = bookOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        existingOrder.setOrderDate(dto.getOrderDate());
        existingOrder.setQuantity(dto.getQuantity());
        if(dto.getCustomerId() != 0) {
            Customer customer = validateAndRetrieveCustomer(dto.getCustomerId());
            existingOrder.setCustomer(customer);
        }

        if(dto.getBookIDs() != null || !dto.getBookIDs().isEmpty()) {
            existingOrder.setBooks(validateAndRetrieveBooks(dto.getBookIDs()));
        }

        BookOrder updatedOrder = bookOrderRepository.save(existingOrder);

        return mapOrderToDTO(updatedOrder);
    }


    @Override
    public void delete(int id) {

        if (!bookOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        bookOrderRepository.deleteById(id);
    }

    // Helper functions for above Crud Operations...

    private OrderDTO mapOrderToDTO(BookOrder bookOrder) {
        OrderDTO orderDTO = orderMapper.toDTO(bookOrder);

        if (bookOrder.getCustomer() != null) {
            orderDTO.setCustomerId(bookOrder.getCustomer().getCustomerId());
        }
        orderDTO.setBookIDs(bookOrder.getBooks() == null
                ? new ArrayList<>()
                : bookOrder.getBooks()
                .stream()
                .map(Book::getBookId)
                .collect(Collectors.toList()));
        return orderDTO;
    }

    private List<Book> validateAndRetrieveBooks(List<Integer> bookIDs) {
        List<Book> books = bookRepository.findAllById(bookIDs);

        // Find missing book IDs
        List<Integer> missingBookIds = bookIDs
                .stream()
                .filter(id -> books.stream().noneMatch(book -> book.getBookId() == id))
                .toList();

        if (!missingBookIds.isEmpty()) {
            throw new EntityNotFoundException("Some books not found for IDs: " + missingBookIds);
        }
        return books;
    }

    private Customer validateAndRetrieveCustomer(int id){
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID: " + id);
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }
}