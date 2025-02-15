package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.OrderItemDTO;
import com.example.online_book_store.model.OrderItem;

@Mapper(componentModel="spring")
public interface OrderItemMapper {
    
    @Mapping(target="order", ignore=true)
    @Mapping(target="book", ignore=true)
    OrderItem toEntity(OrderItemDTO dto);

    @Mapping(target="orderId", expression="java(getOrderIdFromOrder(orderItem))")
    @Mapping(target="bookId", expression="java(getBookIdFromBook(orderItem))")
    OrderItemDTO toDTO(OrderItem orderItem);

    default Long getOrderIdFromOrder(OrderItem orderItem){
        return (orderItem.getOrder() != null)
                ? orderItem.getOrder().getId()
                : null;
    }

    default Long getBookIdFromBook(OrderItem orderItem){
        return (orderItem.getBook() != null)
                ? orderItem.getBook().getId()
                : null;
    }
}
