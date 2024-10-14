package com.example.online_book_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.online_book_store.model.BookOrder;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Integer>{
}