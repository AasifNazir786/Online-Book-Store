package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.BookRepository;

@Service
public class BookService{
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getById(int id){
        Book book = bookRepository.findById(id).orElse(null);
        return book;
    }

    public Book createBook(Book book){
        Book book2 = bookRepository.save(book);
        return book2;
    }

    public Book updateBook(int id, Book newBook){
        Book existingBook = bookRepository.findById(id).orElse(null);
        
            existingBook.setBookTitle(newBook.getBookTitle());
            existingBook.setBookStock(newBook.getBookStock());
            existingBook.setBookPrice(newBook.getBookPrice());
            existingBook.setAuthor(newBook.getAuthor());
            return bookRepository.save(existingBook);
    }

    public void deleteBook(int id){
        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook != null) {
            bookRepository.delete(existingBook);
        } else {
            System.out.println( "Book with id " + id + " not found");
            
        }
    }
}