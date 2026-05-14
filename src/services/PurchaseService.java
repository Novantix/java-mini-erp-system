package services;
import java.io.FileWriter;
import java.util.ArrayList;
import models.Purchase;
import models.Supplier;

public class PurchaseService {

    ArrayList<Supplier> supplierList = new ArrayList<>();
    ArrayList<Purchase> purchaseList = new ArrayList<>();

    public void addSupplier(Supplier supplier) {

        supplierList.add(supplier);

        System.out.println("Supplier Added Successfully");
        try {

    FileWriter writer =
            new FileWriter(
                    "src/data/suppliers.txt",
                    true);

    writer.write(
            supplier.toString()
            + "\n");

    writer.close();

} catch (Exception e) {

    System.out.println(
            "File Error!");
}
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

    purchaseList.add(purchase);

    System.out.println(
            "Purchase Added Successfully");
         try {

    FileWriter writer =
            new FileWriter(
                    "src/data/purchases.txt",
                    true);

    writer.write(
            purchase.toString()
            + "\n");

    writer.close();

} catch (Exception e) {

    System.out.println(
            "File Error!");
}   
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

   public void checkStockAvailability(
        String productName) {

    boolean found = false;

    for (Supplier s : supplierList) {

        if (s.getProductName()
                .equalsIgnoreCase(productName)) {

            System.out.println(
                    "Stock Available : "
                    + s.getStockQuantity());

            found = true;
        }
    }

    if (!found) {

        System.out.println(
                "Product Not Found!");
    }
}
    
}