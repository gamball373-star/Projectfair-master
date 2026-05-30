package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public String createProduct(Long id, String name, String category, double price, int stock) {
        try {
            Product product = new model.Product(id, name, category, price, stock);
            productService.addProduct(product);
            return "Товар '" + name + "' успешно добавлен в магазин электроники!";
        } catch (IllegalArgumentException e) {
            return "Ошибка при создании: " + e.getMessage();
        }
    }

    public List<model.Product> showCatalog() {
        return productService.getAllProducts();
    }
}
