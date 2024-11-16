package com.example.online_book_store.controller;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//        System.out.println("Received BookDTO: " + bookDTO);

        BookDTO bookDTO1 = bookService.createBookDTO(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        List<BookDTO> bookDTOs = bookService.getAllBookDTOs();
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable int id){
        BookDTO bookDTO = bookService.getBookDTOById(id);
        return  new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO){
        BookDTO bookDTO1 = bookService.updateBookDTO(id, bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        bookService.deleteBookDTO(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

// import com.example.online_book_store.model.Book;
// import com.example.online_book_store.service.BookService;


// @RestController
// @RequestMapping("/api/books")
// public class BookController {
//     @Autowired
//     private BookService bookService;

//     @GetMapping
//     public ResponseEntity<List<Book>> getAllBooks() {
//         List<Book> books = bookService.getAllBooks();
//         return ResponseEntity.ok(books);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Book> getBookById(@PathVariable int id) {
//         Book book = bookService.getById(id);
//         return ResponseEntity.ok(book);
//     }

//     @PostMapping
//     public ResponseEntity<Book> createBook(@RequestBody Book book) {
//         Book createdBook = bookService.createBook(book);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
//     }

//     @PostMapping("/list")
//     public ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> books) throws Exception {
//         if (books.isEmpty()) {
//             return ResponseEntity.badRequest().build();
//         }
//         List<Book> createdBooks = bookService.createBooks(books);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdBooks);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
//         Book updatedBook = bookService.updateBook(id, book);
//         return ResponseEntity.ok(updatedBook);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteById(@PathVariable int id) {
//         bookService.deleteBook(id);
//         return ResponseEntity.noContent().build();
//     }
// }
