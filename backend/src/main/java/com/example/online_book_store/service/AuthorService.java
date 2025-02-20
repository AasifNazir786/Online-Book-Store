package com.example.online_book_store.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.mapper.AuthorMapper;
import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    BookMapper bookMapper;

    @Transactional
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorDTO createAuthorWithBooks(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        return authorMapper.toDTO(authorRepository.save(author));
    }

    @Cacheable(value = "authors", key = "#authorName")
    public List<AuthorDTO> getByName(String authorName) {
        return authorRepository.findByAuthorName(authorName)
                        .stream().map(authorMapper::toDTO).toList();
    }

    @Cacheable(value = "authors")
    public Page<AuthorDTO> getAllAuthorsWithBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                        Sort.by(Sort.Direction.ASC, "authorName"));
        return authorRepository.findAll(pageable).map(authorMapper::toDTO);
    }

    @Cacheable(value = "authors", key = "#id")
    public AuthorDTO getByIdWithBooks(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        return authorMapper.toDTO(author);
    }

    @Transactional
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public AuthorDTO updateById(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorBiography(authorDTO.getAuthorBiography());
        author.setNationality(authorDTO.getNationality());
        author.setAwards(authorDTO.getAwards());

        if (authorDTO.getBooks() != null && !authorDTO.getBooks().isEmpty()) {
            List<Book> books = mapBookDTOsToBooks(authorDTO.getBooks(), author);
            author.setBooks(books);
        }

        return authorMapper.toDTO(authorRepository.save(author));
    }

    @Transactional
    @CacheEvict(cacheNames = "authors", allEntries = true)
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

    //Helper functions for above CRUD operations
    private List<Book> mapBookDTOsToBooks(List<BookDTO> bookDTOs, Author author) {
        return bookDTOs.stream()
            .map(bookDTO -> {
                Book book = bookDTO.getId() != null
                    ? bookRepository.findById(bookDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookDTO.getId()))
                    : bookMapper.toEntity(bookDTO);
                book.setBookTitle(bookDTO.getBookTitle());
                book.setBookPrice(bookDTO.getBookPrice());
                book.setBookStock(bookDTO.getBookStock());
                book.setGenre(bookDTO.getGenre());
                book.setImageUrl(bookDTO.getImageUrl());
                book.setPublicationDate(bookDTO.getPublicationDate());
                book.setImageUrl(bookDTO.getImageUrl());
                book.setAuthor(author);
                return book;
            }).collect(Collectors.toList());
    }


//    private List<BookDTO> mapBooksToBookDTOs(List<Book> books) {
//        return books.stream()
//                .map(book -> {
//                    return bookMapper.toDTO(book);
//                }).collect(Collectors.toList());
//    }

//    private AuthorDTO mapAuthorToDTOWithBooks(Author author) {
//
//        AuthorDTO authorDTO = authorMapper.toDTO(author);
//
//        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
//            List<BookDTO> bookDTOs = mapBooksToBookDTOs(author.getBooks());
//            authorDTO.setBooks(bookDTOs);
//        }
//        return authorDTO;
//    }

}