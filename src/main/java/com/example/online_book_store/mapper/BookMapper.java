package com.example.online_book_store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;

@Mapper(componentModel="spring")
public interface BookMapper {

    // BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "author.authorName", target = "authorName")
    BookDTO toDTO(Book book);

    @Mapping(target = "bookId", ignore = true)
    @Mapping(source = "authorName", target = "author", qualifiedByName="authorFromAuthorName")
    Book toEntity(BookDTO bookDTO);

    @Named("authorFromAuthorName")
    default Author authorFromAuthorName(String authorName){
        if(authorName == null || authorName.trim().isEmpty()){
            return null;
        }
        Author author = new Author();
        author.setAuthorName(authorName);
        return author;
    }
}