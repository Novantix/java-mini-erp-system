package services;

import models.Employee;
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

    // 1. Employee Report  →  ADMIN 

    public void generateEmployeeReport(List<Employee> employees, User loggedInUser) {
        try {
            //  Login check — is anyone logged in?
            if (!isLoggedIn(loggedInUser)) return;

            //  Role check — only ADMIN or MANAGER
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

    // Getter — for BackupService 
    public List<Report> getReportStore() {
        return reportStore;
    }

    // ....................... methods for pending modules .......................
    public void generateAttendanceReport(User loggedInUser) {
        System.out.println("[Notice] Attendance Report module is pending integration.");
    }

    public void generateSalesReport(User loggedInUser) {
        System.out.println("[Notice] Sales Report module is pending integration.");
    }

    public void generatePayrollReport(User loggedInUser) {
        System.out.println("[Notice] Payroll Report module is pending integration.");
    }

    public void viewDailyReports() {
        System.out.println("[Notice] View Daily Reports is pending integration. Please use '6. View All Reports'.");
    }

    public void viewMonthlyReports() {
        System.out.println("[Notice] View Monthly Reports is pending integration. Please use '6. View All Reports'.");
    }
}