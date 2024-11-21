package com.example.online_book_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO){

        if(bookDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        BookDTO bookDTO1 = bookService.create(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks(){

        List<BookDTO> bookDTOs = bookService.getAll();
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable int id){

        BookDTO bookDTO = bookService.getById(id);
        return  new ResponseEntity<>(bookDTO, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO){

        BookDTO bookDTO1 = bookService.updateById(id, bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@PathVariable int id){

        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
