package com.example.online_book_store.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.online_book_store.dto.OrderDTO;
import com.example.online_book_store.enums.Status;
import com.example.online_book_store.mapper.OrderMapper;
import com.example.online_book_store.model.Order;
import com.example.online_book_store.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderDTO getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper::toDTO).orElse(null);
    }

    @Cacheable(value = "orders")
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @CacheEvict(value = "orders", key = "#id")
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = orderMapper.toEntity(orderDTO);
            order.setId(id);
            order = orderRepository.save(order);
            return orderMapper.toDTO(order);
        }
        return null;
    }

    @CacheEvict(value = "orders", key = "#id")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Cacheable(value = "orders", key = "#userId + #status.name()")
    public List<OrderDTO> getOrdersByUserIdAndStatus(Long userId, Status status) {
        return orderRepository.findByUserIdAndStatus(userId, status)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Cacheable(value = "orders", key = "#startDate.toString() + #endDate.toString()")
    public List<OrderDTO> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersBetweenDates(startDate, endDate)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Cacheable(value = "orders", key = "#minPrice")
    public List<OrderDTO> getOrdersWithTotalPriceGreaterThan(Double minPrice) {
        return orderRepository.findOrdersWithTotalPriceGreaterThan(minPrice)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}