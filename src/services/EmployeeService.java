package services;

import models.Employee;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeService {
    private ArrayList<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "data/employees.txt";

    public EmployeeService() {
        loadEmployees();
    }

    // =========================
    // DASHBOARD
    // =========================
    public void employeeDashboard() {
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("        EMPLOYEE MANAGEMENT");
            System.out.println("========================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. View All Employees");
            System.out.println("6. Transfer Department");
            System.out.println("7. Promote Employee");
            System.out.println("8. View Promotion Data");
            System.out.println("9. Manager Functions");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> updateEmployee();
                case 3 -> deleteEmployee();
                case 4 -> searchEmployee();
                case 5 -> viewAllEmployees();
                case 6 -> transferDepartment();
                case 7 -> promoteEmployee();
                case 8 -> viewPromotionData();
                case 9 -> managerFunctions();
                case 0 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    // =========================
    // ADD EMPLOYEE
    // =========================
    public void addEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = readInt();

        if (isDuplicateId(id)) {
            System.out.println("Employee ID already exists.");
            return;
        }

        scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();

        System.out.print("Enter Designation: ");
        String designation = scanner.nextLine();

        System.out.print("Enter Salary: ");
        double salary = readDouble();

        scanner.nextLine();

        System.out.print("Enter Manager Name: ");
        String manager = scanner.nextLine();

        System.out.print("Enter Years of Experience: ");
        int exp = readInt();

        Employee employee = new Employee(
                id,
                name,
                dept,
                designation,
                salary,
                manager,
                exp,
                "Not Promoted"
        );

        employees.add(employee);
        saveEmployees();

        System.out.println("Employee added successfully.");
    }

    // =========================
    // UPDATE EMPLOYEE
    // =========================
    public void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        Employee emp = findEmployeeById(readInt());

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        scanner.nextLine();

        System.out.print("Enter New Designation: ");
        emp.setDesignation(scanner.nextLine());

        System.out.print("Enter New Salary: ");
        emp.setSalary(readDouble());

        scanner.nextLine();

        System.out.print("Enter New Manager Name: ");
        emp.setManagerName(scanner.nextLine());

        saveEmployees();
        System.out.println("Employee updated successfully.");
    }

    // =========================
    // DELETE EMPLOYEE
    // =========================
    public void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        Employee emp = findEmployeeById(readInt());

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        employees.remove(emp);
        saveEmployees();

        System.out.println("Employee deleted successfully.");
    }

    // =========================
    // SEARCH EMPLOYEE
    // =========================
    public void searchEmployee() {
        scanner.nextLine();
        System.out.print("Enter Employee ID or Name: ");
        String input = scanner.nextLine();

        boolean found = false;

        for (Employee emp : employees) {
            if (String.valueOf(emp.getEmployeeId()).equals(input)
                    || emp.getEmployeeName().equalsIgnoreCase(input)) {
                System.out.println(emp);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Employee not found.");
        }
    }

    // =========================
    // VIEW ALL EMPLOYEES
    // =========================
    public void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        for (Employee emp : employees) {
            System.out.println(emp);
            System.out.println("-----------------------------------");
        }
    }

    // =========================
    // TRANSFER DEPARTMENT
    // =========================
    public void transferDepartment() {
        System.out.print("Enter Employee ID: ");
        Employee emp = findEmployeeById(readInt());

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        scanner.nextLine();
        System.out.print("Enter New Department: ");
        emp.setDepartment(scanner.nextLine());

        saveEmployees();
        System.out.println("Department transferred successfully.");
    }

    // =========================
    // PROMOTE EMPLOYEE
    // =========================
    public void promoteEmployee() {
        System.out.print("Enter Employee ID: ");
        Employee emp = findEmployeeById(readInt());

        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        if (emp.getYearsOfExperience() >= 3) {
            emp.setSalary(emp.getSalary() * 1.20);
            emp.setPromotionStatus("Promoted");
            saveEmployees();
            System.out.println("Employee promoted successfully.");
        } else {
            System.out.println("Employee is not eligible for promotion.");
        }
    }

    // =========================
    // VIEW PROMOTION DATA
    // =========================
    public void viewPromotionData() {
        boolean found = false;

        for (Employee emp : employees) {
            if ("Promoted".equalsIgnoreCase(emp.getPromotionStatus())) {
                System.out.println(emp);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No promoted employees found.");
        }
    }

    // =========================
    // MANAGER FUNCTIONS
    // =========================
    public void managerFunctions() {
        int choice;

        do {
            System.out.println("\n1. View Employees Under a Manager");
            System.out.println("2. Count Employees Under a Manager");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            choice = readInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Manager Name: ");
                    String manager = scanner.nextLine();

                    for (Employee emp : employees) {
                        if (emp.getManagerName().equalsIgnoreCase(manager)) {
                            System.out.println(emp);
                        }
                    }
                }
                case 2 -> {
                    System.out.print("Enter Manager Name: ");
                    String manager = scanner.nextLine();

                    int count = 0;
                    for (Employee emp : employees) {
                        if (emp.getManagerName().equalsIgnoreCase(manager)) {
                            count++;
                        }
                    }

                    System.out.println("Employees under " + manager + ": " + count);
                }
                case 3 -> System.out.println("Returning...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    // =========================
    // HELPER METHODS
    // =========================
    private Employee findEmployeeById(int employeeId) {
        for (Employee emp : employees) {
            if (emp.getEmployeeId() == employeeId) {
                return emp;
            }
        }
        return null;
    }

    private boolean isDuplicateId(int employeeId) {
        return findEmployeeById(employeeId) != null;
    }

    private int readInt() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a valid amount: ");
                scanner.next();
            }
        }
    }

    // =========================
    // SAVE EMPLOYEES TO TXT FILE
    // =========================
    private void saveEmployees() {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            PrintWriter writer = new PrintWriter(FILE_PATH);

            for (Employee emp : employees) {
                writer.println("Employee ID      : " + emp.getEmployeeId());
                writer.println("Employee Name    : " + emp.getEmployeeName());
                writer.println("Department       : " + emp.getDepartment());
                writer.println("Designation      : " + emp.getDesignation());
                writer.println("Salary           : " + emp.getSalary());
                writer.println("Manager Name     : " + emp.getManagerName());
                writer.println("Experience       : " + emp.getYearsOfExperience() + " years");
                writer.println("Promotion Status : " + emp.getPromotionStatus());
                writer.println("----------------------------------------");
            }

            writer.close();
            System.out.println("Employee data saved successfully to " + FILE_PATH);

        } catch (Exception e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    // =========================
    // LOAD EMPLOYEES
    // =========================
    private void loadEmployees() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            employees = new ArrayList<>();
            return;
        }

        employees = new ArrayList<>();

        System.out.println("Existing employee data found at: " + FILE_PATH);
    }
<<<<<<< HEAD
    public ArrayList<Employee> getEmployees() {
        return employees;
    }
=======
>>>>>>> b6d2e2bb1af2bf097532589377c68e9696b8ad6a
}
