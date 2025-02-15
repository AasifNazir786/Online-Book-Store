package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.model.Order;

@Mapper(componentModel="spring", uses={OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target="userId", expression="java(extractIdFromUser(order))")
    @Mapping(source="orderItems", target="orderItems")
    OrderDTO toDTO(Order order);

    @Mapping(target="user", ignore=true)
    Order toEntity(OrderDTO orderDTO);

    default Long extractIdFromUser(Order order){
        return (order.getUser() != null)
            ? order.getUser().getId()
            : null;
    }
}