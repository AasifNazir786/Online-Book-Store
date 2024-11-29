package com.example.online_book_store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.mapper.OrderMapper;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.model.Customer;
import com.example.online_book_store.repository.BookOrderRepository;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.service_repository.OrderRepo;


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

        Customer customer = dto.getCustomerId() != 0
            ? customerRepository.findById(dto.getCustomerId())
                .orElseThrow(()->new EntityNotFoundException("customer not found with id: "+dto.getCustomerId()))
            : null;

        bookOrder.setCustomer(customer);

        List<Book> books = dto.getBookIDs() != null
            ? bookRepository.findAllById(dto.getBookIDs())
            : null;
        if (books.size() != dto.getBookIDs().size()) {
            throw new EntityNotFoundException("Some books not found for IDs: " + dto.getBookIDs());
        }
        bookOrder.getBooks().clear();
        bookOrder.setBooks(books);

        BookOrder savedOrder = bookOrderRepository.save(bookOrder);
        OrderDTO savedDTO = orderMapper.toDTO(savedOrder);

        if (savedOrder.getCustomer() != null) {
            savedDTO.setCustomerId(savedOrder.getCustomer().getCustomerId());
        }

        List<Integer> bookIDs = savedOrder.getBooks()
                .stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        savedDTO.setBookIDs(bookIDs);

        return savedDTO;
    }

    @Override
    public List<OrderDTO> getAll() {

        List<BookOrder> orders = bookOrderRepository.findAll();

        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (BookOrder order : orders) {
            OrderDTO orderDTO = orderMapper.toDTO(order);

            if (order.getCustomer() != null) {
                orderDTO.setCustomerId(order.getCustomer().getCustomerId());
            }

            List<Integer> bookIDs = new ArrayList<>();

            if (order.getBooks() != null) {
                for (Book book : order.getBooks()) {
                    bookIDs.add(book.getBookId());
                }
            }
            orderDTO.setBookIDs(bookIDs);

            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }


    @Override
    public OrderDTO getById(int id) {

        BookOrder bookOrder = bookOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        OrderDTO orderDTO = orderMapper.toDTO(bookOrder);

        if (bookOrder.getCustomer() != null) {
            orderDTO.setCustomerId(bookOrder.getCustomer().getCustomerId());
        }

        List<Integer> bookIDs = new ArrayList<>();
        if (bookOrder.getBooks() != null) {
            for (Book book : bookOrder.getBooks()) {
                bookIDs.add(book.getBookId());
            }
        }
        orderDTO.setBookIDs(bookIDs);

        return orderDTO;
    }


    @Override
    public OrderDTO updateById(int id, OrderDTO dto) {

        BookOrder existingOrder = bookOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        existingOrder.setOrderDate(dto.getOrderDate());
        existingOrder.setQuantity(dto.getQuantity());

        if (dto.getCustomerId() != 0) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + dto.getCustomerId()));
            existingOrder.setCustomer(customer);
        }

        if (dto.getBookIDs() != null && !dto.getBookIDs().isEmpty()) {
            List<Book> books = bookRepository.findAllById(dto.getBookIDs());

            if (books.size() != dto.getBookIDs().size()) {
                throw new EntityNotFoundException("Some books not found for IDs: " + dto.getBookIDs());
            }

            existingOrder.setBooks(books);
        } else {
            existingOrder.setBooks(new ArrayList<>());
        }

        BookOrder updatedOrder = bookOrderRepository.save(existingOrder);

        OrderDTO updatedDTO = orderMapper.toDTO(updatedOrder);
        updatedDTO.setCustomerId(updatedOrder.getCustomer() != null ? updatedOrder.getCustomer().getCustomerId() : 0);

        List<Integer> bookIDs = updatedOrder.getBooks().stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());
        updatedDTO.setBookIDs(bookIDs);

        return updatedDTO;
    }


    @Override
    public void delete(int id) {

        BookOrder existingOrder = bookOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        bookOrderRepository.delete(existingOrder);
    }


}