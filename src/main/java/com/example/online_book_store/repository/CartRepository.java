package com.example.online_book_store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.online_book_store.model.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

    @Query("SELECT c FROM Cart c JOIN c.user u JOIN c.book b WHERE u.username = :userName")
    Page<Cart> findCartWithBooksByUsername(String userName, Pageable pageable);

    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.user.username = :userName AND c.book.bookTitle = :bookTitle")
    Integer findTotalQuantityByUserAndBook(String userName, String bookTitle);

    Page<Cart> findByUser_Username(String userName, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.username = :userName")
    void clearCartByUsername(String userName);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.username = :userName AND c.book.bookTitle = :bookTitle")
    void removeItemFromCart(String userName, String bookTitle);

    @Query("SELECT SUM(c.quantity * c.book.bookPrice) FROM Cart c WHERE c.user.username = :userName")
    Double findTotalPriceByUsername(String userName);

    @Modifying
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.user.username = :userName AND c.book.bookTitle = :bookTitle")
    void updateQuantityInCart(String userName, String bookTitle, int quantity);
}
