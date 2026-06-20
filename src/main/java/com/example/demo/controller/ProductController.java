package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return new ResponseEntity<>("Товар '" + product.getName() + "' успешно добавлен в магазин электроники!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Ошибка при создании: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Внутренняя ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> showCatalog() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get product by ID
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            if (product.isPresent()) {
                return new ResponseEntity<>(product.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get products by category
     * GET /api/products/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update a product
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        try {
            String result = productService.updateProduct(id, productDetails);
            if (result.contains("успешно")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Ошибка при обновлении: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Внутренняя ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a product
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                return new ResponseEntity<>("Товар успешно удален!", HttpStatus.OK);
            }
            return new ResponseEntity<>("Товар не найден!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Внутренняя ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check if product is in stock
     * GET /api/products/{id}/stock
     */
    @GetMapping("/{id}/stock")
    public ResponseEntity<Boolean> checkStock(@PathVariable Long id) {
        try {
            boolean inStock = productService.isInStock(id);
            return new ResponseEntity<>(inStock, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update product stock
     * PATCH /api/products/{id}/stock
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        try {
            String result = productService.updateStock(id, quantity);
            if (result.contains("успешно")) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Ошибка при обновлении запаса: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Внутренняя ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
