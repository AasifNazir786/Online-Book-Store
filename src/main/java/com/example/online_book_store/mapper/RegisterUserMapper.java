package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.online_book_store.dto.RegisterUser;
import com.example.online_book_store.model.User;

@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    RegisterUserMapper INSTANCE = Mappers.getMapper(RegisterUserMapper.class);
    /*
        No need to add these fields if entity fieldname and dto fieldname is same........
        @Mapping(source = "fullName", target = "fullName")
        @Mapping(source = "username", target = "username")
        @Mapping(source = "password", target = "password")
        @Mapping(source = "email", target = "email")
        @Mapping(source = "phoneNumber", target = "phoneNumber")
        @Mapping(source = "role", target = "role")
    */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "resetToken", ignore = true)
    @Mapping(target = "resetTokenExpiry", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "cartList", ignore = true)
    User toEntity(RegisterUser dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "role", target = "role")
    RegisterUser toDTO(User user);

}
