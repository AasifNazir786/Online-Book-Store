package com.example.online_book_store.mapper;

import com.example.online_book_store.model.User;
import com.example.online_book_store.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.CartDTO;
import com.example.online_book_store.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel="spring", uses={BookMapper.class})
public abstract class CartMapper {

    @Autowired
    private UserRepository userRepository;

    @Mapping(source="user.id", target="userId")
    public abstract CartDTO toDTO(Cart cart);

    @Mapping(target="user", expression = "java(getUser(dto.getUserId()))")
    public abstract Cart toEntity(CartDTO dto);

    protected User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
