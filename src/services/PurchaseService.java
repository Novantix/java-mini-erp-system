package services;

import models.Supplier;
import models.Purchase;

import java.util.ArrayList;

public class PurchaseService {

    ArrayList<Supplier> supplierList = new ArrayList<>();
    ArrayList<Purchase> purchaseList = new ArrayList<>();

    public void addSupplier(Supplier supplier) {

        supplierList.add(supplier);

        System.out.println("Supplier Added Successfully");
    }

    public void viewSuppliers() {

        if (supplierList.isEmpty()) {
            System.out.println("No Suppliers Available");
            return;
        }

        for (Supplier supplier : supplierList) {
            System.out.println("----------------------");
            System.out.println(supplier);
        }
    }

    public void addPurchase(Purchase purchase) {

        purchaseList.add(purchase);

        System.out.println("Purchase Added Successfully");
    }

    public void viewPurchases() {

        if (purchaseList.isEmpty()) {
            System.out.println("No Purchase Records Found");
            return;
        }

        for (Purchase purchase : purchaseList) {
            System.out.println("----------------------");
            System.out.println(purchase);
        }
    }

    public void checkStockAvailability(String productName) {

        boolean found = false;

        for (Supplier supplier : supplierList) {

            if (supplier.getProductName().equalsIgnoreCase(productName)) {

                System.out.println("Stock Available : " + supplier.getStockQuantity());

                found = true;
            }
        }

        if (!found) {
            System.out.println("Product Not Found");
        }
    }
}