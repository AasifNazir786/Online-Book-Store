package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BookService  {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public BookDTO create(BookDTO bookDTO) {

        if (bookDTO == null) {
            throw new IllegalArgumentException("BookDTO cannot be null");
        }
        Book book = mapDtoToBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    @Transactional
    @CacheEvict(value="books", allEntries=true)
    public List<BookDTO> createList(List<BookDTO> list){
        if(list == null || list.isEmpty()){
            throw new IllegalArgumentException("BookDTO list cannot be null");
        }
        List<Book> books = list.stream().map(dto -> {
            Book book = mapDtoToBook(dto);
            return book;
        }).toList();
        List<Book> savedBooks = bookRepository.saveAll(books);

        return savedBooks.stream().map(book -> {
            BookDTO dto = bookMapper.toDTO(book);
            return dto;
        }).toList();
    }

    @Cacheable(value = "books")
    public Page<BookDTO> getAllByBookTitle(String bookTitle, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookTitle")
                .and(Sort.by(Sort.Direction.ASC, "bookPrice")));
        return bookRepository.findByBookTitle(bookTitle, pageable).map(bookMapper::toDTO);
    }

    @Cacheable(value = "books")
    public Page<BookDTO> getAllByBookPriceLessThan(double price, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookTitle")
                .and(Sort.by(Sort.Direction.ASC, "bookPrice")));
        return bookRepository.findByBookPriceLessThan(price, pageable).map(bookMapper::toDTO);
    }

    @Cacheable(value = "books")
    public Page<BookDTO> getAllByAuthorName(String authorName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookTitle")
                .and(Sort.by(Sort.Direction.ASC, "bookPrice")));
        return bookRepository.findByAuthor_AuthorNameContainingIgnoreCase(authorName, pageable).map(bookMapper::toDTO);
    }

    @Cacheable(value = "books")
    public Page<BookDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookTitle")
                .and(Sort.by(Sort.Direction.ASC, "bookPrice")));
        return bookRepository.findAll(pageable).map(bookMapper::toDTO);
    }

    @Cacheable(value = "books", key = "#id")
    public BookDTO getById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Book Not found with id: "+id)
                );
        return bookMapper.toDTO(book);
    }

    @Transactional
    @CacheEvict(value = "books", allEntries = true)
    public BookDTO updateById(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book Not found with id: " + id));

        Author author = (dto.getAuthorId() != null)
                ? authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author Not found with id: " + dto.getAuthorId()))
                : null;

        book.setAuthor(author);
        book.setBookPrice(dto.getBookPrice());
        book.setBookStock(dto.getBookStock());
        book.setBookTitle(dto.getBookTitle());
        book.setGenre(dto.getGenre());
        book.setImageUrl(dto.getImageUrl());
        book.setPublicationDate(dto.getPublicationDate());

        return bookMapper.toDTO(bookRepository.save(book));
    }


    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Helper functions for above methods
    private Book mapDtoToBook(BookDTO bookDTO){
        Book book = bookMapper.toEntity(bookDTO);
        try {
            Author author = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Author Not Found with id: "+bookDTO.getAuthorId())
                    );
            book.setAuthor(author);

        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
        return book;
    }
}