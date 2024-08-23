package com.example.online_book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.online_book_store.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
   // List<Book> findByAuthorName(Author authorName);
   List<Book> findByBookTitle(String bookTitle);
  }