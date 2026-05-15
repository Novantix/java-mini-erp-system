
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
            File file = new File("data/employees.txt");

            if (!file.exists()) {
                System.out.println("Employee file not found");
                return;
            }

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.trim().startsWith("Employee ID")) {
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
            return;
        }

        if (!exists) {
            System.out.println("Invalid id");
            return;
        }

        double pf = calculatePF(payroll.getSalary());
        double tax = calculateTax(payroll.getSalary());
        double bonus = calculateBonus(payroll.getSalary());

        double netSalary = payroll.getSalary() - pf - tax + bonus;

        System.out.println("===== PAYSLIP =====");
        System.out.println("Employee ID: " + payroll.getEmployeeId());
        System.out.println("Basic Salary: " + payroll.getSalary());
        System.out.println("PF: " + pf);
        System.out.println("Tax: " + tax);
        System.out.println("Bonus: " + bonus);
        System.out.println("Net Salary: " + netSalary);

        try {
            FileWriter writer = new FileWriter("data/payrollservicedata.txt", true);

            writer.write("===== PAYSLIP =====\n");
            writer.write("Employee ID: " + payroll.getEmployeeId() + "\n");
            writer.write("Basic Salary: " + payroll.getSalary() + "\n");
            writer.write("PF: " + pf + "\n");
            writer.write("Tax: " + tax + "\n");
            writer.write("Bonus: " + bonus + "\n");
            writer.write("Net Salary: " + netSalary + "\n");
            writer.write("-----------------------\n");

            writer.close();

        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }

  
    public boolean isValidEmployee(int empId) {

        try {
            File file = new File("data/employees.txt");

            if (!file.exists()) {
                System.out.println("Employee file not found");
                return false;
            }

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.trim().startsWith("Employee ID")) {
                    int fileId = Integer.parseInt(line.split(":")[1].trim());

                    if (fileId == empId) {
                        sc.close();
                        return true;
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }

        return false;
    }
}