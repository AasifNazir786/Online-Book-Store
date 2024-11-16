package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.model.Author;

@Mapper(componentModel="spring")
public interface AuthorMapper {

   // AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

   AuthorDTO toDTO(Author author);

   Author toEntity(AuthorDTO authorDTO);
}