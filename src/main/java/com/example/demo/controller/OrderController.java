package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public String createOrder(Long userId, Long productId, int quantity) {
        try {
            Order order = new Order(null, userId, productId, quantity);
            orderService.createOrder(order);
            return "Заказ успешно создан! Количество товара: " + quantity;
        } catch (IllegalArgumentException e) {
            return "Ошибка при создании заказа: " + e.getMessage();
        }
    }

    public List<Order> viewAllOrders() {
        return orderService.getAllOrders();
    }

    public String viewOrderById(Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return "Заказ ID: " + order.getOrderId() + 
                   ", Пользователь ID: " + order.getUserId() + 
                   ", Товар ID: " + order.getProductId() + 
                   ", Количество: " + order.getQuantity();
        } catch (IllegalArgumentException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    public String deleteOrder(Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return "Заказ с ID " + orderId + " успешно удален!";
        } catch (IllegalArgumentException e) {
            return "Ошибка при удалении заказа: " + e.getMessage();
        }
    }

    public List<Order> viewUserOrders(Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}
