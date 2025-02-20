package com.example.online_book_store.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.online_book_store.enums.Status;
import com.example.online_book_store.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Status status);

    @Query("SELECT o FROM Order o WHERE o.orderedDateTime BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT o FROM Order o WHERE o.totalPrice > :minPrice")
    List<Order> findOrdersWithTotalPriceGreaterThan(@Param("minPrice") Double minPrice);
}