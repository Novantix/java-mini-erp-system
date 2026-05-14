package models;

public class Product {

    private int productId;
    private String productName;
    private int stock;

    public Product(int productId, String productName, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
