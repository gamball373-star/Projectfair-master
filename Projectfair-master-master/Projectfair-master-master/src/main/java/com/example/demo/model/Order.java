package com.example.demo.model;

public class Order {
    private Long orderId;
    private Long userId;
    private Long productId;
    private int quantity;

    public Order(Long orderId, Long userId, Long productId, int quantity) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }

    public Object getTotalPrice() {
        return null;
    }

    public Object getStatus() {
        return null;
    }
}
