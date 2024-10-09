package com.example.online_book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.online_book_store.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
}