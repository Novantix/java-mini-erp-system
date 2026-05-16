package models;
public class Product {
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private int reorderLevel;

    public Product() {
    }

    public Product(int productId, String productName, int quantity,
            double price, int reorderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = reorderLevel;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

// toString method for displaying product details

     @Override
    public String toString() {
        return "productId=" + productId +
    " productName='" + productName + "'" +
    " quantity=" + quantity +
    " price=" + price +
    " reorderLevel=" + reorderLevel;
    }
    
}
