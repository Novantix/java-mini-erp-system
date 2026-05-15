package services;

import java.io.FileWriter;
import java.util.ArrayList;
import models.Purchase;
import models.Supplier;

public class PurchaseService {

    private ArrayList<Supplier> supplierList =
            new ArrayList<>();

    private ArrayList<Purchase> purchaseList =
            new ArrayList<>();


    // ADD SUPPLIER

    public void addSupplier(Supplier supplier) {

        for (Supplier s : supplierList) {

            if (s.getSupplierId()
                    == supplier.getSupplierId()) {

                System.out.println(
                        "Supplier ID already exists!");

                return;
            }
        }

        if (supplier.getSupplierName()
                .trim()
                .isEmpty()) {

            System.out.println(
                    "Supplier Name cannot be empty!");

            return;
        }

        if (supplier.getStockQuantity() <= 0) {

            System.out.println(
                    "Invalid Stock Quantity!");

            return;
        }

        supplierList.add(supplier);

        saveSupplierToFile(supplier);

        System.out.println(
                "Supplier Added Successfully");
    }


    // VIEW SUPPLIERS

    public void viewSuppliers() {

        if (supplierList.isEmpty()) {

            System.out.println(
                    "No Suppliers Available");

            return;
        }

        for (Supplier supplier : supplierList) {

            System.out.println(
                    "----------------------");

            System.out.println(supplier);
        }
    }


    // ADD PURCHASE

    public void addPurchase(Purchase purchase) {

        for (Purchase p : purchaseList) {

            if (p.getPurchaseId()
                    == purchase.getPurchaseId()) {

                System.out.println(
                        "Purchase ID already exists!");

                return;
            }
        }

        if (purchase.getQuantity() <= 0) {

            System.out.println(
                    "Invalid Quantity!");

            return;
        }

        if (purchase.getAmount() <= 0) {

            System.out.println(
                    "Invalid Amount!");

            return;
        }

        boolean productFound = false;

        for (Supplier supplier : supplierList) {

            if (supplier.getProductName()
                    .equalsIgnoreCase(
                            purchase.getProductName())) {

                productFound = true;

                if (purchase.getQuantity()
                        > supplier.getStockQuantity()) {

                    System.out.println(
                            "Insufficient Stock!");

                    return;
                }

                int updatedStock =
                        supplier.getStockQuantity()
                        - purchase.getQuantity();

                supplier.setStockQuantity(
                        updatedStock);

                break;
            }
        }

        if (!productFound) {

            System.out.println(
                    "Product Not Found!");

            return;
        }

        purchaseList.add(purchase);

        savePurchaseToFile(purchase);

        System.out.println(
                "Purchase Added Successfully");
    }


    // VIEW PURCHASES

    public void viewPurchases() {

        if (purchaseList.isEmpty()) {

            System.out.println(
                    "No Purchase Records Found");

            return;
        }

        for (Purchase purchase : purchaseList) {

            System.out.println(
                    "----------------------");

            System.out.println(purchase);
        }
    }


    // CHECK STOCK

    public void checkStockAvailability(
            String productName) {

        boolean found = false;

        for (Supplier supplier : supplierList) {

            if (supplier.getProductName()
                    .equalsIgnoreCase(productName)) {

                System.out.println(
                        "Stock Available : "
                        + supplier.getStockQuantity());

                found = true;

                break;
            }
        }

        if (!found) {

            System.out.println(
                    "Product Not Found!");
        }
    }


    // SAVE SUPPLIER TO FILE

    private void saveSupplierToFile(
            Supplier supplier) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "data/suppliers.txt",
                            true);

            writer.write(
                    supplier.toString()
                    + "\n");

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Supplier File Error!");
        }
    }


    // SAVE PURCHASE TO FILE

    private void savePurchaseToFile(
            Purchase purchase) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "data/purchases.txt",
                            true);

            writer.write(
                    purchase.toString()
                    + "\n");

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Purchase File Error!");
        }
    }
    public ArrayList<Supplier> getSupplierList() {
    return supplierList;
}
}