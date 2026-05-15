package services;

import models.Employee;
import models.Report;
import models.User;
import models.Supplier;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import models.Payroll;
import services.PayrollService;


public class ReportService {

    private List<Report> reportStore = new ArrayList<>();
    private int reportIdCounter = 1;

    private AuditService auditService;
    private PayrollService payrollService = new PayrollService();

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final List<String> VALID_MONTHS = Arrays.asList(
            "january", "february", "march", "april",
            "may", "june", "july", "august",
            "september", "october", "november", "december"
    );
    // Constructor

    public ReportService(AuditService auditService) {
        this.auditService = auditService;
    }

    // Private Helpers — Login Check & Role Check

    private boolean isLoggedIn(User user) {
        if (user == null) {
            System.out.println("[Access Denied] No user is logged in. Please login first.");
            return false;
        }
        return true;
    }

    private boolean hasRole(User user, String... allowedRoles) {
        for (String role : allowedRoles) {
            if (user.getRole().equalsIgnoreCase(role)) {
                return true;
            }
        }
        System.out.println("[Access Denied] User '" + user.getUsername()
                + "' with role '" + user.getRole()
                + "' is not allowed to perform this action.");
        return false;
    }
     private boolean isValidMonth(String month) {
        if (month == null || month.trim().isEmpty()) {
            return false;
        }
 
        // Split by space or dash
        String[] parts = month.trim().split("[\\s-]+");
 
        // Must have exactly 2 parts: month name and year
        if (parts.length != 2) {
            return false;
        }
 
        String monthName = parts[0].toLowerCase();
        String yearStr   = parts[1];
 
        // Check month name is valid
        if (!VALID_MONTHS.contains(monthName)) {
            return false;
        }
 
        // Check year is exactly 4 digits and a real number
        if (!yearStr.matches("\\d{4}")) {
            return false;
        }
 
        int year = Integer.parseInt(yearStr);
 
        // Year must be between 2000 and current year + 1
        int currentYear = LocalDate.now().getYear();
        if (year < 2000 || year > currentYear + 1) {
            return false;
        }
 
        return true;
    }

    // 1. Employee Report  →  ADMIN 
public void generateEmployeeReport(List<Employee> employees, User loggedInUser , int searchId) {

    try {

        // Login check
        if (!isLoggedIn(loggedInUser)) return;

        // Role check
        if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

        // Empty check
        if (employees == null || employees.isEmpty()) {
            System.out.println("[Report] No employee data available.");
            return;
        }

        Employee foundEmployee = null;

        // Search employee
        for (Employee emp : employees) {

            if (emp.getEmployeeId() == searchId) {
                foundEmployee = emp;
                break;
            }
        }

        // If employee not found
        if (foundEmployee == null) {

            System.out.println("[Report] Employee ID "
                    + searchId + " not found.");

            return;
        }

        // Generate report content
        StringBuilder content = new StringBuilder();

        content.append("EMPLOYEE REPORT\n");
        content.append("----------------------------\n");

        content.append(foundEmployee.toString()).append("\n");

        content.append("----------------------------\n");

        Report report = new Report(
                reportIdCounter++,
                "EMPLOYEE",
                loggedInUser.getUsername(),
                content.toString()
        );

        reportStore.add(report);

        // Print report
        System.out.println(report);

        // Audit log
        auditService.logAction(
                loggedInUser.getUsername(),
                "Generated Employee Report for Employee ID : "
                        + searchId
        );

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] generateEmployeeReport : "
                        + e.getMessage()
        );
    }
}

    // 2. Daily Report  →  ALL roles allowed (ADMIN,  EMPLOYEE)-----

    public void generateDailyReport(List<Employee> employees, User loggedInUser) {
        try {
            // Only login check — all roles allowed
            if (!isLoggedIn(loggedInUser)) return;

            String today = LocalDate.now().format(DATE_FORMATTER);

            StringBuilder content = new StringBuilder();
            content.append("DAILY REPORT — ").append(today).append("\n\n");

            int empCount = (employees != null) ? employees.size() : 0;
            content.append("Employees on Record  : ").append(empCount).append("\n");

            Report report = new Report(reportIdCounter++, "DAILY", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Daily Report for " + today + " [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generateDailyReport : " + e.getMessage());
        }
    }

    // 3. Monthly Report  →  ADMIN 
    public void generateMonthlyReport(List<Employee> employees,
                                       String month,
                                       User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;
            if (!isValidMonth(month)) {
                System.out.println("[Report] Invalid month format : '" + month + "'");
                System.out.println("[Report] Please enter a valid format.");
                System.out.println("[Report] Examples : 'May 2025'  or  'June-2025'");
                return;
            }
            StringBuilder content = new StringBuilder();
            content.append("MONTHLY REPORT — ").append(month).append("\n\n");

            int empCount = (employees != null) ? employees.size() : 0;
            content.append("Total Employees     : ").append(empCount).append("\n");

            double totalSalary = 0;
            if (employees != null) {
                for (Employee e : employees) totalSalary += e.getSalary();
            }
            content.append("Total Salary Paid   : Rs. ").append(String.format("%.2f", totalSalary)).append("\n");

            Report report = new Report(reportIdCounter++, "MONTHLY", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Monthly Report for " + month + " [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generateMonthlyReport : " + e.getMessage());
        }
    }

    // 4. View All Reports  →  ADMIN 
    public void viewAllReports(User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

            if (reportStore.isEmpty()) {
                System.out.println("[Report] No reports generated yet.");
                return;
            }

            System.out.println("\n===== ALL GENERATED REPORTS =====");
            for (Report r : reportStore) {
                System.out.println("Report ID   : " + r.getReportId());
                System.out.println("Report Type : " + r.getReportType());
                System.out.println("Generated By: " + r.getGeneratedBy());
                System.out.println("Generated At: " + r.getGeneratedAt());
                System.out.println("----------------------------------");
            }

            auditService.logAction(loggedInUser.getUsername(), "Viewed all reports list");

        } catch (Exception e) {
            System.out.println("[ReportService Error] viewAllReports : " + e.getMessage());
        }
    }

    // 5. Export Report to File  →  ADMIN only

    public void exportReport(int reportId, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN")) return;

            Report target = null;
            for (Report r : reportStore) {
                if (r.getReportId() == reportId) {
                    target = r;
                    break;
                }
            }

            if (target == null) {
                System.out.println("[Report] Report ID " + reportId + " not found.");
                return;
            }

            String fileName = "reports/Report_" + reportId + "_" + target.getReportType() + ".txt";

            java.io.File dir = new java.io.File("reports");
            if (!dir.exists()) dir.mkdirs();

            FileWriter fw = new FileWriter(fileName);
            fw.write(target.toString());
            fw.close();

            System.out.println("[Report] Report exported successfully to : " + fileName);
            auditService.logAction(loggedInUser.getUsername(), "Exported Report [ID: " + reportId + "] to " + fileName);

        } catch (IOException e) {
            System.out.println("[ReportService Error] exportReport : " + e.getMessage());
        }
    }

    // 6. Data Summary  →  ALL roles allowed (ADMIN,  EMPLOYEE)
    public void printDataSummary(List<Employee> employees, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;

            System.out.println("\n========== DATA SUMMARY ==========");
            System.out.println("Logged in as  : " + loggedInUser.getUsername()
                             + " [" + loggedInUser.getRole() + "]");
            System.out.println("----------------------------------");
            System.out.println("Total Employees  : " + (employees != null ? employees.size() : 0));
            System.out.println("==================================\n");

            auditService.logAction(loggedInUser.getUsername(), "Viewed Data Summary");

        } catch (Exception e) {
            System.out.println("[ReportService Error] printDataSummary : " + e.getMessage());
        }
    }
    // Integration with PayrollService for Payroll Report 
    
    

    public void generatePayrollReport(List<Payroll> payrollList,
                                  User loggedInUser) {

    try {

        // Login check
        if (!isLoggedIn(loggedInUser)) return;

        // Role check
        if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

        // Empty list check
        if (payrollList == null || payrollList.isEmpty()) {

            System.out.println(
                    "[Payroll Report] No payroll data available."
            );

            return;
        }

        StringBuilder content = new StringBuilder();

        content.append("=========== PAYROLL REPORT ===========\n\n");

        double totalNetSalary = 0;

        for (Payroll payroll : payrollList) {

            double salary = payroll.getSalary();

            double pf =
                    payrollService.calculatePF(salary);

            double tax =
                    payrollService.calculateTax(salary);

            double bonus =
                    payrollService.calculateBonus(salary);

            double netSalary =
                    salary - pf - tax + bonus;

            content.append("Employee ID : ")
                   .append(payroll.getEmployeeId())
                   .append("\n");

            content.append("Basic Salary : Rs. ")
                   .append(String.format("%.2f", salary))
                   .append("\n");

            content.append("PF : Rs. ")
                   .append(String.format("%.2f", pf))
                   .append("\n");

            content.append("Tax : Rs. ")
                   .append(String.format("%.2f", tax))
                   .append("\n");

            content.append("Bonus : Rs. ")
                   .append(String.format("%.2f", bonus))
                   .append("\n");

            content.append("Net Salary : Rs. ")
                   .append(String.format("%.2f", netSalary))
                   .append("\n");

            content.append("-----------------------------------\n");

            totalNetSalary += netSalary;
        }

        content.append("\nTOTAL PAYROLL EXPENSE : Rs. ")
               .append(String.format("%.2f", totalNetSalary))
               .append("\n");

        Report report = new Report(
                reportIdCounter++,
                "PAYROLL",
                loggedInUser.getUsername(),
                content.toString()
        );

        reportStore.add(report);

        System.out.println(report);

        auditService.logAction(
                loggedInUser.getUsername(),
                "Generated Payroll Report [ID: "
                        + report.getReportId()
                        + "]"
        );

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] generatePayrollReport : "
                        + e.getMessage()
        );
    }
}

    // Getter — for BackupService 
    public List<Report> getReportStore() {
        return reportStore;
    }

    // ....................... methods for pending modules .......................
    public void generateAttendanceReport(User loggedInUser) {
        System.out.println("[Notice] Attendance Report module is pending integration.");
    }
public void generateSalesReport(User loggedInUser,
                                int supplierId,
                                String productName) {

    try {

        if (!isLoggedIn(loggedInUser)) return;

        if (!hasRole(loggedInUser,
                "ADMIN",
                "MANAGER")) return;

        java.io.File file =
                new java.io.File("data/suppliers.txt");

        if (!file.exists()) {

            System.out.println(
                    "Supplier File Not Found");

            return;
        }

        java.util.Scanner scanner =
                new java.util.Scanner(file);

        boolean found = false;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.startsWith("Supplier ID")) {

                int id = Integer.parseInt(
                        line.split(":")[1].trim());

                String supplierName =
                        scanner.nextLine()
                                .split(":")[1]
                                .trim();

                String fileProduct =
                        scanner.nextLine()
                                .split(":")[1]
                                .trim();

                int stock =
                        Integer.parseInt(
                                scanner.nextLine()
                                        .split(":")[1]
                                        .trim());

                if (id == supplierId
                        &&
                        fileProduct.equalsIgnoreCase(
                                productName)) {

                    System.out.println(
                            "\n===== SALES REPORT =====");

                    System.out.println(
                            "Supplier ID : " + id);

                    System.out.println(
                            "Supplier Name : "
                                    + supplierName);

                    System.out.println(
                            "Product Name : "
                                    + fileProduct);

                    System.out.println(
                            "Stock Quantity : "
                                    + stock);

                    found = true;

                    break;
                }
            }
        }

        scanner.close();

        if (!found) {

            System.out.println(
                    "Sales Report Not Found");
        }

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] "
                        + e.getMessage());
    }
}
public void viewDailyReports(User loggedInUser) {

    try {

        if (!isLoggedIn(loggedInUser)) {
            return;
        }

        java.io.File file =
                new java.io.File(
                        "data/daily_report.txt");

        if (!file.exists()) {

            System.out.println(
                    "No Daily Reports Found");

            return;
        }

        java.util.Scanner scanner =
                new java.util.Scanner(file);

        System.out.println(
                "\n===== DAILY REPORTS =====");

        while (scanner.hasNextLine()) {

            System.out.println(
                    scanner.nextLine());
        }

        scanner.close();

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] "
                        + e.getMessage());
    }
}
public void viewMonthlyReports(User loggedInUser) {

    try {

        if (!isLoggedIn(loggedInUser)) {
            return;
        }

        java.io.File file =
                new java.io.File(
                        "data/monthly_report.txt");

        if (!file.exists()) {

            System.out.println(
                    "No Monthly Reports Found");

            return;
        }

        java.util.Scanner scanner =
                new java.util.Scanner(file);

        System.out.println(
                "\n===== MONTHLY REPORTS =====");

        while (scanner.hasNextLine()) {

            System.out.println(
                    scanner.nextLine());
        }

        scanner.close();

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] "
                        + e.getMessage());
    }
}
}