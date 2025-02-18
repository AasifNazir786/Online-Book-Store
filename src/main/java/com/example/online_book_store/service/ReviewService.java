package com.example.online_book_store.service;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.dto.SimpleUserDTO;
import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.mapper.ReviewMapper;
import com.example.online_book_store.model.Book;
import com.example.online_book_store.model.User;
import com.example.online_book_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.online_book_store.dto.ReviewDTO;
import com.example.online_book_store.model.Review;
import com.example.online_book_store.repository.ReviewRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookMapper bookMapper;

    @Transactional
    public ReviewDTO addReview(ReviewDTO dto) {

        Review review = mapToEntity(dto);
        return mapToDTO(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviews", key = "#bookTitle + #pageable.pageNumber")
    public Page<Review> getReviewsByBookTitle(String bookTitle, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, bookTitle));
        return reviewRepository.findByBook_BookTitle(bookTitle, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviewsByBook", key = "#id")
    public ReviewDTO getReviewById(Long id) {
        return mapToDTO(reviewRepository.findById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviews")
    public Page<ReviewDTO> getReviewsByUserUsername(String userName, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, userName));
        Page<Review> reviews = reviewRepository.findByUser_Username(userName, pageable);
        return reviews.map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviews", key = "#bookTitle")
    public Double getAverageRatingByBookTitle(String bookTitle) {

        return reviewRepository.findAverageRatingByBookTitle(bookTitle);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviewCount", key = "#bookTitle")
    public Long countReviewsByBookTitle(String bookTitle) {

        return reviewRepository.countReviewsByBookTitle(bookTitle);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "reviews")
    public Page<ReviewDTO> getReviewsByBookTitleSortedByRatingDesc(String bookTitle, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, bookTitle));
        Page<Review> reviews = reviewRepository.findByBook_BookTitleOrderByRatingDesc(bookTitle, pageable);
        return reviews.map(this::mapToDTO);
    }

    @Transactional
    @CacheEvict(value = "reviews", allEntries = true)
    public ReviewDTO updateReview(Long id, ReviewDTO dto) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        if(dto.getUser() != null){
            Long userId = dto.getUser().getId();
            User user = getUserById(userId);
            review.setUser(user);
        }
        if(dto.getBook() != null){
            review.setBook(mapToBookEntity(dto.getBook()));
        }
        return mapToDTO(reviewRepository.save(review));
    }

    @Transactional
    @CacheEvict(value = "reviewsByBook", key = "#id")
    public void deleteReview(Long id) {
        if(!reviewRepository.existsById(id))
            throw new EntityNotFoundException("review not found with id: "+ id);
        reviewRepository.deleteById(id);
    }

    /*
     * Private helper methods
     */
    private ReviewDTO mapToDTO(Review review){
        ReviewDTO dto = reviewMapper.toDTO(review);
        if(review.getUser() != null){
            SimpleUserDTO simpleUserDTO = createSimpleUserDTO(review.getUser());
            dto.setUser(simpleUserDTO);
        }
        return dto;
    }

    private SimpleUserDTO createSimpleUserDTO(User user){
        SimpleUserDTO simpleUserDTO = new SimpleUserDTO();
        simpleUserDTO.setId(user.getId());
        simpleUserDTO.setFullName(user.getFullName());
        return simpleUserDTO;
    }

    private Review mapToEntity(ReviewDTO dto){
        Review review = reviewMapper.toEntity(dto);
        if(dto.getUser() != null){
            Long id = dto.getUser().getId();
            User user = getUserById(id);
            review.setUser(user);
        }
        return review;
    }

    private User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    private Book mapToBookEntity(BookDTO dto){
        return bookMapper.toEntity(dto);
    }
}

