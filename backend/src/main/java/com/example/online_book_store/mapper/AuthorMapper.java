package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.model.Author;

@Mapper(componentModel="spring", uses={BookMapper.class})
public interface AuthorMapper {

   @Mapping(target = "books", source = "books")
   AuthorDTO toDTO(Author author);

   @Mapping(target = "books", source = "books")
   Author toEntity(AuthorDTO authorDTO);
}