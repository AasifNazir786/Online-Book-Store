package com.example.online_book_store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.online_book_store.dto.BookDTO;
import com.example.online_book_store.dto.CartDTO;
import com.example.online_book_store.mapper.BookMapper;
import com.example.online_book_store.mapper.CartMapper;
import com.example.online_book_store.model.Cart;
import com.example.online_book_store.model.User;
import com.example.online_book_store.repository.CartRepository;
import com.example.online_book_store.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private BookMapper bookMapper;

    @Transactional
    @CacheEvict(value = "cart", allEntries = true)
    public CartDTO saveCart(CartDTO cartDto) {

        Cart cart = cartMapper.toEntity(cartDto);
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cart", key = "#id")
    public Optional<CartDTO> getCartById(Long id) {

        Cart cart = cartRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Cart not found with id: " + id));
        return Optional.of(cartMapper.toDTO(cart));
    }

    @Transactional
    @CachePut(value = "cart", key = "#id")
    public CartDTO updateCart(Long id, CartDTO cartDto) {
        Cart cart = cartRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Cart not found with id: " + id));
        cart.setQuantity(cartDto.getQuantity());
        if(cartDto.getBook() != null) {
            mapBookDtoToBookEntity(cartDto.getBook(), cart);
        }
        if(cartDto.getUserId() != null) {
            retrieveAndSetUserToCart(cartDto.getUserId(), cart);
        }
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Transactional
    @CacheEvict(value = "cart", key = "#id")
    public void deleteCart(Long id) {
        if(!cartRepository.existsById(id))
            throw new EntityNotFoundException("Cart not found with id: "+ id);
        cartRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cartByUser", key = "#userName")
    public Page<CartDTO> getCartWithBooksByUsername(String userName, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, userName));
        Page<Cart> cartPage = cartRepository.findCartWithBooksByUsername(userName, pageable);
        return cartPage.map(cartMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "totalQuantity", key = "#userName + #bookTitle")
    public Integer getTotalQuantityByUserAndBook(String userName, String bookTitle) {

        return cartRepository.findTotalQuantityByUserAndBook(userName, bookTitle);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "cartByUser", key = "#userName")
    public Page<CartDTO> getCartByUsername(String userName, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, userName));
        Page<Cart> cartPage = cartRepository.findByUser_Username(userName, pageable);
        return cartPage.map(cartMapper::toDTO);
    }

    @Transactional
    @CacheEvict(value = "cartByUser", key = "#userName")
    public void clearCartByUsername(String userName) {

        if(!cartRepository.existsByUser_Username(userName))
            throw new EntityNotFoundException("Cart not found with username: "+ userName);
        cartRepository.clearCartByUsername(userName);
    }

    @Transactional
    @CacheEvict(value = "cartByUser", key = "#userName")
    public void removeItemFromCart(String userName, String bookTitle) {
        cartRepository.removeItemFromCart(userName, bookTitle);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "totalPrice", key = "#userName")
    public Double getTotalPriceByUsername(String userName) {
        return cartRepository.findTotalPriceByUsername(userName);
    }

    @Transactional
    @CacheEvict(value = "cartByUser", key = "#userName")
    public void updateQuantityInCart(String userName, String bookTitle, int quantity) {
        cartRepository.updateQuantityInCart(userName, bookTitle, quantity);
    }

    /*
    * Private Helper Methods
    */
    private void mapBookDtoToBookEntity(BookDTO bookDto, Cart cart) {

        cart.setBook(bookMapper.toEntity(bookDto));
    }

    private void retrieveAndSetUserToCart(Long userId, Cart cart) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found with id: " + userId));
        cart.setUser(user);
    }
}
