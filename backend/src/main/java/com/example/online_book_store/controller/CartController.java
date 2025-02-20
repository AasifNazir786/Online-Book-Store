package com.example.online_book_store.controller;

import com.example.online_book_store.dto.CartDTO;
import com.example.online_book_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDTO> saveCart(@RequestBody CartDTO cartDto) {

        CartDTO savedCart = cartService.saveCart(cartDto);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CartDTO>> getCartById(@PathVariable Long id) {

        Optional<CartDTO> cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDto) {

        CartDTO updatedCart = cartService.updateCart(id, cartDto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {

        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<CartDTO>> getCartWithBooksByUsername(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<CartDTO> cartDTOS = cartService.getCartWithBooksByUsername(username, page, size);
        return ResponseEntity.ok(cartDTOS);
    }

    @GetMapping("/total-quantity")
    public ResponseEntity<Integer> getTotalQuantityByUserAndBook(
            @RequestParam String username,
            @RequestParam String bookTitle
    ) {

        Integer totalQuantity = cartService.getTotalQuantityByUserAndBook(username, bookTitle);
        return ResponseEntity.ok(totalQuantity);
    }

    @GetMapping("/user/{username}/basic")
    public ResponseEntity<Page<CartDTO>> getCartByUsername(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<CartDTO> cartDTOS = cartService.getCartByUsername(username, page, size);
        return ResponseEntity.ok(cartDTOS);
    }

    @DeleteMapping("/user/{username}/clear")
    public ResponseEntity<Void> clearCartByUsername(@PathVariable String username) {

        cartService.clearCartByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{username}/item")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable String username,
            @RequestParam String bookTitle
    ) {

        cartService.removeItemFromCart(username, bookTitle);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{username}/total-price")
    public ResponseEntity<Double> getTotalPriceByUsername(@PathVariable String username) {

        Double totalPrice = cartService.getTotalPriceByUsername(username);
        return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/user/{username}/quantity")
    public ResponseEntity<Void> updateQuantityInCart(
            @PathVariable String username,
            @RequestParam String bookTitle,
            @RequestParam int quantity
    ) {

        cartService.updateQuantityInCart(username, bookTitle, quantity);
        return ResponseEntity.noContent().build();
    }
}