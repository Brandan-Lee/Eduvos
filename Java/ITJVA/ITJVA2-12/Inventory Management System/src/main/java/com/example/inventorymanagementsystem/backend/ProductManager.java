package com.example.inventorymanagementsystem.backend;

//this class is part of question 2 for the project

import java.util.ArrayList;

public class ProductManager {

    //I am using an array list due to the fact that we won't be taught in this module how to use a hashmap
    ArrayList<Product> products;

    //this function allows the user to add a project, but it first checks if the product already exists
    public void addProduct(Product product) {
        if (products.contains(product.getProductId())) {
            System.out.println("The following product ID " + product.getProductId() + " already exists");
        } else {
            products.add(product);
            System.out.println("The product has been added successfully");
        }
    }

    //this function will return all the products to the user
    public ArrayList<Product> viewProducts() {
        return new ArrayList<Product>(products);
    }

    //this function allows the user to update the product details if the product id does not exist yet
    public void updateProduct(String productId, Product updatedProduct) {
        if (products.contains(productId)) {
            System.out.println("The following product ID " + productId + " already exists");
        } else {
            products.set(products.indexOf(productId), updatedProduct);
            System.out.println("The product has been updated successfully");
        }
    }

    //This function checks to see if the product id exists in the array list, and then removes it
    public void deleteProduct(String productId) {
        if (products.contains(productId)) {
            products.remove(productId);
            System.out.println("This product has been removed successfully");
        } else {
            System.out.println("The following product ID " + productId + " does not exist");
        }
    }
}
