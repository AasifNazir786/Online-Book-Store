package com.example.online_book_store.service;

import java.util.ArrayList;
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
import jakarta.transaction.Transactional;

@Service
public class AuthorService implements AuthorRepo {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired(required=true)
    private BookMapper bookMapper;

    @Override
    public AuthorDTO createAuthorDTO(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        // author.setAuthorName(authorDTO.getAuthorName());
        // author.setAuthorBiography(authorDTO.getAuthorBio());
        List<Book> books = new ArrayList<>();
        if(authorDTO.getBooks() != null){
            for(BookDTO bookDTO : authorDTO.getBooks()){
                Book book = bookMapper.toEntity(bookDTO);
                // book.setBookTitle(bookDTO.getBookTitle());
                // book.setBookPrice(bookDTO.getBookPrice());
                // book.setBookStock(bookDTO.getBookStock());
                book.setAuthor(author);
                books.add(book);
            }
        }
        author.setBooks(books);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @Override
    public List<AuthorDTO> getAllAuthorDTOs() {
    
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        try {
            List<Author> authors = authorRepository.findAll();
            authorDTOs = authors.stream().
                map(authorMapper::toDTO).
                collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return authorDTOs;
    }

    @Override
    public AuthorDTO getAuthorDTOById(int id) {
        Author author = authorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Author Not found with id: "+id));
        
        return authorMapper.toDTO(author);
    }

    @Override
    public AuthorDTO updateAuthorDTO(int id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Author Not found with id: "+id));
        
        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorBiography(authorDTO.getAuthorBio());

        if(authorDTO.getBooks() != null){
            List<Book> books = new ArrayList<>();
            for(BookDTO bookDTO : authorDTO.getBooks()){
                Book book;
                if (bookDTO.getBookId() != 0) {
                    book = bookRepository.findById(bookDTO.getBookId()).
                                    orElseGet(() -> bookMapper.toEntity(bookDTO));
                } else {
                    book = bookMapper.toEntity(bookDTO);
                }
                book.setAuthor(author);
                books.add(book);
            }
            author.getBooks().clear();
            author.getBooks().addAll(books);
        }
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthorDTO(int id) {
        if(!authorRepository.existsById(id)){
            throw new EntityNotFoundException("Author not found with id: " + id);
        }
        Author author = authorRepository.findById(id).orElse(null);
        for(Book book : author.getBooks()){
            bookRepository.deleteById(book.getBookId());
        }

        bookRepository.deleteAll(author.getBooks());
        authorRepository.deleteById(id);
    }
    
}