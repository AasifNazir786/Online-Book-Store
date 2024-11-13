// package com.example.online_book_store.controller;

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
