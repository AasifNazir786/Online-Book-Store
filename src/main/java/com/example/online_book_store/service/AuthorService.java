package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;
import com.example.online_book_store.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author getById(int id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }
    
    public List<Author> createListAuthor(List<Author> authors) {
        if (authors.isEmpty()) {
            throw new IllegalArgumentException("Can't save Authors because the list is empty");
        }
        for (Author author : authors) {
            if(author.getBooks() != null){
                for (Book book : author.getBooks()) {
                    book.setAuthor(author);
                }
            }
        }
        return authorRepository.saveAll(authors);
    }
    

    public Author createAuthor(Author author) {
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
        return authorRepository.save(author);
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
