package com.example.inventorymanagementsystem.Backend;

import java.time.LocalDate;

public class Product {

    private int quantity, productId;
    private String name;
    private double price;
    private LocalDate createdAt, updatedAt;

    public Product(int productId, String name, int quantity, double price, LocalDate createdAt, LocalDate updatedAt) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int setQuantity(int quantity) {
        this.quantity = quantity;
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double setPrice(double price) {
        this.price = price;
        return price;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public LocalDate setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return updatedAt;
    }
}
