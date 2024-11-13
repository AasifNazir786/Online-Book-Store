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

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.repository.BookOrderRepository;
import com.example.online_book_store.service.AuthorService;



@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookOrderRepository bookOrderRepository;


    @PostMapping("/create")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO){
        if(authorDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AuthorDTO author = authorService.createAuthorDTO(authorDTO);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/all")    
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        List<AuthorDTO> authors = authorService.getAllAuthorDTOs();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable int id){
        AuthorDTO author = authorService.getAuthorDTOById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable int id, @RequestBody AuthorDTO author){
        AuthorDTO authorDTO = authorService.updateAuthorDTO(id, author);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable int id){
        authorService.deleteAuthorDTO(id);
    }

}

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.online_book_store.dto.AuthorDTO;
// import com.example.online_book_store.model.Author;
// import com.example.online_book_store.service.AuthorService;

// @RestController
// @RequestMapping("/api/authors")
// public class AuthorController {
//     @Autowired
//     private AuthorService authorService;

//     @GetMapping
//     public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
//         return ResponseEntity.ok(authorService.getAllAuthors());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<AuthorDTO> getById(@PathVariable int id) {
//         AuthorDTO author = authorService.getById(id);
//         return ResponseEntity.ok(author);
//     }

//     @PostMapping
//     public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {
//         AuthorDTO createdAuthor = authorService.authorToAuthorDTO(author);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
//     }

//     @PostMapping("/list")
//     public ResponseEntity<List<AuthorDTO>> createListAuthors(@RequestBody List<Author> authors) throws Exception{
//         if(authors == null || authors.isEmpty()){
//             throw new Exception("Unable to create Authors");
//         }
//         List<AuthorDTO> authors2 = authorService.createListAuthor(authors);
//         return new ResponseEntity<>(authors2, HttpStatus.CREATED);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
//         Author updatedAuthor = authorService.updateAuthor(id, author);
//         return ResponseEntity.ok(updatedAuthor);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteAuthor(@PathVariable int id) {
//         authorService.deleteAuthor(id);
//         return ResponseEntity.noContent().build();
//     }
// }
