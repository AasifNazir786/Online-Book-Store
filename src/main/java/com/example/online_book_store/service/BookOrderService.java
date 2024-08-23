package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.repository.BookOrderRepository;


@Service
public class BookOrderService {

    @Autowired
    private BookOrderRepository BookOrderRepository;

    public List<BookOrder> getAllOrders(){
        return BookOrderRepository.findAll();
    }   

    public BookOrder getOrderById(int id) throws Exception {
        return BookOrderRepository.findById(id)
            .orElseThrow(() -> new Exception("BookOrder Not Found"));
    }

    public BookOrder createOrder(BookOrder order){
        return BookOrderRepository.save(order);
    }

    public BookOrder updatOrder(int id, BookOrder order){
        BookOrder existingOrder = BookOrderRepository.findById(id).orElse(null);
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setBook(order.getBook());
        return BookOrderRepository.save(existingOrder);
    }
    public void deleteOrder(int id){
        BookOrder order = BookOrderRepository.findById(id).orElse(null);
        BookOrderRepository.delete(order);
    }
}