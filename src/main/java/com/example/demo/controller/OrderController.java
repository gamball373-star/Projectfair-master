package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create a new order
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@Valid @RequestBody Order order) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order createdOrder = orderService.createOrder(order);
            response.put("message", "Заказ успешно создан!");
            response.put("orderId", createdOrder.getOrderId());
            response.put("userId", createdOrder.getUserId());
            response.put("productId", createdOrder.getProductId());
            response.put("quantity", createdOrder.getQuantity());
//            response.put("totalPrice", createdOrder.getTotalPrice());
//            response.put("status", createdOrder.getStatus());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("error", "Ошибка при создании заказа: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all orders
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get order by ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Order> order = orderService.getOrderByIdOptional(id);
            if (order.isPresent()) {
                return new ResponseEntity<>(order.get(), HttpStatus.OK);
            }
            response.put("error", "Заказ с ID " + id + " не найден!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get orders by user ID
     * GET /api/orders/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get orders by product ID
     * GET /api/orders/product/{productId}
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getOrdersByProductId(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = orderService.getOrdersByProductId(productId);
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update order
     * PUT /api/orders/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            if (updatedOrder != null) {
                response.put("message", "Заказ успешно обновлен!");
                response.put("order", updatedOrder);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("error", "Заказ с ID " + id + " не найден!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.put("error", "Ошибка при обновлении: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update order status
     * PATCH /api/orders/{id}/status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = orderService.updateOrderStatus(id, status);
            response.put("message", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("error", "Ошибка при обновлении статуса: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update order quantity
     * PATCH /api/orders/{id}/quantity
     */
//    @PatchMapping("/{id}/quantity")
//    public ResponseEntity<Map<String, Object>> updateOrderQuantity(@PathVariable Long id, @RequestParam int quantity) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Order updatedOrder = orderService.updateOrderQuantity(id, quantity);
//            response.put("message", "Количество товара успешно обновлено!");
//            response.put("newQuantity", updatedOrder.getQuantity());
//            response.put("newTotalPrice", updatedOrder.getTotalPrice());
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            response.put("error", "Ошибка при обновлении количества: " + e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * Delete order
     * DELETE /api/orders/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean deleted = orderService.deleteOrder(id);
            if (deleted) {
                response.put("message", "Заказ с ID " + id + " успешно удален!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("error", "Заказ с ID " + id + " не найден!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cancel order (update status to CANCELLED)
     * PATCH /api/orders/{id}/cancel
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = orderService.cancelOrder(id);
            response.put("message", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("error", "Ошибка при отмене заказа: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Complete order (update status to COMPLETED)
     * PATCH /api/orders/{id}/complete
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Map<String, String>> completeOrder(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = orderService.completeOrder(id);
            response.put("message", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("error", "Ошибка при завершении заказа: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get orders by status
     * GET /api/orders/status/{status}
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get total orders count
     * GET /api/orders/count/total
     */
    @GetMapping("/count/total")
    public ResponseEntity<Map<String, Long>> getTotalOrdersCount() {
        Map<String, Long> response = new HashMap<>();
        try {
            long count = orderService.getTotalOrdersCount();
            response.put("totalOrders", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get pending orders count
     * GET /api/orders/count/pending
     */
    @GetMapping("/count/pending")
    public ResponseEntity<Map<String, Long>> getPendingOrdersCount() {
        Map<String, Long> response = new HashMap<>();
        try {
            long count = orderService.getPendingOrdersCount();
            response.put("pendingOrders", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get completed orders count
     * GET /api/orders/count/completed
     */
    @GetMapping("/count/completed")
    public ResponseEntity<Map<String, Long>> getCompletedOrdersCount() {
        Map<String, Long> response = new HashMap<>();
        try {
            long count = orderService.getCompletedOrdersCount();
            response.put("completedOrders", count);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get total revenue from all orders
     * GET /api/orders/revenue/total
     */
    @GetMapping("/revenue/total")
    public ResponseEntity<Map<String, Double>> getTotalRevenue() {
        Map<String, Double> response = new HashMap<>();
        try {
            double revenue = orderService.getTotalRevenue();
            response.put("totalRevenue", revenue);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get revenue for specific user
     * GET /api/orders/revenue/user/{userId}
     */
    @GetMapping("/revenue/user/{userId}")
    public ResponseEntity<?> getUserOrdersRevenue(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            double revenue = orderService.getUserOrdersRevenue(userId);
            response.put("userRevenue", revenue);
            response.put("userId", userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get average order value
     * GET /api/orders/stats/average-value
     */
    @GetMapping("/stats/average-value")
    public ResponseEntity<Map<String, Double>> getAverageOrderValue() {
        Map<String, Double> response = new HashMap<>();
        try {
            double avgValue = orderService.getAverageOrderValue();
            response.put("averageOrderValue", avgValue);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get highest value order
     * GET /api/orders/stats/highest-order
     */
    @GetMapping("/stats/highest-order")
    public ResponseEntity<?> getHighestValueOrder() {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Order> highestOrder = orderService.getHighestValueOrder();
            if (highestOrder.isPresent()) {
                return new ResponseEntity<>(highestOrder.get(), HttpStatus.OK);
            }
            response.put("error", "Нет заказов в системе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Search orders by user ID and date range
     * GET /api/orders/search?userId={userId}&startDate={startDate}&endDate={endDate}
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchOrders(@RequestParam(required = false) Long userId,
                                          @RequestParam(required = false) String status) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Order> orders = orderService.searchOrders(userId, status);
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Внутренняя ошибка сервера: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
