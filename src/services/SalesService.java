package services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import models.Customer;
import models.Sale;

public class SalesService {

    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Sale> sales = new ArrayList<>();

    public void addCustomer(Scanner sc) {

        System.out.println("Enter Customer ID:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Customer Name:");
        String name = sc.nextLine();

        System.out.println("Enter Phone Number:");
        String phone = sc.nextLine();

        Customer customer = new Customer(id, name, phone);

        customers.add(customer);

        saveCustomer(customer);

        System.out.println("Customer Added Successfully");
    }

    public void viewCustomers() {

        if (customers.isEmpty()) {

            System.out.println("No Customers Found");
            return;
        }

        for (Customer customer : customers) {

            System.out.println(customer);
        }
    }

    public void addSale(Scanner sc) {

        System.out.println("Enter Sale ID:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Product Name:");
        String productName = sc.nextLine();

        System.out.println("Enter Amount:");
        double amount = sc.nextDouble();
        sc.nextLine();

        Sale sale = new Sale(id, productName, amount);

        sales.add(sale);

        saveSale(sale);

        System.out.println("Sale Added Successfully");
    }

    public void viewSales() {

        if (sales.isEmpty()) {

            System.out.println("No Sales Found");
            return;
        }

        for (Sale sale : sales) {

            System.out.println(sale);
        }
    }

    public void generateInvoice() {

        if (sales.isEmpty()) {

            System.out.println("No Sales Available");
            return;
        }

        for (Sale sale : sales) {

            System.out.println("\n------------ INVOICE ------------");

            System.out.println("Sale ID       : " + sale.getSaleId());

            System.out.println("Product Name  : " + sale.getProductName());

            System.out.println("Amount        : " + sale.getAmount());

            System.out.println("GST 18%       : " + sale.getGst());

            System.out.println("Final Amount  : " + sale.getFinalAmount());

            System.out.println("---------------------------------");
        }
    }

    private void saveCustomer(Customer customer) {

        try {

            FileWriter fw = new FileWriter("data/customers.txt", true);

            fw.write(customer.toString() + "\n");

            fw.close();

        } catch (IOException e) {

            System.out.println("Error Saving Customer");
        }
    }

    private void saveSale(Sale sale) {

        try {

            FileWriter fw = new FileWriter("data/sales.txt", true);

            fw.write(sale.toString() + "\n");

            fw.close();

        } catch (IOException e) {

            System.out.println("Error Saving Sale");
        }
    }
}