package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть больше нуля!");
        }
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказ с ID " + orderId + " не найден!"));
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.findById(orderId).isPresent()) {
            throw new IllegalArgumentException("Заказ с ID " + orderId + " не найден!");
        }
        orderRepository.deleteById(orderId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
