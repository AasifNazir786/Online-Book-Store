package com.example.online_book_store.service;

import java.util.ArrayList;
import java.util.List;

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
        Author author = authorRepository.findByAuthorName(authorDTO.getAuthorName());

        if (author == null) {
            author = authorMapper.toEntity(authorDTO);
        }

        List<Book> books = new ArrayList<>();
        if (authorDTO.getBooks() != null) {
            for (BookDTO bookDTO : authorDTO.getBooks()) {
                Book existingBook = bookRepository.findById(bookDTO.getBookId()).orElse(null);
                if(existingBook == null) {
                    Book book = bookMapper.toEntity(bookDTO);
                    book.setAuthor(author);
                    books.add(book);
                }else{
                    existingBook.setAuthor(author);
                    books.add(existingBook);
                }
            }
        }
        author.setBooks(books);
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO savedAuthorDTO = authorMapper.toDTO(savedAuthor);

        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : savedAuthor.getBooks()) {
            BookDTO bookDTO = bookMapper.toDTO(book);
            bookDTO.setAuthorId(book.getAuthor().getAuthorId());
            bookDTOs.add(bookDTO);
        }

        savedAuthorDTO.setBooks(bookDTOs);

        return savedAuthorDTO;
    }

    @Override
    public List<AuthorDTO> getAllAuthorDTOs() {
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        List<Author> authors = null;
        try {
            authors = authorRepository.findAll();
            if (authors != null && !authors.isEmpty()) {
                for (Author author : authors) {
                    AuthorDTO authorDTO = authorMapper.toDTO(author);

                    List<BookDTO> bookDTOs = new ArrayList<>();
                    if (author.getBooks() != null && !author.getBooks().isEmpty()) {
                        for (Book book : author.getBooks()) {

                            BookDTO bookDTO = bookMapper.toDTO(book);
                            bookDTO.setAuthorId(author.getAuthorId());

                            bookDTOs.add(bookDTO);
                        }
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
    public AuthorDTO getAuthorDTOById(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author Not found with id: " + id));

        AuthorDTO authorDTO = authorMapper.toDTO(author);

        List<BookDTO> bookDTOs = new ArrayList<>();

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            for (Book book : author.getBooks()) {
                BookDTO bookDTO = bookMapper.toDTO(book);

                bookDTO.setAuthorId(author.getAuthorId());

                bookDTOs.add(bookDTO);
            }
            authorDTO.setBooks(bookDTOs);
        }
        return authorDTO;
    }


    @Override
    public AuthorDTO updateAuthorDTO(int id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorBiography(authorDTO.getAuthorBiography());
        author.setNationality(authorDTO.getNationality());
        author.setAwards(authorDTO.getAwards());

        if (authorDTO.getBooks() != null) {
            List<Book> books = new ArrayList<>();

            for (BookDTO bookDTO : authorDTO.getBooks()) {
                Book book;
                if (bookDTO.getBookId() != 0) {
                    book = bookRepository.findById(bookDTO.getBookId())
                            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookDTO.getBookId()));

                    book.setBookTitle(bookDTO.getBookTitle());
                    book.setBookPrice(bookDTO.getBookPrice());
                    book.setBookStock(bookDTO.getBookStock());
                    book.setGenre(bookDTO.getGenre());
                    book.setPublicationDate(bookDTO.getPublicationDate());

                    Author bookAuthor = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
                    if (bookAuthor != null) {
                        book.setAuthor(bookAuthor);
                    } else {
                        throw new EntityNotFoundException("Author not found with name: " + bookDTO.getAuthorId());
                    }

                } else {
                    book = bookMapper.toEntity(bookDTO);

                    Author bookAuthor = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
                    if (bookAuthor != null) {
                        book.setAuthor(bookAuthor);
                    } else {
                        throw new EntityNotFoundException("Author not found with name: " + bookDTO.getAuthorId());
                    }
                }

                book.setAuthor(author);
                books.add(book);
            }

            author.setBooks(books);
        }

        Author savedAuthor = authorRepository.save(author);

        AuthorDTO savedAuthorDTO = authorMapper.toDTO(savedAuthor);

        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : savedAuthor.getBooks()) {
            BookDTO bookDTO = bookMapper.toDTO(book);
            bookDTO.setAuthorId(book.getAuthor().getAuthorId());
            bookDTOs.add(bookDTO);
        }

        savedAuthorDTO.setBooks(bookDTOs);

        return savedAuthorDTO;
    }


    @Override
    @Transactional
    public void deleteAuthorDTO(int id) {

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