package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.model.Author;

@Mapper(componentModel="spring", uses={BookMapper.class})
public interface AuthorMapper {

   // AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
   @Mapping(source = "books", target = "books")
   AuthorDTO toDTO(Author author);

   @Mapping(source = "books", target = "books")
   @Mapping(target="authorId", ignore=true)
   Author toEntity(AuthorDTO authorDTO);
}