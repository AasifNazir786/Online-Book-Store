package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Book;

@Mapper(componentModel="spring")
public interface BookMapper {
    BookDTO toDTO(Book book);
    
    Book toEntity(BookDTO bookDTO);
}