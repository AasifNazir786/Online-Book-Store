package com.example.online_book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.online_book_store.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

    List<Author> findByAuthorName(String authorName);
}