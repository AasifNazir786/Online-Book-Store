package com.example.online_book_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderItemDTO;
import com.example.online_book_store.mapper.OrderItemMapper;
import com.example.online_book_store.model.OrderItem;
import com.example.online_book_store.repository.OrderItemRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @CacheEvict(value = "orderItems", allEntries = true)
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    @Cacheable(value = "orderItems", key = "#id")
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + id));
        return orderItemMapper.toDTO(orderItem);
    }

    @Cacheable(value = "orderItems")
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "orderItems", key = "#id")
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + id));
        if (existingOrderItem != null) {
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
            orderItem.setId(id);
            orderItem = orderItemRepository.save(orderItem);
            return orderItemMapper.toDTO(orderItem);
        }
        return null;
    }

    @CacheEvict(value = "orderItems", key = "#id")
    public void deleteOrderItem(Long id) {
        if(!orderItemRepository.existsById(id))
            throw new EntityNotFoundException("orderItem not exists");
        orderItemRepository.deleteById(id);
    }

    @Cacheable(value = "orderItems", key = "#orderId")
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

    @Cacheable(value = "orderItems", key = "#bookId")
    public List<OrderItemDTO> getOrderItemsByBookId(Long bookId) {
        return orderItemRepository.findByBookId(bookId)
                .stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }
}