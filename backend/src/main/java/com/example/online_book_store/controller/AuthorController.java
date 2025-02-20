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

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.service.AuthorService;


@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorDTO1 = authorService.createAuthorWithBooks(authorDTO);
        return new ResponseEntity<>(authorDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/name/{authorName}")
    public ResponseEntity<?> getAuthorByName(@PathVariable String authorName) {
        List<AuthorDTO> authorDTOs = authorService.getByName(authorName);
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<AuthorDTO> authorDTOs = authorService.getAllAuthorsWithBooks(page, size);
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getByIdWithBooks(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorDTO1 = authorService.updateById(id, authorDTO);
        return new ResponseEntity<>(authorDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
