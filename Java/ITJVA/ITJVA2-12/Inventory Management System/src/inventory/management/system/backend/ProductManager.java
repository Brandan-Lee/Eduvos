package inventory.management.system.backend;

import java.sql.Array;
import java.util.ArrayList;

public class ProductManager {

    //I am using an ArrayList due to the fact that we won't be taught the hashmap datastructure during this module

    ArrayList<Product> products;


    //This function allows the user to add a product if the product does not exist yet
    public void addProduct(Product product) {
        if (products.contains(product.getProductId())) {
            System.out.println("The following product ID " + product.getProductId() + " already exists");
        } else {
            products.add(product);
            product.getCreatedAt();
            System.out.println("The product has been added successfully");
        }
    }

    //This function will return all the products to the user
    public ArrayList<Product> viewProducts() {
        return new ArrayList<Product>(products);
    }

    //This function allows the user to update the product details if the product id does exist
    public void updateProduct(String productId, Product updatedProduct) {
        if (products.contains(productId)) {
            products.set(products.indexOf(productId), updatedProduct);
            System.out.println("The product has been updated successfully");
        } else {
            System.out.println("The following product ID " + productId + " already exists");
        }
    }

    public void deleteProduct(String productId) {
        if (products.contains(productId)) {
            products.remove(productId);
            System.out.println("This product has been removed successfully");
        } else {
            System.out.println("The following product ID " + productId + " does not exist");
        }
    }
}
