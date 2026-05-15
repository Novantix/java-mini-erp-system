package services;

import java.io.*;
import java.util.*;
import models.Product;

public class InventoryService {

    private ArrayList<Product> products = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private final String FILE = "data/inventoryservice.txt";

    public InventoryService() {
        loadFromFile();
    }

    // ================= DASHBOARD =================
    public void inventoryDashboard() {

        int choice;

        do {
            System.out.println("\n========== INVENTORY MANAGEMENT ==========");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. View Low Stock Alert");
            System.out.println("4. View Inventory Report");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> updateProduct();
                case 3 -> lowStockAlert();
                case 4 -> inventoryReport();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ================= ADD =================
    public void addProduct() {

        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (findProductById(id) != null) {
            System.out.println("Product already exists.");
            return;
        }

        scanner.nextLine();

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = readInt();

        System.out.print("Enter Price: ");
        double price = readDouble();

        System.out.print("Enter Reorder Level: ");
        int reorder = readInt();

        products.add(new Product(id, name, qty, price, reorder));

        saveToFile(); // ✅ IMPORTANT

        System.out.println("Product added successfully.");
    }

    // ================= UPDATE =================
    public void updateProduct() {

        loadFromFile(); // ✅ latest data

        System.out.print("Enter Product ID: ");
        int id = readInt();

        Product p = findProductById(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter New Quantity: ");
        p.setQuantity(readInt());

        System.out.print("Enter New Price: ");
        p.setPrice(readDouble());

        saveToFile(); // ✅ IMPORTANT FIX

        System.out.println("Product updated successfully.");
    }

    // ================= LOW STOCK =================
    public void lowStockAlert() {

        loadFromFile(); // ✅ read from file

        boolean found = false;

        for (Product p : products) {

            if (p.getQuantity() <= p.getReorderLevel()) {

                System.out.println(p);
                System.out.println("*** LOW STOCK ***");
                System.out.println("----------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("No low stock products.");
        }
    }

    // ================= REPORT =================
    public void inventoryReport() {

        loadFromFile(); // ✅ read file

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product p : products) {
            System.out.println(p);
            System.out.println("----------------");
        }
    }

    // ================= SAVE FILE =================
    private void saveToFile() {

        try {
            FileWriter fw = new FileWriter(FILE);

            for (Product p : products) {
                fw.write(
                        p.getProductId() + "," +
                        p.getProductName() + "," +
                        p.getQuantity() + "," +
                        p.getPrice() + "," +
                        p.getReorderLevel() + "\n"
                );
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

    // ================= LOAD FILE =================
    private void loadFromFile() {

        products.clear();

        try {
            File file = new File(FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");

                products.add(new Product(
                        Integer.parseInt(d[0]),
                        d[1],
                        Integer.parseInt(d[2]),
                        Double.parseDouble(d[3]),
                        Integer.parseInt(d[4])
                ));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Read Error: " + e.getMessage());
        }
    }

    // ================= HELPERS =================
    private Product findProductById(int id) {
        for (Product p : products) {
            if (p.getProductId() == id) return p;
        }
        return null;
    }

    private int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.print("Enter number: ");
                scanner.next();
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.print("Enter valid price: ");
                scanner.next();
            }
        }
    }
}