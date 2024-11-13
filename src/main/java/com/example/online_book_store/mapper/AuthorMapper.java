package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.model.Author;

@Mapper(componentModel="spring")
public interface AuthorMapper {

    @Mapping(source = "authorBiography", target = "authorBio")
    AuthorDTO toDTO(Author author);

    @Mapping(source="authorBio", target="authorBiography")
    Author toEntity(AuthorDTO authorDTO);
}