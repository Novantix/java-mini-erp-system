package services;

import java.util.ArrayList;
import models.Product;

public class InventoryService {

    private ArrayList<Product> products =
            new ArrayList<>();

    // Add Product
    public void addProduct(Product product) {

        products.add(product);

        System.out.println(
                "\nProduct Added Successfully");
    }

    // Check Low Stock
    public void checkLowStock(Product product) {

        if (product.getStock() < 10) {

            System.out.println(
                    "Low Stock Alert!");

        } else {

            System.out.println(
                    "Stock Available");
        }
    }

    // Update Stock
    public void updateStock(
            Product product,
            int quantity
    ) {

        product.setStock(quantity);

        System.out.println(
                "\nStock Updated Successfully");
    }

    // View All Products
    public void showAllProducts() {

        System.out.println(
                "\n===== PRODUCT LIST =====");

        if (products.isEmpty()) {

            System.out.println(
                    "No Products Available");

            return;
        }

        for (Product product : products) {

            System.out.println(product);

            System.out.println(
                    "----------------------");
        }
    }
}