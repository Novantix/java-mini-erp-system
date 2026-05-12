package services;
import models.Employee;
import models.Purchase;
import models.Supplier;
import models.Report;
import models.User;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    private List<Report> reportStore = new ArrayList<>();
    private int reportIdCounter = 1;

    private AuditService auditService;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

      //  Employee Report  →  ADMIN and MANAGER only
   

    public void generateEmployeeReport(List<Employee> employees, User loggedInUser) {
        try {
            //  Login check — is anyone logged in?
            if (!isLoggedIn(loggedInUser)) return;

            // Role check — only ADMIN or MANAGER
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

            if (employees == null || employees.isEmpty()) {
                System.out.println("[Report] No employee data available to generate report.");
                return;
            }

            StringBuilder content = new StringBuilder();
            content.append("EMPLOYEE REPORT\n");
            content.append("Total Employees : ").append(employees.size()).append("\n\n");

            double totalSalary = 0;
            for (Employee emp : employees) {
                content.append("----------------------------\n");
                content.append(emp.toString()).append("\n");
                totalSalary += emp.getSalary();
            }

            content.append("----------------------------\n");
            content.append("Total Salary Expense : Rs. ").append(String.format("%.2f", totalSalary)).append("\n");

            Report report = new Report(reportIdCounter++, "EMPLOYEE", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Employee Report [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generateEmployeeReport : " + e.getMessage());
        }
    }

    // Purchase Report  →  ADMIN and MANAGER only

    
    public void generatePurchaseReport(List<Purchase> purchases, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

            if (purchases == null || purchases.isEmpty()) {
                System.out.println("[Report] No purchase data available to generate report.");
                return;
            }

            StringBuilder content = new StringBuilder();
            content.append("PURCHASE REPORT\n");
            content.append("Total Purchases : ").append(purchases.size()).append("\n\n");

            double totalAmount = 0;
            for (Purchase p : purchases) {
                content.append("----------------------------\n");
                content.append(p.toString()).append("\n");
                totalAmount += p.getAmount();
            }

            content.append("----------------------------\n");
            content.append("Total Purchase Amount : Rs. ").append(String.format("%.2f", totalAmount)).append("\n");

            Report report = new Report(reportIdCounter++, "PURCHASE", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Purchase Report [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generatePurchaseReport : " + e.getMessage());
        }
    }

    //  3. Supplier Report  →  ADMIN and MANAGER only

    public void generateSupplierReport(List<Supplier> suppliers, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER")) return;

            if (suppliers == null || suppliers.isEmpty()) {
                System.out.println("[Report] No supplier data available to generate report.");
                return;
            }

            StringBuilder content = new StringBuilder();
            content.append("SUPPLIER REPORT\n");
            content.append("Total Suppliers : ").append(suppliers.size()).append("\n\n");

            int totalStock = 0;
            for (Supplier s : suppliers) {
                content.append("----------------------------\n");
                content.append(s.toString()).append("\n");
                totalStock += s.getStockQuantity();
            }

            content.append("----------------------------\n");
            content.append("Total Stock Available : ").append(totalStock).append(" units\n");

            Report report = new Report(reportIdCounter++, "SUPPLIER", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Supplier Report [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generateSupplierReport : " + e.getMessage());
        }
    }

    //  4. Daily Report  →  ALL roles allowed (ADMIN, MANAGER, EMPLOYEE) 


    public void generateDailyReport(List<Employee> employees,
                                     List<Purchase> purchases,
                                     List<Supplier> suppliers,
                                     User loggedInUser) {
        try {
            // Only login check — all roles allowed
            if (!isLoggedIn(loggedInUser)) return;

            String today = LocalDate.now().format(DATE_FORMATTER);

            StringBuilder content = new StringBuilder();
            content.append("DAILY REPORT — ").append(today).append("\n\n");

            int empCount = (employees != null) ? employees.size() : 0;
            content.append("Employees on Record  : ").append(empCount).append("\n");

            double totalPurchase = 0;
            int purchaseCount = 0;
            if (purchases != null) {
                purchaseCount = purchases.size();
                for (Purchase p : purchases) totalPurchase += p.getAmount();
            }
            content.append("Purchases Today      : ").append(purchaseCount).append("\n");
            content.append("Total Purchase Value : Rs. ").append(String.format("%.2f", totalPurchase)).append("\n");

            int supplierCount = (suppliers != null) ? suppliers.size() : 0;
            int totalStock = 0;
            if (suppliers != null) {
                for (Supplier s : suppliers) totalStock += s.getStockQuantity();
            }
            content.append("Active Suppliers     : ").append(supplierCount).append("\n");
            content.append("Total Stock Units    : ").append(totalStock).append("\n");

            Report report = new Report(reportIdCounter++, "DAILY", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);

            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Daily Report for " + today + " [ID: " + report.getReportId() + "]");

        } catch (Exception e) {
            System.out.println("[ReportService Error] generateDailyReport : " + e.getMessage());
        }
    }

  
    //Getter — for BackupService (Module 8) or other modules
   
    public List<Report> getReportStore() {
        return reportStore;
    }
}
