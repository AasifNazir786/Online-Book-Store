package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Book;

@Mapper(componentModel="spring")
public interface BookMapper {

    @Mapping(target = "authorId", ignore = true)
    BookDTO toDTO(Book book);

    @Mapping(target = "author", ignore=true)
    Book toEntity(BookDTO bookDTO);

    // @Named("authorFromAuthorId")
    // default Author authorFromAuthorId(int id){
    //     if(id == 0){
    //         return null;
    //     }
    //     Author author = new Author();
    //     author.setAuthorId(id);
    //     return author;
    // }
}