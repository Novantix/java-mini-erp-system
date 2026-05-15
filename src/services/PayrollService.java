// // // package services;

// // // import java.io.FileWriter;
// // // import java.io.IOException;
// // // import models.Employee;
// // // import models.Payroll;

// // // public class PayrollService {

// // //     public double calculatePF(double salary) {
// // //         return salary * 0.12;
// // //     }

// // //     public double calculateTax(double salary) {
// // //         return salary * 0.10;
// // //     }

// // //     public double calculateBonus(double salary) {
// // //         return salary * 0.05;
// // //     }

// // //     public void generatePayslip(Payroll payroll) {

// // //         EmployeeService employeeService =
// // //                 new EmployeeService();

// // //         boolean exists = false;

// // //         // CHECK EMPLOYEE ID
// // //         for (Employee emp : employeeService.getEmployees()) {

// // //             System.out.println(
// // //                     "Employee ID from file: "
// // //                             + emp.getEmployeeId());

// // //             System.out.println(
// // //                     "Entered Employee ID: "
// // //                             + payroll.getEmployeeId());

// // //             if (String.valueOf(emp.getEmployeeId()).trim()
// // //                     .equals(
// // //                             String.valueOf(
// // //                                     payroll.getEmployeeId()).trim())) {

// // //                 exists = true;
// // //                 break;
// // //             }
// // //         }

// // //         // INVALID ID CHECK
// // //         if (!exists) {
// // //             System.out.println("Invalid id");
// // //             return;
// // //         }

// // //         // CALCULATIONS
// // //         double pf =
// // //                 calculatePF(payroll.getSalary());

// // //         double tax =
// // //                 calculateTax(payroll.getSalary());

// // //         double bonus =
// // //                 calculateBonus(payroll.getSalary());

// // //         double netSalary =
// // //                 payroll.getSalary() - pf - tax + bonus;

// // //         // DISPLAY PAYSLIP
// // //         System.out.println("\n===== PAYSLIP =====");

// // //         System.out.println("Employee ID: "
// // //                 + payroll.getEmployeeId());

// // //         System.out.println("Month: "
// // //                 + payroll.getMonth());

// // //         System.out.println("Year: "
// // //                 + payroll.getYear());

// // //         System.out.println("Basic Salary: "
// // //                 + payroll.getSalary());

// // //         System.out.println("PF: " + pf);

// // //         System.out.println("Tax: " + tax);

// // //         System.out.println("Bonus: " + bonus);

// // //         System.out.println("Net Salary: "
// // //                 + netSalary);

// // //         // SAVE TO FILE
// // //         try {

// // //             FileWriter writer =
// // //                     new FileWriter(
// // //                             "data/payrollservicedata.txt",
// // //                             true);

// // //             writer.write("===== PAYSLIP =====\n");

// // //             writer.write("Employee ID: "
// // //                     + payroll.getEmployeeId() + "\n");

// // //             writer.write("Month: "
// // //                     + payroll.getMonth() + "\n");

// // //             writer.write("Year: "
// // //                     + payroll.getYear() + "\n");

// // //             writer.write("Basic Salary: "
// // //                     + payroll.getSalary() + "\n");

// // //             writer.write("PF: " + pf + "\n");

// // //             writer.write("Tax: " + tax + "\n");

// // //             writer.write("Bonus: " + bonus + "\n");

// // //             writer.write("Net Salary: "
// // //                     + netSalary + "\n");

// // //             writer.write(
// // //                     "-----------------------\n");

// // //             writer.close();

// // //             System.out.println(
// // //                     "Payslip saved successfully!");

// // //         } catch (IOException e) {

// // //             System.out.println(
// // //                     "File Error: "
// // //                             + e.getMessage());
// // //         }
// // //     }
// // // }
// // package services;

// // import java.io.FileWriter;
// // import java.io.IOException;
// // import models.Employee;
// // import models.Payroll;

// // public class PayrollService {

// //     public double calculatePF(double salary) {
// //         return salary * 0.12;
// //     }

// //     public double calculateTax(double salary) {
// //         return salary * 0.10;
// //     }

// //     public double calculateBonus(double salary) {
// //         return salary * 0.05;
// //     }

// //     public void generatePayslip(Payroll payroll) {

// //         EmployeeService employeeService = new EmployeeService();

// //         boolean exists = false;

// //         // ✅ CHECK EMPLOYEE ID (DIRECT INT COMPARISON)
// //         for (Employee emp : employeeService.getEmployees()) {

// //             if (emp.getEmployeeId() == payroll.getEmployeeId()) {
// //                 exists = true;
// //                 break;
// //             }
// //         }

// //         // ❌ INVALID ID
// //         if (!exists) {
// //             System.out.println("Invalid id");
// //             return;
// //         }

// //         // ================= CALCULATIONS =================
// //         double pf = calculatePF(payroll.getSalary());
// //         double tax = calculateTax(payroll.getSalary());
// //         double bonus = calculateBonus(payroll.getSalary());

// //         double netSalary =
// //                 payroll.getSalary() - pf - tax + bonus;

// //         // ================= DISPLAY =================
// //         System.out.println("\n===== PAYSLIP =====");
// //         System.out.println("Employee ID: " + payroll.getEmployeeId());
// //         System.out.println("Month: " + payroll.getMonth());
// //         System.out.println("Year: " + payroll.getYear());
// //         System.out.println("Basic Salary: " + payroll.getSalary());
// //         System.out.println("PF: " + pf);
// //         System.out.println("Tax: " + tax);
// //         System.out.println("Bonus: " + bonus);
// //         System.out.println("Net Salary: " + netSalary);

// //         // ================= FILE SAVE =================
// //         try {

// //             FileWriter writer =
// //                     new FileWriter("data/payrollservicedata.txt", true);

// //             writer.write("===== PAYSLIP =====\n");
// //             writer.write("Employee ID: " + payroll.getEmployeeId() + "\n");
// //             writer.write("Month: " + payroll.getMonth() + "\n");
// //             writer.write("Year: " + payroll.getYear() + "\n");
// //             writer.write("Basic Salary: " + payroll.getSalary() + "\n");
// //             writer.write("PF: " + pf + "\n");
// //             writer.write("Tax: " + tax + "\n");
// //             writer.write("Bonus: " + bonus + "\n");
// //             writer.write("Net Salary: " + netSalary + "\n");
// //             writer.write("-----------------------\n");

// //             writer.close();

// //             System.out.println("Payslip saved successfully!");

// //         } catch (IOException e) {
// //             System.out.println("File Error: " + e.getMessage());
// //         }
// //     }
// // }
// package models;

// public class Product {

//     private int productId;
//     private String productName;
//     private int quantity;
//     private double price;
//     private int reorderLevel;

//     public Product() {}

//     public Product(int productId, String productName, int quantity,
//                    double price, int reorderLevel) {
//         this.productId = productId;
//         this.productName = productName;
//         this.quantity = quantity;
//         this.price = price;
//         this.reorderLevel = reorderLevel;
//     }

//     public int getProductId() { return productId; }
//     public String getProductName() { return productName; }
//     public int getQuantity() { return quantity; }
//     public double getPrice() { return price; }
//     public int getReorderLevel() { return reorderLevel; }

//     public void setQuantity(int quantity) { this.quantity = quantity; }
//     public void setPrice(double price) { this.price = price; }

//     @Override
//     public String toString() {
//         return "productId=" + productId +
//                " productName='" + productName + "'" +
//                " quantity=" + quantity +
//                " price=" + price +
//                " reorderLevel=" + reorderLevel;
//     }
// }
package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import models.Payroll;

public class PayrollService {

    public double calculatePF(double salary) {
        return salary * 0.12;
    }

    public double calculateTax(double salary) {
        return salary * 0.10;
    }

    public double calculateBonus(double salary) {
        return salary * 0.05;
    }

    public void generatePayslip(Payroll payroll) {

        boolean exists = false;

        try {
            File file = new File("src/data/employees.txt");

            if (!file.exists()) {
                System.out.println("Employee file not found");
                return;
            }

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.startsWith("Employee ID")) {
                    int fileId = Integer.parseInt(line.split(":")[1].trim());

                    if (fileId == payroll.getEmployeeId()) {
                        exists = true;
                        break;
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!exists) {
            System.out.println("Invalid id");
            return;
        }

        double pf = calculatePF(payroll.getSalary());
        double tax = calculateTax(payroll.getSalary());
        double bonus = calculateBonus(payroll.getSalary());

        double netSalary =
                payroll.getSalary() - pf - tax + bonus;

        System.out.println("\n===== PAYSLIP =====");
        System.out.println("Employee ID: " + payroll.getEmployeeId());
        System.out.println("Month: " + payroll.getMonth());
        System.out.println("Year: " + payroll.getYear());
        System.out.println("Basic Salary: " + payroll.getSalary());
        System.out.println("PF: " + pf);
        System.out.println("Tax: " + tax);
        System.out.println("Bonus: " + bonus);
        System.out.println("Net Salary: " + netSalary);

        try {
            FileWriter writer =
                    new FileWriter("data/payrollservicedata.txt", true);

            writer.write("===== PAYSLIP =====\n");
            writer.write("Employee ID: " + payroll.getEmployeeId() + "\n");
            writer.write("Month: " + payroll.getMonth() + "\n");
            writer.write("Year: " + payroll.getYear() + "\n");
            writer.write("Basic Salary: " + payroll.getSalary() + "\n");
            writer.write("PF: " + pf + "\n");
            writer.write("Tax: " + tax + "\n");
            writer.write("Bonus: " + bonus + "\n");
            writer.write("Net Salary: " + netSalary + "\n");
            writer.write("-----------------------\n");

            writer.close();

            System.out.println("Payslip saved successfully!");

        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
}
