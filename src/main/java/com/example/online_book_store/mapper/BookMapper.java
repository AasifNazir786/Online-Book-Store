package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Book;

@Mapper(componentModel="spring")
public interface BookMapper {

    @Mapping(target = "authorId", expression="java(getId(book))")
    BookDTO toDTO(Book book);

    @Mapping(target = "author", ignore=true)
    Book toEntity(BookDTO bookDTO);

    default Long getId(Book book){
        return (book.getAuthor() != null)
                    ? book.getAuthor().getId()
                    : null;
    }
}