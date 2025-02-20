package com.example.online_book_store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.online_book_store.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

    Page<Review> findByBook_BookTitle(String bookTitle, Pageable pageable);

    Page<Review> findByUser_Username(String userName, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.bookTitle = :bookTitle")
    Double findAverageRatingByBookTitle(String bookTitle);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.book.bookTitle = :bookTitle")
    Long countReviewsByBookTitle(String bookTitle);

    @Query("SELECT r FROM Review r WHERE r.book.bookTitle = :bookTitle ORDER BY r.rating DESC")
    Page<Review> findByBook_BookTitleOrderByRatingDesc(String bookTitle, Pageable pageable);

}
