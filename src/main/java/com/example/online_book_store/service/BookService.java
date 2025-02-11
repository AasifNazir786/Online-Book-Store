// package com.example.online_book_store.service;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.online_book_store.dto.BookDTO;
// import com.example.online_book_store.mapper.BookMapper;
// import com.example.online_book_store.model.Author;
// import com.example.online_book_store.model.Book;
// import com.example.online_book_store.repository.AuthorRepository;
// import com.example.online_book_store.repository.BookRepository;
// import com.example.online_book_store.service_repository.BookRepo;

// import jakarta.persistence.EntityNotFoundException;

// @Service
// public class BookService implements BookRepo {

//     @Autowired
//     private BookRepository bookRepository;

//     @Autowired
//     private AuthorRepository authorRepository;

//     @Autowired
//     private BookMapper bookMapper;

//     @Override
//     public BookDTO create(BookDTO bookDTO) {

//         if (bookDTO == null) {

//             throw new IllegalArgumentException("BookDTO cannot be null");
//         }
//         Book book = mapDtoToBook(bookDTO);
//         Book savedBook = bookRepository.save(book);
//         return mapBookToDTO(savedBook);
//     }

//     @Override
//     public List<BookDTO> getAll() {

//         return bookRepository.findAll()
//             .stream()
//             .map(this::mapBookToDTO)
//             .collect(Collectors.toList());
//     }

//     @Override
//     public BookDTO getById(int id) {

//         Book book = bookRepository.findById(id)
//                 .orElseThrow(() ->
//                     new EntityNotFoundException("Book Not found with id: "+id)
//                 );
//         return mapBookToDTO(book);
//     }

//     @Override
//     public BookDTO updateById(int id, BookDTO dto) {
        
//         Book book = bookRepository.findById(id)
//                                         .orElseGet(() -> {

//             Book newBook = bookMapper.toEntity(dto);
//             return newBook;
//         });
            
//         Author author = null;

//         if(dto.getAuthorId() != 0){

//             author = authorRepository.findById(dto.getAuthorId())
//                 .orElseThrow(()-> new EntityNotFoundException("Author Not found with id: "+dto.getAuthorId()));
//         }
//         book.setAuthor(author);
//         book.setBookPrice(dto.getBookPrice());
//         book.setBookStock(dto.getBookStock());
//         book.setBookTitle(dto.getBookTitle());
//         book.setGenre(dto.getGenre());
//         book.setPublicationDate(dto.getPublicationDate());

//         Book savedBook = bookRepository.save(book);

//         return mapBookToDTO(savedBook);
//     }

//     @Override
//     public void delete(int id) {
//         if (!bookRepository.existsById(id)) {
//             throw new EntityNotFoundException("Book not found with id: " + id);
//         }
//         bookRepository.deleteById(id);
//     }

//     // Helper functions for above methods
//     private BookDTO mapBookToDTO(Book book){
//         BookDTO bookDTO = bookMapper.toDTO(book);

//         if(book.getAuthor().getAuthorId() != 0)
//             bookDTO.setAuthorId(book.getAuthor().getAuthorId());
//         else
//             bookDTO.setAuthorId(0);
//         return bookDTO;
//     }

//     private Book mapDtoToBook(BookDTO bookDTO){
//         Book book = bookMapper.toEntity(bookDTO);
//         try {
//             Author author = authorRepository.findById(bookDTO.getAuthorId())
//                     .orElseThrow(() ->
//                             new EntityNotFoundException("Author Not Found with id: "+bookDTO.getAuthorId())
//                     );
//             book.setAuthor(author);

//         } catch (Exception e) {
//             throw new EntityNotFoundException(e);
//         }
//         return book;
//     }
// }
































// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import com.example.online_book_store.dto.BookDTO;
// // import com.example.online_book_store.model.Author;
// // import com.example.online_book_store.model.Book;
// // import com.example.online_book_store.repository.AuthorRepository;
// // import com.example.online_book_store.repository.BookRepository;

// // import jakarta.persistence.EntityNotFoundException;

// // @Service
// // public class BookService{
// //     @Autowired
// //     private BookRepository bookRepository;

// //     @Autowired
// //     private AuthorRepository authorRepository;

// //     public List<Book> getAllBooks(){
// //         return bookRepository.findAll();
// //     }

// //     public Book getById(int id){
// //         Book book = bookRepository.findById(id).orElse(null);
// //         return book;
// //     }

// //     public Book createBook(Book book) {
// //         if (book == null || book.getBookTitle() == null || book.getBookTitle().isEmpty()) {
// //             throw new IllegalArgumentException("Book title cannot be null or empty");
// //         }
// //         if (book.getAuthor() != null) {
// //             Author author = authorRepository.findById(book.getAuthor().getAuthorId()).orElse(null);
// //             if (author != null) {
// //                 book.setAuthor(author);
// //             } else {
// //                 throw new EntityNotFoundException("Author not found with ID: " + book.getAuthor().getAuthorId());
// //             }
// //         }
// //         return bookRepository.save(book);
// //     }

// //     public List<Book> createBooks(List<Book> books) throws Exception {
// //         if(books == null || books.isEmpty()){
// //             throw new Exception("books can't be Null or Empty");
// //         }
// //         for (Book book : books) {
// //             Author author = book.getAuthor();
// //             if (author != null) {
// //                 Author existingAuthor = authorRepository.findById(author.getAuthorId()).orElse(null);
// //                 if (existingAuthor == null) {
// //                     System.out.println("Author not found with ID: " + author.getAuthorId());
// //                     continue;
// //                 }
// //                 book.setAuthor(existingAuthor);
// //             }
// //         }
// //         return bookRepository.saveAll(books);
// //     }
    
// //     public Book updateBook(int bookId, Book updatedBook) {
// //         // Fetch the existing book
// //         Book existingBook = bookRepository.findById(bookId)
// //             .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    
// //         // Update the properties
// //         existingBook.setBookTitle(updatedBook.getBookTitle());
// //         existingBook.setBookStock(updatedBook.getBookStock());
// //         existingBook.setBookPrice(updatedBook.getBookPrice());
// //         Author author = authorRepository.findById(updatedBook.getAuthor().getAuthorId())
// //             .orElseThrow(() -> new EntityNotFoundException("Author not found"));
    
// //         existingBook.setAuthor(author);
    
// //         return bookRepository.save(existingBook);
// //     }
    

// //     public void deleteBook(int id) {
// //         if (!bookRepository.existsById(id)) {
// //             throw new EntityNotFoundException("Book with id " + id + " not found");
// //         }
// //         bookRepository.deleteById(id);
// //     }

// //     public BookDTO bookToBookDTO(Book book) {
// //         BookDTO bookDTO = new BookDTO();
// //         bookDTO.setBookId(book.getBookId());
// //         bookDTO.setBookTitle(book.getBookTitle());
// //         bookDTO.setBookPrice(book.getBookPrice());
// //         bookDTO.setBookStock(book.getBookStock());
// //         bookDTO.setAuthorName(book.getAuthor().getAuthorName());
// //         return bookDTO;
// //     }
// // }