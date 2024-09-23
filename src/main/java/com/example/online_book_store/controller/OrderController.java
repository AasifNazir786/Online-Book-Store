package com.example.online_book_store.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_book_store.model.BookOrder;
import com.example.online_book_store.service.BookOrderService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private BookOrderService bookOrderService;
    
    @GetMapping
    public List<BookOrder> getAllOrders(){
        return bookOrderService.getAllOrders();
    }

    @GetMapping("/date/{orderDate}")
    public ResponseEntity<List<BookOrder>> getOrdersByDate(@PathVariable String orderDate) {
        LocalDate parsedDate = LocalDate.parse(orderDate); // Parse the date string
        List<BookOrder> orders = bookOrderService.getOrdersByDate(parsedDate);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No orders found
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public BookOrder getOrderById(@PathVariable int id) throws Exception{
        return bookOrderService.getOrderById(id);
    }

    @PostMapping
    public BookOrder createOrder(@RequestBody BookOrder order){
        return bookOrderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public BookOrder updateOrder(@PathVariable int id, @RequestBody BookOrder order){
        return bookOrderService.updatOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") int id) {
        bookOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}