package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import models.Attendance.Status;
import models.Payroll;
import models.Product;
import models.User;

import services.AttendanceService;
import services.AuditService;
import services.EmployeeService;
import services.InventoryService;
import services.LeaveService;
import services.LoginService;
import services.PayrollService;
import services.ReportService;
import services.RoleService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        AuditService auditService = new AuditService();
        ReportService reportService = new ReportService(auditService);
        EmployeeService employeeService = new EmployeeService();

        // Existing Services
        PayrollService payrollService = new PayrollService();
        InventoryService inventoryService = new InventoryService();

        // New Attendance & Leave Services
        AttendanceService attendanceService = new AttendanceService();
        LeaveService leaveService = new LeaveService();

        boolean systemRunning = true;

        User loginUser = null;

        while (systemRunning) {

            // ================= MAIN MENU =================

            System.out.println("\n===== MAIN MENU =====");

            System.out.println("1. User Registration");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            // ================= REGISTER =================

            if (choice == 1) {

                loginService.registerUser();

            }

            // ================= LOGIN =================

            else if (choice == 2) {

                loginUser = loginService.login();

                if (loginUser != null) {

                    String currentUser = loginUser.getUsername();
                    String currentRole = loginUser.getRole();

                    auditService.logAction(
                            currentUser,
                            "User Logged In"
                    );

                    // ================= ROLE MESSAGE =================

                    if (roleService.isAdmin(currentRole)) {

                        System.out.println("\nWelcome Admin");
                        System.out.println("Admin Access Granted");

                    }

                    else if (roleService.isEmployee(currentRole)) {

                        System.out.println("\nWelcome Employee");
                        System.out.println("Employee Access Granted");

                    }

                    else {

                        System.out.println("\nWelcome User");
                    }

                    // ================= ERP MENU =================

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

                        System.out.println("1. Employee Management");
                        System.out.println("2. Payroll Module");
                        System.out.println("3. Inventory Module");
                        System.out.println("4. Attendance & Leave Module");
                        System.out.println("5. Reports & Audit");
                        System.out.println("0. Logout");

                        System.out.println(
                                "=========================================");

                        System.out.print("Enter Choice: ");

                        mainChoice = sc.nextInt();
                        sc.nextLine();

                        switch (mainChoice) {

                            // ================= EMPLOYEE MANAGEMENT =================

                            case 1:

                                auditService.logAction(
                                        currentUser,
                                        "Opened Employee Management"
                                );

                                employeeService.employeeDashboard();

                                break;

                            // ================= PAYROLL MODULE =================

                            case 2:

                                System.out.println(
                                        "\n======== PAYROLL MODULE ========");

                                System.out.print("Enter Employee ID: ");

                                int employeeId = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter Year and Month: ");

                                String yrmonth = sc.nextLine();

                                System.out.print("Enter Net Salary: ");

                                double salary = sc.nextDouble();
                                sc.nextLine();

                                Payroll payroll = new Payroll(
                                        employeeId,
                                        salary,
                                        yrmonth
                                );

                                payrollService.generatePayslip(payroll);

                                auditService.logAction(
                                        currentUser,
                                        "Generated Payroll"
                                );

                                break;

                            // ================= INVENTORY MODULE =================

                            case 3:

                                System.out.println(
                                        "\n======== INVENTORY MODULE ========");

                                System.out.print("Enter Product ID: ");

                                int productId = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Enter Product Name: ");

                                String productName = sc.nextLine();

                                System.out.print("Enter Product Stock: ");

                                int stock = sc.nextInt();
                                sc.nextLine();

                                Product product = new Product(
                                        productId,
                                        productName,
                                        stock
                                );

                                inventoryService.addProduct(product);

                                auditService.logAction(
                                        currentUser,
                                        "Added Product to Inventory"
                                );

                                break;

                            // ================= ATTENDANCE & LEAVE MODULE =================

                            case 4:

                                int attendanceChoice;

                                do {

                                    System.out.println(
                                            "\n===== ATTENDANCE & LEAVE MODULE =====");

                                    System.out.println(
                                            "1. Mark Attendance");

                                    System.out.println(
                                            "2. Apply Leave");

                                    System.out.println(
                                            "3. Approve Leave");

                                    System.out.println(
                                            "4. View Attendance Report");

                                    System.out.println(
                                            "5. View Leave Report");

                                    System.out.println(
                                            "0. Back");

                                    System.out.print(
                                            "Enter Choice: ");

                                    attendanceChoice = sc.nextInt();
                                    sc.nextLine();

                                    switch (attendanceChoice) {

                                        // ================= MARK ATTENDANCE =================

                                        case 1:

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int empId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Employee Name: ");

                                            String empName = sc.nextLine();

                                            System.out.print(
                                                    "Enter Check-In Hour: ");

                                            int hour = sc.nextInt();

                                            System.out.print(
                                                    "Enter Check-In Minute: ");

                                            int minute = sc.nextInt();
                                            sc.nextLine();

                                            System.out.println(
                                                    "Select Status:");

                                            System.out.println(
                                                    "1. PRESENT");

                                            System.out.println(
                                                    "2. ABSENT");

                                            System.out.println(
                                                    "3. HALF_DAY");

                                            System.out.println(
                                                    "4. OD");

                                            int statusChoice = sc.nextInt();
                                            sc.nextLine();

                                            Status status = Status.PRESENT;

                                            switch (statusChoice) {

                                                case 1:
                                                    status = Status.PRESENT;
                                                    break;

                                                case 2:
                                                    status = Status.ABSENT;
                                                    break;

                                                case 3:
                                                    status = Status.HALF_DAY;
                                                    break;

                                                case 4:
                                                    status = Status.OD;
                                                    break;

                                                default:
                                                    System.out.println(
                                                            "Invalid Status");
                                            }

                                            attendanceService.markAttendance(
                                                    empId,
                                                    empName,
                                                    LocalTime.of(hour, minute),
                                                    status
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Marked Attendance"
                                            );

                                            break;

                                        // ================= APPLY LEAVE =================

                                        case 2:

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int leaveEmpId = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Employee Name: ");

                                            String leaveEmpName = sc.nextLine();

                                            System.out.print(
                                                    "Enter From Year: ");

                                            int fy = sc.nextInt();

                                            System.out.print(
                                                    "Enter From Month: ");

                                            int fm = sc.nextInt();

                                            System.out.print(
                                                    "Enter From Day: ");

                                            int fd = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Year: ");

                                            int ty = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Month: ");

                                            int tm = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Day: ");

                                            int td = sc.nextInt();
                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Leave Reason: ");

                                            String reason = sc.nextLine();

                                            leaveService.applyLeave(
                                                    leaveEmpId,
                                                    leaveEmpName,
                                                    LocalDate.of(fy, fm, fd),
                                                    LocalDate.of(ty, tm, td),
                                                    reason
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Applied Leave"
                                            );

                                            break;

                                        // ================= APPROVE LEAVE =================

                                        case 3:

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int approveId = sc.nextInt();
                                            sc.nextLine();

                                            leaveService.approveLeave(
                                                    approveId
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Approved Leave"
                                            );

                                            break;

                                        // ================= ATTENDANCE REPORT =================

                                        case 4:

                                            attendanceService.showAllAttendance();

                                            break;

                                        // ================= LEAVE REPORT =================

                                        case 5:

                                            leaveService.showAllLeaves();

                                            break;

                                        // ================= BACK =================

                                        case 0:

                                            System.out.println(
                                                    "Returning...");

                                            break;

                                        default:

                                            System.out.println(
                                                    "Invalid Choice");
                                    }

                                } while (attendanceChoice != 0);

                                break;

                            // ================= REPORTS & AUDIT =================

                            case 5:

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

                            // ================= LOGOUT =================

                            case 0:

                                auditService.logAction(
                                        currentUser,
                                        "Logged Out"
                                );

                                System.out.println(
                                        "\nLogged out successfully!");

                                break;

                            // ================= INVALID =================

                            default:

                                System.out.println(
                                        "Invalid choice! Try again.");
                        }

                    } while (mainChoice != 0);

                }

                else {

                    System.out.println(
                            "\nInvalid Username or Password");
                }
            }

            // ================= FORGOT PASSWORD =================

            else if (choice == 3) {

                loginService.forgotPassword();

            }

            // ================= EXIT =================

            else if (choice == 4) {

                systemRunning = false;

                System.out.println(
                        "\nExiting ERP System...");

                System.out.println(
                        "Thank You!");
            }

            // ================= INVALID =================

            else {

                System.out.println(
                        "\nInvalid Choice");
            }
        }

        sc.close();
    }

    // ================= REPORTS MENU =================

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
                    "1. Generate Daily Report");

            System.out.println(
                    "2. Generate Monthly Report");

            System.out.println(
                    "3. Generate Attendance Report");

            System.out.println(
                    "4. Generate Sales Report");

            System.out.println(
                    "5. Generate Payroll Report");

            System.out.println(
                    "6. Generate Employee Report");

            System.out.println(
                    "7. View Daily Reports");

            System.out.println(
                    "8. View Monthly Reports");

            System.out.println(
                    "9. View All Audit Logs");

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
                    "0. Back to Main Menu");

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