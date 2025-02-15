package com.example.online_book_store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderItemDTO;
import com.example.online_book_store.mapper.OrderItemMapper;
import com.example.online_book_store.model.OrderItem;
import com.example.online_book_store.repository.OrderItemRepository;

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
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem.map(orderItemMapper::toDTO).orElse(null);
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
        Optional<OrderItem> existingOrderItem = orderItemRepository.findById(id);
        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
            orderItem.setId(id);
            orderItem = orderItemRepository.save(orderItem);
            return orderItemMapper.toDTO(orderItem);
        }
        return null;
    }

    @CacheEvict(value = "orderItems", key = "#id")
    public void deleteOrderItem(Long id) {
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