package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.model.BookOrder;

@Mapper(componentModel="spring")
public interface OrderMapper {

    @Mapping(source="customer.customerId", target="customerId")
    @Mapping(target="booksIDs", expression="java(order.getBooks().stream().map(book -> book.getBookId()).toList())")
    OrderDTO toDTO(BookOrder order);
    
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "books", ignore = true)
    BookOrder toEntity(OrderDTO orderDTO);
    
}
