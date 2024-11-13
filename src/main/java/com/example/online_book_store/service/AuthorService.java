package com.example.online_book_store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.AuthorDTO;
import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    public AuthorDTO authorToAuthorDTO(Author author){
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());

        List<BookDTO> dtoList = new ArrayList<>();
        List<Book> bookList = author.getBooks() != null ? author.getBooks() : new ArrayList<>();
        for(var book : bookList){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookId(book.getBookId());
            bookDTO.setBookTitle(book.getBookTitle());
            bookDTO.setBookStock(book.getBookStock());
            bookDTO.setBookPrice(book.getBookPrice());
            bookDTO.setAuthorName(author.getAuthorName());
            dtoList.add(bookDTO);
        }
        dto.setBooks(dtoList);
        return dto;
    }

    public Author dtoToAuthor(AuthorDTO authorDTO){
        Author author = new Author();
        author.setAuthorId(authorDTO.getAuthorId());
        author.setAuthorName(authorDTO.getAuthorName());

        // Convert books from BookDTO to Book entity
        List<Book> books = new ArrayList<>();
        for (BookDTO bookDTO : authorDTO.getBooks()) {
            Book book = new Book();
            book.setBookId(bookDTO.getBookId());
            book.setBookTitle(bookDTO.getBookTitle());
            book.setBookStock(bookDTO.getBookStock());
            book.setBookPrice(bookDTO.getBookPrice());
            book.setAuthor(author);
            books.add(book);
        }
        author.setBooks(books);

        return author;
    }

    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        List<Author> authorList = authorRepository.findAll();

        for(Author author : authorList){
            AuthorDTO dto = authorToAuthorDTO(author);
            authorDTOList.add(dto);
        }
        return authorDTOList;
    }

    public AuthorDTO getById(int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        return authorToAuthorDTO(author);
    }
    
    public List<AuthorDTO> createListAuthor(List<Author> authors) {
        if (authors.isEmpty()) {
            throw new IllegalArgumentException("Can't save Authors because the list is empty");
        }
        List<AuthorDTO> list = new ArrayList<>();
        for (Author author : authors) {
            AuthorDTO authorDTO = authorToAuthorDTO(author);
            if(author.getBooks() != null){
                List<BookDTO> bookDTOList = new ArrayList<>();
                for (Book book : author.getBooks()) {
                    BookDTO bookDTO = bookService.bookToBookDTO(book);
                    bookDTOList.add(bookDTO);
                }
                authorDTO.setBooks(bookDTOList);
            }
            list.add(authorDTO);
        }
        return list;
    }
    

    public AuthorDTO createAuthor(Author author) {
        if(author == null){
            throw new EntityNotFoundException("Author can not be null");
        }
        //Not mandatory to do this...............................................

        // List<Book> books = author.getBooks();
        // System.out.println("books are: "+books);
        // if(books != null){
        //     List<Book> newBooks = new ArrayList<>();
        //     for (Book book : books) {
        //         Book book2;
        //         System.out.println(book.getBookId());
        //         if(book.getBookId() == 0){
        //             book2 = bookRepository.save(book);
        //         }else{
        //             book2 = bookRepository.findById(book.getBookId())
        //                 .orElseThrow(() -> new EntityNotFoundException("Book with id " + book.getBookId() + " not found"));
        //         }
        //         book2.setAuthor(author);
        //         newBooks.add(book2);
        //     }
        //     author.setBooks(newBooks);
        //     System.out.println("new books are: " + newBooks);
        // }
        Author author1 = authorRepository.save(author);
        return authorToAuthorDTO(author1);
    }
    

    public Author updateAuthor(int id, Author author) {

        Author existingAuthor = authorRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Author not found with ID: " + id));

        existingAuthor.setAuthorName(author.getAuthorName());
        existingAuthor.setBooks(author.getBooks());
        return authorRepository.save(existingAuthor);
    }
    
    public void deleteAuthor(int authorId) {
        if(authorRepository.existsById(authorId)){
            authorRepository.deleteById(authorId);
        }else{
            throw new EntityNotFoundException("author with id: " +authorId+" not found");
        }
    }
}
