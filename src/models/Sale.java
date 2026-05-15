package models;

public class Sale {

    private int saleId;
    private String productName;
    private double amount;
    private double gst;
    private double finalAmount;

    public Sale(int saleId, String productName, double amount) {
        this.saleId = saleId;
        this.productName = productName;
        this.amount = amount;

        this.gst = amount * 0.18;
        this.finalAmount = amount + gst;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getProductName() {
        return productName;
    }

    public double getAmount() {
        return amount;
    }

    public double getGst() {
        return gst;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    @Override
    public String toString() {
        return saleId + "," + productName + "," + amount + "," + gst + "," + finalAmount;
    }
}
