package models;

public class Customer {

    private int customerId;
    private String customerName;
    private String phone;

    public Customer(int customerId, String customerName, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return customerId + "," + customerName + "," + phone;
    }
}
