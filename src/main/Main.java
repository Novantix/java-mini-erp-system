package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import models.Attendance.Status;
import models.Payroll;
import models.Purchase;
import models.Supplier;
import models.User;
import services.AdminService;
import services.AttendanceService;
import services.AuditService;
import services.EmployeeService;
import services.InventoryService;
import services.LeaveService;
import services.LoginService;
import services.PayrollService;
import services.PurchaseService;
import services.ReportService;
import services.RoleService;
import services.SalesService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        AuditService auditService = new AuditService();
        ReportService reportService = new ReportService(auditService);
        EmployeeService employeeService = new EmployeeService();
        PayrollService payrollService = new PayrollService();
        InventoryService inventoryService = new InventoryService();
        AttendanceService attendanceService = new AttendanceService();
        LeaveService leaveService = new LeaveService();
        PurchaseService purchaseService = new PurchaseService();
        SalesService salesService = new SalesService();
        AdminService adminService = new AdminService();

        boolean systemRunning = true;

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
            } // ================= LOGIN =================
            else if (choice == 2) {

                User loginUser = loginService.login();

                if (loginUser != null) {

                    String currentUser
                            = loginUser.getUsername();

                    String currentRole
                            = loginUser.getRole();

                    auditService.logAction(
                            currentUser,
                            "User Logged In"
                    );

                    // ================= ROLE ACCESS =================
                    if (roleService.isAdmin(currentRole)) {

                        System.out.println("\nWelcome " + currentUser);
                        System.out.println(currentUser + ", Access Granted");
                        //adminService.adminMenu();

                    } else if (roleService.isEmployee(currentRole)) {

                        System.out.println("\nWelcome Employee " + currentUser);
                        System.out.println("Employee Access Granted");

                    } else {

                        System.out.println("\nWelcome " + currentUser);
                    }

                    int mainChoice;

                    do {

                        // ================= ERP MENU =================
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
                                "2. Payroll Module");

                        System.out.println(
                                "3. Inventory Module");

                        System.out.println(
                                "4. Attendance & Leave Module");

                        System.out.println(
                                "5. Reports & Audit");

                        System.out.println(
                                "6. Supplier & Purchase Management");

                        System.out.println(
                                "7. Sales & Customer Management");

                        if (roleService.isAdmin(currentRole)) {

                            System.out.println(
                                    "8. Admin Panel");
                        }

                        System.out.println(
                                "0. Logout");
                        System.out.print(
                                "Enter Choice: ");

                        mainChoice = sc.nextInt();
                        sc.nextLine();

                        switch (mainChoice) {

                            // ================= LOGOUT =================
                            case 0:

                                auditService.logAction(
                                        currentUser,
                                        "Logged Out"
                                );

                                System.out.println(
                                        "\nLogged out successfully!");

                                break;

                            // ================= EMPLOYEE =================
                            case 1:

                                auditService.logAction(
                                        currentUser,
                                        "Opened Employee Management"
                                );

                                employeeService.employeeDashboard();

                                break;

                            // ================= PAYROLL =================
                            case 2:

                                System.out.println("\n======== PAYROLL MODULE ========");

                                System.out.println("\n======== Generate Payslip ? ========");
                                System.out.println("1. Generate Payslip");
                                System.out.println("0. Back");
                                System.out.print("Enter choice: ");

                                int pChoice = sc.nextInt();
                                sc.nextLine();

                                if (pChoice == 1) {

                                    System.out.print("Enter Employee ID: ");
                                    int employeeId = sc.nextInt();
                                    sc.nextLine();

                                    if (!payrollService.isValidEmployee(employeeId)) {

                                        System.out.println("Invalid id");
                                        break;
                                    }

                                    System.out.print("Enter the Month: ");
                                    String months = sc.next();

                                    System.out.print("Enter the Year:");
                                    int year = sc.nextInt();

                                    System.out.print("Enter Net Salary: ");
                                    double salary = sc.nextDouble();
                                    sc.nextLine();

                                    Payroll payroll = new Payroll(
                                            employeeId,
                                            salary,
                                            months,
                                            year
                                    );

                                    payrollService.generatePayslip(payroll);

                                    auditService.logAction(
                                            currentUser,
                                            "Generated Payroll"
                                    );

                                } else if (pChoice == 0) {

                                    System.out.println("Returning...Please wait");

                                } else {

                                    System.out.println("Please enter a valid choice.");
                                }

                                break;
                            // ================= INVENTORY =================

                            case 3:
                                if (!roleService.isAdmin(currentRole)) {
                                    System.out.println(" Access Denied! Only Admin can access Inventory.");
                                    break;
                                }

                                System.out.println("\n======== INVENTORY MODULE ========");

                                inventoryService.inventoryDashboard();
                                //InventoryService inventoryService = new InventoryService();

                                auditService.logAction(currentUser, "Accessed Inventory Module");

                                break;

                            // REPORTS & AUDIT
                            // ================= ATTENDANCE & LEAVE =================
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

                                    attendanceChoice
                                            = sc.nextInt();

                                    sc.nextLine();

                                    switch (attendanceChoice) {

                                        // ================= MARK ATTENDANCE =================
                                        case 1:

                                            System.out.println(
                                                    "\n===== MARK ATTENDANCE =====");

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int empId
                                                    = sc.nextInt();

                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Employee Name: ");

                                            String empName
                                                    = sc.nextLine();

                                            // AUTO LOGIN TIME
                                            LocalTime loginTime
                                                    = LocalTime.now();

                                            System.out.println(
                                                    "Login Time : "
                                                    + loginTime);

                                            attendanceService.markAttendance(
                                                    empId,
                                                    empName,
                                                    loginTime,
                                                    Status.PRESENT
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Attendance Marked"
                                            );

                                            break;

                                        // ================= APPLY LEAVE =================
                                        case 2:

                                            System.out.println(
                                                    "\n===== APPLY LEAVE =====");

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int leaveEmpId
                                                    = sc.nextInt();

                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Employee Name: ");

                                            String leaveEmpName
                                                    = sc.nextLine();

                                            System.out.print(
                                                    "Enter From Year: ");

                                            int fy
                                                    = sc.nextInt();

                                            System.out.print(
                                                    "Enter From Month: ");

                                            int fm
                                                    = sc.nextInt();

                                            System.out.print(
                                                    "Enter From Day: ");

                                            int fd
                                                    = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Year: ");

                                            int ty
                                                    = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Month: ");

                                            int tm
                                                    = sc.nextInt();

                                            System.out.print(
                                                    "Enter To Day: ");

                                            int td
                                                    = sc.nextInt();

                                            sc.nextLine();

                                            System.out.print(
                                                    "Enter Leave Reason: ");

                                            String reason
                                                    = sc.nextLine();

                                            leaveService.applyLeave(
                                                    leaveEmpId,
                                                    leaveEmpName,
                                                    LocalDate.of(
                                                            fy,
                                                            fm,
                                                            fd
                                                    ),
                                                    LocalDate.of(
                                                            ty,
                                                            tm,
                                                            td
                                                    ),
                                                    reason
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Applied Leave"
                                            );

                                            break;

                                        // ================= APPROVE LEAVE =================
                                        case 3:

                                            System.out.println(
                                                    "\n===== APPROVE LEAVE =====");

                                            System.out.print(
                                                    "Enter Employee ID: ");

                                            int approveId
                                                    = sc.nextInt();

                                            sc.nextLine();

                                            leaveService.approveLeave(
                                                    approveId
                                            );

                                            auditService.logAction(
                                                    currentUser,
                                                    "Approved Leave"
                                            );

                                            break;

                                        // ================= VIEW ATTENDANCE =================
                                        case 4:

                                            attendanceService.showAllAttendance();

                                            break;

                                        // ================= VIEW LEAVE =================
                                        case 5:

                                            leaveService.showAllLeaves();

                                            break;

                                        // ================= BACK =================
                                        case 0:

                                            System.out.println(
                                                    "Returning...");

                                            break;

                                        // ================= INVALID =================
                                        default:

                                            System.out.println(
                                                    "Invalid Choice");
                                    }

                                } while (attendanceChoice != 0);

                                break;

                            // ================= REPORTS =================
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
                                        purchaseService,
                                        loginUser,
                                        currentUser,
                                        currentRole
                                );

                                break;
                            case 6:

                                System.out.println(
                                        "\n===== SUPPLIER & PURCHASE MANAGEMENT =====");

                                int supplierId;

                                while (true) {

                                    System.out.print("Enter Supplier ID : ");

                                    supplierId = sc.nextInt();

                                    if (supplierId > 0) {
                                        break;
                                    }

                                    System.out.println("Supplier ID must be positive!");
                                }

                                sc.nextLine();

                                System.out.print(
                                        "Enter Supplier Name : ");

                                String supplierName
                                        = sc.nextLine();

                                System.out.print(
                                        "Enter Product Name : ");

                                String productName
                                        = sc.nextLine();

                                int stockQuantity;

                                while (true) {

                                    System.out.print(
                                            "Enter Stock Quantity : ");

                                    stockQuantity = sc.nextInt();

                                    if (stockQuantity > 0) {
                                        break;
                                    }

                                    System.out.println(
                                            "Stock must be greater than 0!");
                                }

                                Supplier supplier
                                        = new Supplier(
                                                supplierId,
                                                supplierName,
                                                productName,
                                                stockQuantity
                                        );

                                purchaseService.addSupplier(
                                        supplier);

                                int purchaseId;

                                while (true) {

                                    System.out.print(
                                            "\nEnter Purchase ID : ");

                                    purchaseId = sc.nextInt();

                                    if (purchaseId > 0) {
                                        break;
                                    }

                                    System.out.println(
                                            "Purchase ID must be positive!");
                                }

                                sc.nextLine();

                                System.out.print(
                                        "Enter Purchase Product Name : ");

                                String purchaseProduct
                                        = sc.nextLine();

                                int quantity;

                                while (true) {

                                    System.out.print(
                                            "Enter Quantity : ");

                                    quantity = sc.nextInt();

                                    if (quantity > 0) {
                                        break;
                                    }

                                    System.out.println(
                                            "Quantity must be greater than 0!");
                                }

                                double amount;

                                while (true) {

                                    System.out.print(
                                            "Enter Amount : ");

                                    amount = sc.nextDouble();
                                    sc.nextLine();

                                    if (amount > 0) {
                                        break;
                                    }

                                    System.out.println(
                                            "Amount must be greater than 0!");
                                }

                                Purchase purchase
                                        = new Purchase(
                                                purchaseId,
                                                purchaseProduct,
                                                quantity,
                                                amount
                                        );

                                purchaseService.addPurchase(
                                        purchase);

                                System.out.println(
                                        "\n===== SUPPLIER DETAILS =====");

                                purchaseService.viewSuppliers();

                                System.out.println(
                                        "\n===== PURCHASE DETAILS =====");

                                purchaseService.viewPurchases();

                                System.out.println(
                                        "\n===== STOCK CHECK =====");

                                purchaseService.checkStockAvailability(
                                        purchaseProduct);

                                break;
                            case 7:
                                int salesChoice;
                                do {
                                    System.out.println("\n===== SALES & CUSTOMER MANAGEMENT =====");
                                    System.out.println("1. Add Customer");
                                    System.out.println("2. View Customers");
                                    System.out.println("3. Add Sale");
                                    System.out.println("4. View Sales");
                                    System.out.println("5. Generate Invoice");
                                    System.out.println("0. Back");
                                    System.out.print("Enter Choice : ");
                                    salesChoice = sc.nextInt();
                                    sc.nextLine();

                                    switch (salesChoice) {

                                        case 1:

                                            salesService.addCustomer(sc);

                                            break;

                                        case 2:

                                            salesService.viewCustomers();

                                            break;

                                        case 3:

                                            salesService.addSale(sc);

                                            break;

                                        case 4:

                                            salesService.viewSales();

                                            break;

                                        case 5:

                                            salesService.generateInvoice();

                                            break;

                                        case 0:

                                            System.out.println(
                                                    "Returning to Main Menu...");

                                            break;

                                        default:

                                            System.out.println(
                                                    "Invalid Choice!");
                                    }

                                } while (salesChoice != 0);

                                break;
                            case 8:

                                if (roleService.isAdmin(currentRole)) {

                                    adminService.adminMenu();

                                } else {

                                    System.out.println(
                                            "Access Denied!");
                                }

                                break;

                            // ================= INVALID =================
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
    // ================= REPORT MENU =================

    private static void reportsMenu(
            Scanner sc,
            ReportService reportService,
            AuditService auditService,
            EmployeeService employeeService,
            PurchaseService purchaseService,
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
                    "0. Back");

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
                            "Enter Month: ");

                    String month
                            = sc.nextLine();

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

                    System.out.println("Enter Supplier ID : ");
                    int supplierId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Product Name : ");
                    String productName = sc.nextLine();

                    reportService.generateSalesReport(
                            loginUser,
                            supplierId,
                            productName);

                    break;

                case 5:

                    java.util.List<Payroll> payrollList = new java.util.ArrayList<>();
                    try {
                        java.io.File file = new java.io.File("data/payrollservicedata.txt");
                        if (file.exists()) {
                            java.util.Scanner fileScanner = new java.util.Scanner(file);
                            int empId = 0;
                            double basicSalary = 0;
                            while (fileScanner.hasNextLine()) {
                                String line = fileScanner.nextLine();
                                if (line.startsWith("Employee ID:")) {
                                    empId = Integer.parseInt(line.substring(12).trim());
                                } else if (line.startsWith("Basic Salary:")) {
                                    basicSalary = Double.parseDouble(line.substring(13).trim());
                                } else if (line.startsWith("-----------------------")) {
                                    System.out.print("Enter Month: ");
                                    String months = sc.nextLine();

                                    System.out.print("Enter Year: ");
                                    int year = sc.nextInt();
                                    sc.nextLine(); // Consume the newline character

                                    payrollList.add(new Payroll(empId, basicSalary, months, year));
                                }
                            }
                            fileScanner.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Error reading payroll data: " + e.getMessage());
                    }
                    reportService.generatePayrollReport(payrollList, loginUser);

                    break;

                case 6:

                    System.out.print("Enter Employee ID : ");
                    int searchId = sc.nextInt();
                    sc.nextLine();

                    java.util.List<models.Employee> employeeList
                            = new java.util.ArrayList<>();

                    try {

                        java.io.File empFile
                                = new java.io.File("data/employees.txt");

                        if (empFile.exists()) {

                            java.util.Scanner empScanner
                                    = new java.util.Scanner(empFile);

                            int empId = 0;
                            int exp = 0;

                            String empName = "";
                            String dept = "";
                            String desig = "";
                            String mgr = "";
                            String promo = "";

                            double salary = 0;

                            while (empScanner.hasNextLine()) {

                                String line = empScanner.nextLine();

                                if (line.startsWith("Employee ID")) {

                                    empId = Integer.parseInt(
                                            line.split(":", 2)[1].trim());

                                } else if (line.startsWith("Employee Name")) {

                                    empName
                                            = line.split(":", 2)[1].trim();

                                } else if (line.startsWith("Department")) {

                                    dept
                                            = line.split(":", 2)[1].trim();

                                } else if (line.startsWith("Designation")) {

                                    desig
                                            = line.split(":", 2)[1].trim();

                                } else if (line.startsWith("Salary")) {

                                    salary = Double.parseDouble(
                                            line.split(":", 2)[1].trim());

                                } else if (line.startsWith("Manager Name")) {

                                    mgr
                                            = line.split(":", 2)[1].trim();

                                } else if (line.startsWith("Experience")) {

                                    exp = Integer.parseInt(
                                            line.split(":", 2)[1]
                                                    .replaceAll("[^0-9]", "")
                                                    .trim());

                                } else if (line.startsWith("Promotion Status")) {

                                    promo
                                            = line.split(":", 2)[1].trim();

                                } else if (line.startsWith("----------------------------------------")) {

                                    employeeList.add(
                                            new models.Employee(
                                                    empId,
                                                    empName,
                                                    dept,
                                                    desig,
                                                    salary,
                                                    mgr,
                                                    exp,
                                                    promo
                                            )
                                    );
                                }
                            }

                            empScanner.close();
                        }

                    } catch (Exception e) {

                        System.out.println(
                                "Error reading employee data: "
                                + e.getMessage());
                    }

                    reportService.generateEmployeeReport(
                            employeeList,
                            loginUser,
                            searchId
                    );

                    break;

                case 7:

                    reportService.viewDailyReports(loginUser);

                    break;

                case 8:

                    reportService.viewMonthlyReports(loginUser);

                    break;

                case 9:

                    auditService.viewAllLogs(
                            loginUser
                    );

                    break;

                case 0:

                    System.out.println(
                            "Back to Main Menu...");

                    break;

                default:

                    System.out.println(
                            "Invalid Choice");
            }

        } while (choice != 0);
    }
}
