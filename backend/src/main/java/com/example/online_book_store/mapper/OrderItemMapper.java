package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.online_book_store.dto.OrderItemDTO;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.Order;
import com.example.online_book_store.model.OrderItem;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel="spring")
public abstract class OrderItemMapper {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;
    
    @Mapping(target="order", source="orderId", qualifiedByName="mapOrder")
    @Mapping(target="book", source="bookId", qualifiedByName="mapBook")
    public abstract OrderItem toEntity(OrderItemDTO dto);

    @Mapping(target="orderId", source = "order.id")
    @Mapping(target="bookId", source="book.id")
    public abstract OrderItemDTO toDTO(OrderItem orderItem);
    
    @Named("mapOrder")
    protected Order mapOrder(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
    }

    @Named("mapBook")
    protected Book mapBook(Long bookId){
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
    }
}
