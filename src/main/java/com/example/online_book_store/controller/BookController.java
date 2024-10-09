package com.example.online_book_store.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_book_store.model.Book;
import com.example.online_book_store.service.BookService;


@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = bookService.getById(id);
        if(book != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book book2 = bookService.createBook(book);
        if(book2 != null){
            return ResponseEntity.ok(book2);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Book book2 = bookService.updateBook(id, book);
        return ResponseEntity.ok(book2);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        Book book = bookService.getById(id);
        if (book != null) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    
    }
}