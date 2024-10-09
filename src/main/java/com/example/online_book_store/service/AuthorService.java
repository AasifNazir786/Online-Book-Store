package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.online_book_store.model.Author;
import com.example.online_book_store.repository.AuthorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author getById(int id){
        return authorRepository.findById(id).orElse(null);
    }

    public Author createAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author updateAuthor(int id, Author author){
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        existingAuthor.setAuthorName(author.getAuthorName());
        existingAuthor.setBooks(author.getBooks());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(int id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    authorRepository.delete(author);
    }
}
