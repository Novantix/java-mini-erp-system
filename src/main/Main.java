package main;
import java.util.Scanner;
import models.Purchase;
import models.Supplier;
import models.User;
import services.AuditService;
import services.EmployeeService;
import services.LoginService;
import services.PurchaseService;
import services.ReportService;
import services.RoleService;
import models.Purchase;
import models.Supplier;
import services.PurchaseService;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        AuditService auditService = new AuditService();
        ReportService reportService = new ReportService(auditService);
        EmployeeService employeeService = new EmployeeService();
        PurchaseService purchaseService = new PurchaseService();

        boolean systemRunning = true;

        User loginUser = null;

        while (systemRunning) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. User Registration");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Logout");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                loginService.registerUser();

            } else if (choice == 2) {

                loginUser = loginService.login();

                if (loginUser != null) {

                    String currentUser = loginUser.getUsername();
                    String currentRole = loginUser.getRole();

                    auditService.logAction(
                            currentUser,
                            "User Logged In"
                    );

                    if (roleService.isAdmin(currentRole)) {

                        System.out.println("\nWelcome Admin");
                        System.out.println("Admin Access Granted");

                    } else if (roleService.isEmployee(currentRole)) {

                        System.out.println("\nWelcome Employee");
                        System.out.println("Employee Access Granted");

                    } else {

                        System.out.println("\nWelcome User");
                    }

                    int mainChoice;

                    do {

                        System.out.println(
                                "\n========= JAVA MINI ERP SYSTEM =========");

                        System.out.println(
                                "Logged In As : "
                                + currentUser
                                + " ("
                                + currentRole
                                + ")");

                        System.out.println(
                                "-----------------------------------------");

                        System.out.println(
                                "1. Employee Management");

                        System.out.println(
                                "2. Reports & Audit");

                        System.out.println(
                                "3. Supplier & Purchase Management");

                        System.out.println(
                                "0. Logout");

                        System.out.println(
                                "=========================================");

                        System.out.print(
                                "Enter Choice: ");

                        mainChoice = sc.nextInt();
                        sc.nextLine();

                        switch (mainChoice) {

                            case 1:

                                auditService.logAction(
                                        currentUser,
                                        "Opened Employee Management"
                                );

                                employeeService.employeeDashboard();

                                break;

                            case 2:

                                auditService.logAction(
                                        currentUser,
                                        "Opened Reports & Audit"
                                );

                                reportsMenu(
                                        sc,
                                        reportService,
                                        auditService,
                                        employeeService,
                                        loginUser,
                                        currentUser,
                                        currentRole
                                );

                                break;
                            case 3:

                                Supplier supplier1
                                        = new Supplier(1, "Ramesh", "Laptop", 50);

                                Supplier supplier2
                                        = new Supplier(2, "Suresh", "Mouse", 100);

                                purchaseService.addSupplier(supplier1);
                                purchaseService.addSupplier(supplier2);

                                Purchase purchase1
                                        = new Purchase(101, "Laptop", 2, 90000);

                                purchaseService.addPurchase(purchase1);

                                System.out.println(
                                        "\n===== SUPPLIER DETAILS =====");

                                purchaseService.viewSuppliers();

                                System.out.println(
                                        "\n===== PURCHASE DETAILS =====");

                                purchaseService.viewPurchases();

                                System.out.println(
                                        "\n===== STOCK CHECK =====");

                                purchaseService.checkStockAvailability(
                                        "Laptop");

                                break;

                            case 0:

                                auditService.logAction(
                                        currentUser,
                                        "Logged Out"
                                );

                                System.out.println(
                                        "\nLogged out successfully!");

                                break;

                            default:

                                System.out.println(
                                        "Invalid choice! Try again.");
                        }

                    } while (mainChoice != 0);

                } else {

                    System.out.println(
                            "\nInvalid Username or Password");
                }

            } else if (choice == 3) {

                loginService.forgotPassword();

            } else if (choice == 4) {

                systemRunning = false;

                System.out.println(
                        "\nExiting ERP System...");
                System.out.println(
                        "Thank You!");

            } else {

                System.out.println(
                        "\nInvalid Choice");
            }
        }

        sc.close();
    }

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

            System.out.println(
                    "\n====== REPORTS & AUDIT MENU ======");

            System.out.println(
                    "1.  Generate Daily Report");

            System.out.println(
                    "2.  Generate Monthly Report");

            System.out.println(
                    "3.  Generate Attendance Report");

            System.out.println(
                    "4.  Generate Sales Report");

            System.out.println(
                    "5.  Generate Payroll Report");

            System.out.println(
                    "6.  Generate Employee Report");

            System.out.println(
                    "7.  View Daily Reports");

            System.out.println(
                    "8.  View Monthly Reports");

            System.out.println(
                    "9.  View All Audit Logs");

            System.out.println(
                    "10. Filter Audit by User");

            System.out.println(
                    "11. Filter Audit by Date");

            System.out.println(
                    "12. Data Summary");

            System.out.println(
                    "13. Export Logs to File");

            System.out.println(
                    "14. Clear Audit Logs");

            System.out.println(
                    "0.  Back to Main Menu");

            System.out.println(
                    "==================================");

            System.out.print(
                    "Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    reportService.generateDailyReport(
                            employeeService.getEmployees(),
                            loginUser
                    );

                    break;

                case 2:

                    System.out.print(
                            "Enter Month (e.g. June-2025): ");

                    String month = sc.nextLine();

                    reportService.generateMonthlyReport(
                            employeeService.getEmployees(),
                            month,
                            loginUser
                    );

                    break;

                case 3:

                    reportService.generateAttendanceReport(
                            loginUser
                    );

                    break;

                case 4:

                    reportService.generateSalesReport(
                            loginUser
                    );

                    break;

                case 5:

                    reportService.generatePayrollReport(
                            loginUser
                    );

                    break;

                case 6:

                    reportService.generateEmployeeReport(
                            employeeService.getEmployees(),
                            loginUser
                    );

                    break;

                case 7:

                    reportService.viewDailyReports();

                    break;

                case 8:

                    reportService.viewMonthlyReports();

                    break;

                case 9:

                    auditService.viewAllLogs(
                            loginUser
                    );

                    break;

                case 10:

                    System.out.print(
                            "Enter Username to filter: ");

                    String user = sc.nextLine();

                    auditService.viewLogsByUser(
                            user,
                            loginUser
                    );

                    break;

                case 11:

                    System.out.print(
                            "Enter Date (dd-MM-yyyy): ");

                    String date = sc.nextLine();

                    auditService.viewLogsByDate(
                            date,
                            loginUser
                    );

                    break;

                case 12:

                    reportService.printDataSummary(
                            employeeService.getEmployees(),
                            loginUser
                    );

                    break;

                case 13:

                    auditService.exportLogsToFile(
                            loginUser
                    );

                    break;

                case 14:

                    auditService.clearAllLogs(
                            loginUser
                    );

                    break;

                case 0:

                    System.out.println(
                            "Back to Main Menu...");

                    break;

                default:

                    System.out.println(
                            "Invalid choice! Try again.");
            }

        } while (choice != 0);
    }
}
