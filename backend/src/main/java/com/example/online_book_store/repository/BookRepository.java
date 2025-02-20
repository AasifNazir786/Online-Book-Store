package com.example.online_book_store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.online_book_store.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    Page<Book> findByAuthor_AuthorNameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<Book> findByBookPriceLessThan(double price, Pageable pageable);

    Page<Book> findByBookTitle(String bookTitle, Pageable pageable);
}