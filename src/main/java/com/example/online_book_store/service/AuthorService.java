package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.Author;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

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
        return authorRepository.saveAll(authors);
    }
    

    public Author createAuthor(Author author) {
    if (author == null || author.getAuthorName() == null || author.getAuthorName().isEmpty()) {
        throw new IllegalArgumentException("Author name cannot be null or empty");
    }
    if (author.getBooks() != null) {
        for (Book book : author.getBooks()) {
            book.setAuthor(author);
        }
    }
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
