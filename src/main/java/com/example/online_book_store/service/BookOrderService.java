package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.repository.BookOrderRepository;


@Service
public class BookOrderService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    public List<BookOrder> getAllOrders(){
        return bookOrderRepository.findAll();
    }

    public BookOrder getOrderById(int id) throws Exception {
        return bookOrderRepository.findById(id)
            .orElseThrow(() -> new Exception("BookOrder Not Found"));
    }

    public BookOrder createOrder(BookOrder order){
        return bookOrderRepository.save(order);
    }

    public BookOrder updatOrder(int id, BookOrder order){
        BookOrder existingOrder = bookOrderRepository.findById(id).orElse(null);
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setBooks(order.getBooks());
        return bookOrderRepository.save(existingOrder);
    }
    public void deleteOrder(int id){
        BookOrder order = bookOrderRepository.findById(id).orElse(null);
        bookOrderRepository.delete(order);
    }
}