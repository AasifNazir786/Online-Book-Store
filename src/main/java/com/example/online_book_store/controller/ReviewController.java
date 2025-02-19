package com.example.online_book_store.controller;

import com.example.online_book_store.dto.ReviewDTO;
import com.example.online_book_store.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {

        ReviewDTO savedReview = reviewService.addReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/book/{bookTitle}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByBookTitle(
            @PathVariable String bookTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ReviewDTO> reviews = reviewService.getReviewsByBookTitle(bookTitle, page, size);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {

        ReviewDTO review = reviewService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByUserUsername(
            @PathVariable String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ReviewDTO> reviews = reviewService.getReviewsByUserUsername(userName, page, size);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/book/{bookTitle}/average-rating")
    public ResponseEntity<Double> getAverageRatingByBookTitle(@PathVariable String bookTitle) {

        Double averageRating = reviewService.getAverageRatingByBookTitle(bookTitle);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @GetMapping("/book/{bookTitle}/count")
    public ResponseEntity<Long> countReviewsByBookTitle(@PathVariable String bookTitle) {

        Long count = reviewService.countReviewsByBookTitle(bookTitle);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/book/{bookTitle}/sorted")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByBookTitleSortedByRatingDesc(
            @PathVariable String bookTitle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<ReviewDTO> reviews = reviewService.getReviewsByBookTitleSortedByRatingDesc(bookTitle, page, size);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewDTO reviewDTO
    ) {

        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
