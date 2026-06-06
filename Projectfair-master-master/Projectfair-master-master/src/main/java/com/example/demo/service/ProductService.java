package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Add a new product with validation
     * Throws IllegalArgumentException if price is negative
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть пустым!");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Название товара не может быть пустым!");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной!");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Количество на складе не может быть отрицательным!");
        }
        productRepository.save(product);
    }

    /**
     * Get all products from the catalog
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get product by ID
     */
    public Optional<Product> getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }
        return productRepository.findById(id);
    }

    /**
     * Get products filtered by category
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой!");
        }
        return productRepository.findByCategory(category);
    }

    /**
     * Update an existing product
     * Returns success/failure message
     */
    public String updateProduct(Long id, Product productDetails) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }
        if (productDetails == null) {
            throw new IllegalArgumentException("Данные товара не могут быть пустыми!");
        }

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            // Update only non-null fields
            if (productDetails.getName() != null && !productDetails.getName().trim().isEmpty()) {
                product.setName(productDetails.getName());
            }
            if (productDetails.getCategory() != null && !productDetails.getCategory().trim().isEmpty()) {
                product.setCategory(productDetails.getCategory());
            }
            if (productDetails.getPrice() >= 0) {
                product.setPrice(productDetails.getPrice());
            } else if (productDetails.getPrice() < 0) {
                throw new IllegalArgumentException("Цена не может быть отрицательной!");
            }
            if (productDetails.getStockQuantity() >= 0) {
                product.setStockQuantity(productDetails.getStockQuantity());
            } else if (productDetails.getStockQuantity() < 0) {
                throw new IllegalArgumentException("Количество на складе не может быть отрицательным!");
            }

            productRepository.save(product);
            return "Товар '" + product.getName() + "' успешно обновлен!";
        }

        return "Товар с ID " + id + " не найден!";
    }

    /**
     * Delete a product by ID
     * Returns true if deleted, false if not found
     */
    public boolean deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if a product is in stock
     */
    public boolean isInStock(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }

        return productRepository.findById(id)
                .map(product -> product.getStockQuantity() > 0)
                .orElse(false);
    }

    /**
     * Update product stock quantity
     * Returns success/failure message
     */
    public String updateStock(Long id, int quantity) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество товара не может быть отрицательным!");
        }

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStockQuantity(quantity);
            productRepository.save(product);
            return "Запас товара '" + product.getName() + "' успешно обновлен на " + quantity + " единиц!";
        }

        return "Товар с ID " + id + " не найден!";
    }

    /**
     * Get total number of products
     */
    public long getTotalProductCount() {
        return productRepository.count();
    }

    /**
     * Get total inventory value (sum of price * stockQuantity)
     */
    public double getTotalInventoryValue() {
        return getAllProducts().stream()
                .mapToDouble(p -> p.getPrice() * p.getStockQuantity())
                .sum();
    }

    /**
     * Get products with low stock (less than specified threshold)
     */
    public List<Product> getLowStockProducts(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("Порог не может быть отрицательным!");
        }

        return getAllProducts().stream()
                .filter(p -> p.getStockQuantity() <= threshold)
                .toList();
    }

    /**
     * Decrease product stock (for purchase operations)
     */
    public String decreaseStock(Long id, int quantity) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество для уменьшения должно быть положительным!");
        }

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Недостаточно товара на складе! " +
                        "В наличии: " + product.getStockQuantity() + ", требуется: " + quantity);
            }

            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
            return "Запас товара '" + product.getName() + "' успешно уменьшен на " + quantity + " единиц!";
        }

        return "Товар с ID " + id + " не найден!";
    }

    /**
     * Increase product stock (for restock operations)
     */
    public String increaseStock(Long id, int quantity) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID товара должен быть положительным числом!");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество для увеличения должно быть положительным!");
        }

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStockQuantity(product.getStockQuantity() + quantity);
            productRepository.save(product);
            return "Запас товара '" + product.getName() + "' успешно увеличен на " + quantity + " единиц!";
        }

        return "Товар с ID " + id + " не найден!";
    }
}