package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "ID пользователя не может быть пустым")
    private Long userId;

    @Column(name = "product_id", nullable = false)
    @NotNull(message = "ID продукта не может быть пустым")
    private Long productId;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Количество товара должно быть не менее 1")
    private int quantity;

//    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
//    @NotNull(message = "Итоговая цена не может быть пустой")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
//    private BigDecimal totalPrice;




    public Long getOrderId() { return orderId; }

    public Long getUserId() { return userId; }

    public Long getProductId() { return productId; }

    public int getQuantity() { return quantity; }





//    @Column(name = "status", nullable = false, length = 30)
//    @NotNull(message = "Статус заказа не может быть пустым")

}