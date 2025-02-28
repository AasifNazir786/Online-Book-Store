package com.example.online_book_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.dto.BookWithAuthorDTO;
import com.example.online_book_store.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO bookDTO1 = bookService.create(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<BookDTO>> createListBooks(@RequestBody List<BookDTO> listBooks){
        List<BookDTO> books = bookService.createList(listBooks);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/byTitle")
    public ResponseEntity<Page<BookDTO>> getAllBooks(
        @RequestParam String bookTitle,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookDTO> bookDTOs = bookService.getAllByBookTitle(bookTitle, page, size);
        return ResponseEntity.ok(bookDTOs);
    }

    @GetMapping("/price")
    public ResponseEntity<Page<BookDTO>> getByPriceLessThan(
        @RequestParam double price,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
    Page<BookDTO> bookDTOS = bookService.getAllByBookPriceLessThan(price, page, size);
    return ResponseEntity.ok(bookDTOS);
    }

    @GetMapping("/name/{authorName}")
    public ResponseEntity<Page<BookDTO>> getAllByAuthorName(
        @PathVariable String authorName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Page<BookDTO> bookDTOS = bookService.getAllByAuthorName(authorName, page, size);
        return ResponseEntity.ok(bookDTOS);
    }

    @PutMapping("/updateList")
    public ResponseEntity<List<BookDTO>> updateListBooks(@RequestBody List<BookDTO> listBooks){
        List<BookDTO> books = bookService.updateList(listBooks);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BookWithAuthorDTO>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<BookWithAuthorDTO> bookDTOS = bookService.getAll(page, size);
        return ResponseEntity.ok(bookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long bookId){
        return new ResponseEntity<>(bookService.getById(bookId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        BookDTO  updatedBook = bookService.updateById(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
