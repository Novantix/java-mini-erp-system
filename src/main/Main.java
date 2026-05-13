package main;

import java.util.Scanner;
import models.User;
import services.AuditService;
import services.EmployeeService;
import services.LoginService;
import services.ReportService;
import services.RoleService;
import services.FileService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LoginService    loginService    = new LoginService();
        RoleService     roleService     = new RoleService();
        AuditService    auditService    = new AuditService();
        EmployeeService employeeService = new EmployeeService();
        ReportService   reportService   = new ReportService(auditService);

        boolean systemRunning = true;

        while (systemRunning) {
            User loginUser = null;

            while (loginUser == null) {
                System.out.println("\n ===== MAIN MENU =====");
                System.out.println("1. User Registration");
                System.out.println("2. Login");
                System.out.println("3. Forgot Password");
                System.out.println("4. Exit");
                System.out.print("Enter Choice : ");

                int choice = sc.nextInt();
                sc.nextLine();

                // USER REGISTRATION
                if (choice == 1) {
                    loginService.registerUser();
                }
                // LOGIN
                else if (choice == 2) {
                    loginUser = loginService.login();

                    // ROLE VALIDATION
                    if (loginUser != null) {
                        // FILE SERVICE
                        FileService fileService = new FileService();

                    fileService.saveData(
                            loginUser.getUsername()
                            + ","
                            + loginUser.getRole());

                    fileService.readData();

                    // ADMIN LOGIN
                        if (roleService.isAdmin(loginUser.getRole())) {
                            System.out.println("\nWelcome Admin");
                            System.out.println("Admin Access Granted");
                        } 
                        // EMPLOYEE LOGIN
                        else if (roleService.isEmployee(loginUser.getRole())) {
                            System.out.println("\nWelcome Employee");
                            System.out.println("Employee Access Granted");
                        }// NORMAL USER
                         else {
                            System.out.println("\nWelcome User");
                        }

                        // LOG LOGIN ACTION
                        auditService.logAction(
                            loginUser.getUsername(),
                            "Logged In as " + loginUser.getRole()
                        );

                        // STOP LOGIN LOOP → go to ERP menu
                        break;
                    }
                }
                // FORGOT PASSWORD
                else if (choice == 3) {
                    loginService.forgotPassword();
                }
                // EXIT
                else if (choice == 4) {
                    System.out.println("Program Closed");
                    systemRunning = false;
                    break;
                }
                else {
                    System.out.println("Invalid Choice");
                }
            }

            if (!systemRunning) {
                break;
            }

            // ERP MASTER MENU
            // Only reached after successful login

            String currentUser = loginUser.getUsername();
            String currentRole = loginUser.getRole();

            int mainChoice;
            do {
                System.out.println("\n========= JAVA MINI ERP SYSTEM =========");
                System.out.println("Logged In As : " + currentUser + " (" + currentRole + ")");
                System.out.println("-----------------------------------------");
                System.out.println("1. Employee Management");
                System.out.println("2. Reports & Audit");
                System.out.println("0. Logout");
                System.out.println("=========================================");
                System.out.print("Enter Choice: ");

                mainChoice = sc.nextInt();
                sc.nextLine();

                switch (mainChoice) {
                    // EMPLOYEE MANAGEMENT
                    case 1:
                        auditService.logAction(currentUser, "Opened Employee Management");
                        employeeService.employeeDashboard();
                        break;

                    // REPORTS & AUDIT
                    case 2:
                        auditService.logAction(currentUser, "Opened Reports & Audit");
                        reportsMenu(sc, reportService, auditService, employeeService, loginUser, currentUser, currentRole);
                        break;

                    // LOGOUT
                    case 0:
                        auditService.logAction(currentUser, "Logged Out");
                        System.out.println("\nLogged out. Bye " + currentUser + "!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } while (mainChoice != 0);
        }

        sc.close();
    }

    // REPORTS & AUDIT MENU
    private static void reportsMenu(
            Scanner sc,
            ReportService reportService,
            AuditService auditService,
            EmployeeService employeeService,
            User loginUser,
            String currentUser,
            String currentRole) {

        int choice;
        do {
            System.out.println("\n====== REPORTS & AUDIT MENU ======");
            System.out.println("1.  Generate Daily Report");
            System.out.println("2.  Generate Monthly Report");
            System.out.println("3.  Generate Attendance Report");
            System.out.println("4.  Generate Sales Report");
            System.out.println("5.  Generate Payroll Report");
            System.out.println("6.  Generate Employee Report");
            System.out.println("7.  View Daily Reports");
            System.out.println("8.  View Monthly Reports");
            System.out.println("9.  View All Audit Logs");
            System.out.println("10. Filter Audit by User");
            System.out.println("11. Filter Audit by Date");
            System.out.println("12. Data Summary");
            System.out.println("13. Export Logs to File");
            System.out.println("14. Clear Audit Logs");
            System.out.println("0.  Back to Main Menu");
            System.out.println("==================================");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    reportService.generateDailyReport(employeeService.getEmployees(), loginUser);
                    break;
                case 2:
                    System.out.print("Enter Month (e.g. June-2025): ");
                    String month = sc.nextLine();
                    reportService.generateMonthlyReport(employeeService.getEmployees(), month, loginUser);
                    break;
                case 3:
                    reportService.generateAttendanceReport(loginUser);
                    break;
                case 4:
                    reportService.generateSalesReport(loginUser);
                    break;
                case 5:
                    reportService.generatePayrollReport(loginUser);
                    break;
                case 6:
                    reportService.generateEmployeeReport(employeeService.getEmployees(), loginUser);
                    break;
                case 7:
                    reportService.viewDailyReports();
                    break;
                case 8:
                    reportService.viewMonthlyReports();
                    break;
                case 9:
                    auditService.viewAllLogs(loginUser);
                    break;
                case 10:
                    System.out.print("Enter Username to filter: ");
                    String user = sc.nextLine();
                    auditService.viewLogsByUser(user, loginUser);
                    break;
                case 11:
                    System.out.print("Enter Date (dd-MM-yyyy): ");
                    String date = sc.nextLine();
                    auditService.viewLogsByDate(date, loginUser);
                    break;
                case 12:
                    reportService.printDataSummary(employeeService.getEmployees(), loginUser);
                    break;
                case 13:
                    auditService.exportLogsToFile(loginUser);
                    break;
                case 14:
                    auditService.clearAllLogs(loginUser);
                    break;
                case 0:
                    System.out.println("Back to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 0);
    }
}