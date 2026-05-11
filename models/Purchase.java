package models;

public class Purchase {

    private int purchaseId;
    private String productName;
    private int quantity;
    private double amount;

    public Purchase(int purchaseId, String productName, int quantity, double amount) {
        this.purchaseId = purchaseId;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Purchase ID : " + purchaseId +
                "\nProduct Name : " + productName +
                "\nQuantity : " + quantity +
                "\nAmount : " + amount;
    }
}