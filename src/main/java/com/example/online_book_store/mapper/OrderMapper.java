package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.model.Order;
import com.example.online_book_store.model.User;
import com.example.online_book_store.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel="spring", uses={OrderItemMapper.class})
public abstract class OrderMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(target="userId", source = "user.id")
    public abstract OrderDTO toDTO(Order order);

    @Mapping(target="user", source = "userId", qualifiedByName = "mapUser")
    public abstract Order toEntity(OrderDTO orderDTO);

    @Named("mapUser")
    public User mapUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}