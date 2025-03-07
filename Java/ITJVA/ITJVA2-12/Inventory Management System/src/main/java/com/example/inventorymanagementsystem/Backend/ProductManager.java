package com.example.inventorymanagementsystem.Backend;

import com.example.inventorymanagementsystem.Frontend.CustomAlerts;
import java.util.ArrayList;

public class ProductManager {

    //I am using an ArrayList due to the fact that we won't be taught the hashmap datastructure during this module
    private final ArrayList<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    public boolean productExists(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return true;
            }
        }

        return false;
    }

    //This function allows the user to add a product if the product does not exist yet
    public boolean addProduct(Product product) {

        if (!productExists(product.getProductId())) {
            products.add(product);
            return true;
        }

        return false;
    }

    //This function will return all the products to the user
    public ArrayList<Product> viewProducts() {
        return products;
    }

    //This function allows the user to update the product details if the product id does exist
    public boolean updateProduct(int productId, Product updatedProduct) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.setProductId(productId);
                return true;
            }
        }

        return false;
    }

    //This function deletes the product from the products arraylist if the product ID does exist
    public boolean deleteProduct(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                products.remove(i);
                return true;
            }
        }

        return false;
    }

}
