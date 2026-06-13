package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // Указывает, что этот класс связан с таблицей в БД
@Table(name = "products") // Имя таблицы в базе данных
@Getter
@Setter
public class Product {

    @Id // Маркер первичного ключа (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент ID (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Поле не может быть пустым (NOT NULL)
    private String name;

    private String category;
    private double price;

    @Column(name = "stock_quantity") // Переименует колонку в базе в змеиный_регистр
    private int stockQuantity;

    // Конструктор по умолчанию ОБЯЗАТЕЛЕН для JPA
    public Product() {}

    public Product(String name, String category, double price, int stockQuantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}