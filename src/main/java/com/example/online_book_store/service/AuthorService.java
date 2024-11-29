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

@Service
public class AuthorService implements AuthorRepo {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public AuthorDTO create(AuthorDTO authorDTO) {

        Author author = authorMapper.toEntity(authorDTO);

        if (authorDTO.getBooks() != null) {
            List<Book> books = authorDTO.getBooks()
                .stream()
                .map(bookDTO -> {
                    Book book = bookRepository.findById(bookDTO.getBookId())
                        .orElseGet(() -> bookMapper.toEntity(bookDTO));
                        book.setAuthor(author);
                        return book;
                })
                .collect(Collectors.toList());

            author.setBooks(books);
        }
        Author savedAuthor = authorRepository.save(author);

        AuthorDTO savedAuthorDTO = authorMapper.toDTO(savedAuthor);

        List<BookDTO> bookDTOs = savedAuthor.getBooks()
            .stream()
            .map(book -> {
                BookDTO bookDTO = bookMapper.toDTO(book);
                bookDTO.setAuthorId(book.getAuthor().getAuthorId());
                return bookDTO;
            })
            .collect(Collectors.toList());

        savedAuthorDTO.setBooks(bookDTOs);

        return savedAuthorDTO;
    }

    @Override
    public List<AuthorDTO> getAll() {
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        // List<Author> authors = null;
        try {
            List<Author> authors = authorRepository.findAll();
            if (authors != null && !authors.isEmpty()) {
                for (Author author : authors) {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);

                    List<BookDTO> bookDTOs;
                    if (author.getBooks() != null && !author.getBooks().isEmpty()) {
                        bookDTOs = author.getBooks()
                            .stream()
                            .map(book ->{
                                BookDTO bookDTO = bookMapper.toDTO(book);
                                bookDTO.setAuthorId(author.getAuthorId());
                                return bookDTO;
                            })
                            .collect(Collectors.toList());
                        
                        authorDTO.setBooks(bookDTOs);
                    }
                    authorDTOs.add(authorDTO);
                }
            } else {
                System.out.println("No authors found.");
            }
        } catch (Exception e) {
            System.out.println("Error fetching authors: " + e.getMessage());
        }
        return authorDTOs;
    }

    @Override
    public AuthorDTO getById(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author Not found with id: " + id));

        AuthorDTO authorDTO = authorMapper.toDTO(author);

        List<BookDTO> bookDTOs;

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {

            bookDTOs = author.getBooks()
                .stream()
                .map(book -> {
                    BookDTO bookDTO = bookMapper.toDTO(book);
                    bookDTO.setAuthorId(book.getAuthor().getAuthorId());
                    return bookDTO;
                })
                .collect(Collectors.toList());
        
            authorDTO.setBooks(bookDTOs);
        }
        return authorDTO;
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

        if (authorDTO.getBooks() != null) {
            List<Book> books = authorDTO.getBooks()
                .stream()
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

                    Author bookAuthor = authorRepository.findById(bookDTO.getAuthorId()).orElse(author);
                    
                        book.setAuthor(bookAuthor);

                    return book;
                })
                .collect(Collectors.toList());

            author.setBooks(books);
        }

        Author savedAuthor = authorRepository.save(author);

        AuthorDTO savedAuthorDTO = authorMapper.toDTO(savedAuthor);

        List<BookDTO> bookDTOs = savedAuthor.getBooks()
            .stream()
            .map(book -> {

            BookDTO bookDTO = bookMapper.toDTO(book);
            bookDTO.setAuthorId(book.getAuthor().getAuthorId());

            return bookDTO;
            
        }).collect(Collectors.toList());

        savedAuthorDTO.setBooks(bookDTOs);

        return savedAuthorDTO;
    }

    // @Override
    // public AuthorDTO updateById(int id, AuthorDTO authorDTO) {
    //     Author author = authorRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

    //     author.setAuthorName(authorDTO.getAuthorName());
    //     author.setAuthorBiography(authorDTO.getAuthorBiography());
    //     author.setNationality(authorDTO.getNationality());
    //     author.setAwards(authorDTO.getAwards());

    //     if (authorDTO.getBooks() != null) {
    //         List<Book> books = new ArrayList<>();

    //         for (BookDTO bookDTO : authorDTO.getBooks()) {
    //             Book book;
    //             if (bookDTO.getBookId() != 0) {
    //                 book = bookRepository.findById(bookDTO.getBookId())
    //                         .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookDTO.getBookId()));

    //                 book.setBookTitle(bookDTO.getBookTitle());
    //                 book.setBookPrice(bookDTO.getBookPrice());
    //                 book.setBookStock(bookDTO.getBookStock());
    //                 book.setGenre(bookDTO.getGenre());
    //                 book.setPublicationDate(bookDTO.getPublicationDate());

    //                 Author bookAuthor = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
    //                 if (bookAuthor != null) {
    //                     book.setAuthor(bookAuthor);
    //                 } else {
    //                     throw new EntityNotFoundException("Author not found with name: " + bookDTO.getAuthorId());
    //                 }

    //             } else {
    //                 book = bookMapper.toEntity(bookDTO);

    //                 Author bookAuthor = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
    //                 if (bookAuthor != null) {
    //                     book.setAuthor(bookAuthor);
    //                 } else {
    //                     throw new EntityNotFoundException("Author not found with name: " + bookDTO.getAuthorId());
    //                 }
    //             }

    //             book.setAuthor(author);
    //             books.add(book);
    //         }

    //         author.setBooks(books);
    //     }

    //     Author savedAuthor = authorRepository.save(author);

    //     AuthorDTO savedAuthorDTO = authorMapper.toDTO(savedAuthor);

    //     List<BookDTO> bookDTOs = new ArrayList<>();
    //     for (Book book : savedAuthor.getBooks()) {
    //         BookDTO bookDTO = bookMapper.toDTO(book);
    //         bookDTO.setAuthorId(book.getAuthor().getAuthorId());
    //         bookDTOs.add(bookDTO);
    //     }

    //     savedAuthorDTO.setBooks(bookDTOs);

    //     return savedAuthorDTO;
    // }


    @Override
    public void delete(int id) {
        if(!authorRepository.existsById(id)){
            throw new EntityNotFoundException("Author not found with id: " + id);
        }

        Author author = authorRepository.findById(id).orElse(null);

        if(author != null){
            for(Book book : author.getBooks()){
                bookRepository.deleteById(book.getBookId());
            }
            bookRepository.deleteAll(author.getBooks());
        }
        authorRepository.deleteById(id);
    }
    
}