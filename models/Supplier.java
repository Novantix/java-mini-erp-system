package models;

public class Supplier {

    private int supplierId;
    private String supplierName;
    private String productName;
    private int stockQuantity;

    public Supplier(int supplierId, String supplierName, String productName, int stockQuantity) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Supplier ID : " + supplierId +
                "\nSupplier Name : " + supplierName +
                "\nProduct Name : " + productName +
                "\nStock Quantity : " + stockQuantity;
    }
}