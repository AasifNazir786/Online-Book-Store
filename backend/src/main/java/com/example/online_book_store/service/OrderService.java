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

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @CacheEvict(value = "orders", allEntries = true)
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        calculateTotalPrice(order);
        order.setStatus(Status.PENDING);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order Not found with id: " + id));
        return orderMapper.toDTO(order);
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
            calculateTotalPrice(order);
            order = orderRepository.save(order);
            return orderMapper.toDTO(order);
        }
        return null;
    }

    @CacheEvict(value = "orders", key = "#id")
    public void deleteOrder(Long id) {
        if(!orderRepository.existsById(id))
            throw new EntityNotFoundException("order not exists");
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

    @CacheEvict(value = "orders", key = "#id")
    public OrderDTO updateOrderStatus(Long id, Status status) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setStatus(status);
            order = orderRepository.save(order);
            return orderMapper.toDTO(order);
        }
        return null;
    }


    /*
    * private methods
    * this method is used to calculate the total price of the ordered books
    */
    private void calculateTotalPrice(Order order) {
        double totalPrice = order.getOrderItems()
                .stream()
                .mapToDouble(orderItem -> orderItem.getBook().getBookPrice() * orderItem.getQuantity())
                .sum();
        order.setTotalPrice(totalPrice);
    }
}