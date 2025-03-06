package com.example.inventorymanagementsystem.Backend;

import java.util.Currency;
import java.util.Date;

public class Product {

    //Declaration of variables to build the class
    private int quantity;
    private String productId, name;
    private Currency price;
    private Date createdAt, updatedAt;

    //create the constructor for the class
    public Product(String productId, String name, int quantity, Currency price, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //Public getter and setter methods to access all the private variables

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    public void setQuantity(int quantity)  {
        this.quantity = quantity;
    }

    public Currency getPrice() {
        return price;
    }

    public void setPrice(Currency price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
