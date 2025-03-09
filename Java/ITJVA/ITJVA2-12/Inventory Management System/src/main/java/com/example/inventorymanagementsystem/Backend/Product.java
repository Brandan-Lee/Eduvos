//This class was created to answer QUESTION 2 of the project

package com.example.inventorymanagementsystem.Backend;

import java.time.LocalDate;

public class Product {

    //These are the private attributes of the Product class
    private int quantity, productId;
    private String name;
    private double price;
    private LocalDate createdAt, updatedAt;

    //Create the constructor for the class to store the product details
    public Product(int productId, String name, int quantity, double price, LocalDate createdAt, LocalDate updatedAt) {

        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    //Public getter and setter methods in order to interact with the classes private attributes
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
