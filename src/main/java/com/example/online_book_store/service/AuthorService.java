package com.example.online_book_store.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.mapper.AuthorMapper;
import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;
import com.example.online_book_store.service_repository.AuthorRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService implements AuthorRepo {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public AuthorDTO create(AuthorDTO authorDTO) {

        Author author = authorMapper.toEntity(authorDTO);
        if (authorDTO.getBooks() != null) {

            List<Book> books = mapBookDTOsToBooks(authorDTO.getBooks(), author);
            author.setBooks(books);
        }
        Author savedAuthor = authorRepository.save(author);

        return mapAuthorToDTOWithBooks(savedAuthor);
    }

    @Override
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapAuthorToDTOWithBooks)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getById(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author Not found with id: " + id));

        return mapAuthorToDTOWithBooks(author);
    }

    @Override
    public AuthorDTO updateById(int id, AuthorDTO authorDTO) {

        Author author = authorRepository.findById(id)
                .orElseGet(() -> {
                    Author newAuthor = authorMapper.toEntity(authorDTO);
                    newAuthor.setAuthorId(id);
                    return newAuthor;
                });

        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorBiography(authorDTO.getAuthorBiography());
        author.setNationality(authorDTO.getNationality());
        author.setAwards(authorDTO.getAwards());

        if (authorDTO.getBooks() != null && !authorDTO.getBooks().isEmpty()) {
            List<Book> books = mapBookDTOsToBooks(authorDTO.getBooks(), author);
            author.setBooks(books);
        }
        Author savedAuthor = authorRepository.save(author);
        return mapAuthorToDTOWithBooks(savedAuthor);
    }

    @Override
    public void delete(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author); // Assuming cascade delete is configured for books
    }

    //Helper functions for above CRUD operations
    private List<Book> mapBookDTOsToBooks(List<BookDTO> bookDTOs, Author author) {
        return bookDTOs.stream()
                .map(bookDTO -> {
                    Book book = bookDTO.getBookId() != 0
                        ? bookRepository.findById(bookDTO.getBookId())
                            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookDTO.getBookId()))
                        : bookMapper.toEntity(bookDTO);
                    book.setBookTitle(bookDTO.getBookTitle());
                    book.setBookPrice(bookDTO.getBookPrice());
                    book.setBookStock(bookDTO.getBookStock());
                    book.setGenre(bookDTO.getGenre());
                    book.setPublicationDate(bookDTO.getPublicationDate());
                    book.setAuthor(author);
                    return book;
                })
                .collect(Collectors.toList());
    }


    private List<BookDTO> mapBooksToBookDTOs(List<Book> books) {
        return books.stream()
                .map(book -> {
                    BookDTO bookDTO = bookMapper.toDTO(book);

                    if(book.getAuthor() != null)
                        bookDTO.setAuthorId(book.getAuthor().getAuthorId());
                    bookDTO.setBookPrice(book.getBookPrice());
                    return bookDTO;
                })
                .collect(Collectors.toList());
    }

    private AuthorDTO mapAuthorToDTOWithBooks(Author author) {

        AuthorDTO authorDTO = authorMapper.toDTO(author);

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            List<BookDTO> bookDTOs = mapBooksToBookDTOs(author.getBooks());
            authorDTO.setBooks(bookDTOs);
        }
        return authorDTO;
    }

}