package services;
import models.Payroll;
import java.io.FileWriter;
import java.io.IOException;

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

        double pf = calculatePF(payroll.getSalary());
        double tax = calculateTax(payroll.getSalary());
        double bonus = calculateBonus(payroll.getSalary());

        double netSalary =
                payroll.getSalary() - pf - tax + bonus;

        System.out.println("===== PAYSLIP =====");
        System.out.println("Employee ID: " + payroll.getEmployeeId());
        System.out.println("Basic Salary: " + payroll.getSalary());
        System.out.println("PF: " + pf);
        System.out.println("Tax: " + tax);
        System.out.println("Bonus: " + bonus);
        System.out.println("Net Salary: " + netSalary);

        // FILE WRITING
        try {

            FileWriter writer =
                    new FileWriter("data/payrollservicedata.txt", true);

            writer.write("===== PAYSLIP =====\n");
            writer.write("Employee ID: "
                    + payroll.getEmployeeId() + "\n");

            writer.write("Basic Salary: "
                    + payroll.getSalary() + "\n");

            writer.write("PF: " + pf + "\n");
            writer.write("Tax: " + tax + "\n");
            writer.write("Bonus: " + bonus + "\n");
            writer.write("Net Salary: "
                    + netSalary + "\n");

            writer.write("-----------------------\n");

            writer.close();

            System.out.println("Payroll saved to file.");

        } catch (IOException e) {

            System.out.println("File Error: "
                    + e.getMessage());
        }
    }
}

