package com.example.online_book_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.online_book_store.model.Order;
import com.example.online_book_store.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
    @Query("SELECT oi.order FROM OrderItem oi WHERE oi.book.id = :bookId")
    List<Order> findOrdersContainingBook(@Param("bookId") Long bookId);

    @Query("SELECT oi.book.id, SUM(oi.quantity) as totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.book.id " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> findTop5MostPurchasedBooks(Pageable pageable);

    Optional<OrderItem> findByOrderId(Long orderId);

    Optional<OrderItem> findByBookId(Long bookId);
}
