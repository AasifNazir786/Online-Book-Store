package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getById(int id){
        Book book = bookRepository.findById(id).orElse(null);
        return book;
    }

    public Book createBook(Book book) {
        if (book == null || book.getBookTitle() == null || book.getBookTitle().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthor() != null) {
            Author author = authorRepository.findById(book.getAuthor().getAuthorId()).orElse(null);
            if (author != null) {
                book.setAuthor(author);
            } else {
                throw new EntityNotFoundException("Author not found with ID: " + book.getAuthor().getAuthorId());
            }
        }
        return bookRepository.save(book);
    }

    public List<Book> createBooks(List<Book> books) throws Exception {
        if(books == null || books.isEmpty()){
            throw new Exception("books can't be Null or Empty");
        }
        for (Book book : books) {
            Author author = book.getAuthor();
            if (author != null) {
                Author existingAuthor = authorRepository.findById(author.getAuthorId()).orElse(null);
                if (existingAuthor == null) {
                    System.out.println("Author not found with ID: " + author.getAuthorId());
                    continue;
                }
                book.setAuthor(existingAuthor);
            }
        }
        return bookRepository.saveAll(books);
    }
    
    public Book updateBook(int bookId, Book updatedBook) {
        // Fetch the existing book
        Book existingBook = bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    
        // Update the properties
        existingBook.setBookTitle(updatedBook.getBookTitle());
        existingBook.setBookStock(updatedBook.getBookStock());
        existingBook.setBookPrice(updatedBook.getBookPrice());
        Author author = authorRepository.findById(updatedBook.getAuthor().getAuthorId())
            .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    
        existingBook.setAuthor(author);
    
        return bookRepository.save(existingBook);
    }
    

    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    public BookDTO bookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setBookTitle(book.getBookTitle());
        bookDTO.setBookPrice(book.getBookPrice());
        bookDTO.setBookStock(book.getBookStock());
        bookDTO.setAuthorName(book.getAuthor().getAuthorName());
        return bookDTO;
    }
}