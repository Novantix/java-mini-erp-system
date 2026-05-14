package services;
import models.Product;
public class InventoryService {

    public void checkLowStock(Product product) {

        if(product.getStock() < 10) {
            System.out.println("Low Stock Alert!");
        } else {
            System.out.println("Stock Available");
        }
    }

    public void updateStock(Product product, int quantity) {
        product.setStock(quantity);
    }
}
