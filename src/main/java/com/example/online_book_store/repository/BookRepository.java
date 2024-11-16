package com.example.online_book_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.online_book_store.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

    List<Book> findByAuthor_AuthorName(String authorName);
}