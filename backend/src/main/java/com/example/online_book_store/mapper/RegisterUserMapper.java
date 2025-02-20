package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.RegisterUser;
import com.example.online_book_store.model.User;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "resetToken", ignore = true)
    @Mapping(target = "resetTokenExpiry", ignore = true)
    @Mapping(target = "orders", ignore = true)
    User toEntity(RegisterUser dto);

    RegisterUser toDTO(User user);
}
