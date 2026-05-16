package main;

import java.util.Scanner;
import models.Payroll;
import models.Purchase;
import models.Supplier;
import models.User;
import services.AuditService;
import services.EmployeeService;
import services.InventoryService;
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
        PurchaseService purchaseService = new PurchaseService();
        PayrollService payrollService = new PayrollService();
        InventoryService inventoryService = new InventoryService();
        SalesService salesService = new SalesService();

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

                        System.out.println("\nWelcome "+ currentUser);
                        System.out.println(currentUser + ", Access Granted");

                    } else if (roleService.isEmployee(currentRole)) {

                        System.out.println("\nWelcome Employee" + currentUser);
                        System.out.println("Employee Access Granted");

                    } else {

                        System.out.println("\nWelcome " + currentUser);
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


                        System.out.println("2. Payroll Module");
                        System.out.println("3. Inventory Module");


                        System.out.println(
                                "4. Reports & Audit");

                        System.out.println(
                                "5. Supplier & Purchase Management");
                        System.out.println( 
                                "6. Sales & Customer Management");

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

                                System.err.print("Enter the Year:");
                                int year  = sc.nextInt();
                                

                                System.out.print("Enter Net Salary: ");
                                double salary = sc.nextDouble();
                                sc.nextLine();

                                // Payroll Object
                                Payroll payroll = new Payroll(
                                        employeeId,
                                        salary,
                                        months,
                                        year
                                );

                                // Generate Payslip
                                payrollService.generatePayslip(payroll);

                                auditService.logAction(
                                        currentUser,
                                        "Generated Payroll"
                                );

                                break;
                                } else if (pChoice == 0) {
                                        System.out.println("Returning...Please wait");
                                } else {
                                        System.out.println("Please enter a valid choice.");
                        }

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
                        case 4:

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
                                        payrollService,
                                        loginUser,
                                        currentUser,
                                        currentRole
                                );

                                break;
                            case 5:

                                System.out.println(
                                        "\n===== SUPPLIER & PURCHASE MANAGEMENT =====");

                                int supplierId;

                                while (true) {

                                    System.out.print( "Enter Supplier ID : ");

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
                        
                                case 6:
                                        int salesChoice;
                                         do {
                                                 System.out.println( "\n===== SALES & CUSTOMER MANAGEMENT =====");
                                                  System.out.println( "1. Add Customer");
                                                  System.out.println( "2. View Customers");
                                                   System.out.println( "3. Add Sale");
                                                   System.out.println("4. View Sales");
                                                   System.out.println("5. Generate Invoice");
                                                   System.out.println( "0. Back");
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
            PurchaseService purchaseService,
            PayrollService payrollService,
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

    System.out.print("Enter Employee ID : ");
    int searchEmpId = sc.nextInt();
    sc.nextLine();

    // Validate Employee ID
    if (!payrollService.isValidEmployee(searchEmpId)) {

        System.out.println(
                "Employee ID not found.");
        break;
    }

    // Month Input
    System.out.print("Enter Month : ");
    String months = sc.nextLine();

    // Valid Months
    java.util.List<String> validMonths =
            java.util.Arrays.asList(
                    "january", "february",
                    "march", "april",
                    "may", "june",
                    "july", "august",
                    "september", "october",
                    "november", "december"
            );

    // Month Validation
    if (!validMonths.contains(
            months.toLowerCase())) {

        System.out.println(
                "Invalid Month!");

        break;
    }

    // Year Input
    System.out.print("Enter Year : ");
    int year = sc.nextInt();
    sc.nextLine();

    // Year Validation
    if (year < 2000) {

        System.out.println(
                "Invalid Year!");

        break;
    }

    java.util.List<Payroll> payrollList =
            new java.util.ArrayList<>();

    boolean found = false;

    try {

        java.io.File file =
                new java.io.File(
                        "data/payrollservicedata.txt");

        if (file.exists()) {

            java.util.Scanner fileScanner =
                    new java.util.Scanner(file);

            int empId = 0;
            double basicSalary = 0;

            while (fileScanner.hasNextLine()) {

                String line =
                        fileScanner.nextLine();

                if (line.startsWith(
                        "Employee ID:")) {

                    empId = Integer.parseInt(
                            line.substring(12).trim());

                } else if (line.startsWith(
                        "Basic Salary:")) {

                    basicSalary =
                            Double.parseDouble(
                                    line.substring(13)
                                            .trim());

                } else if (line.startsWith(
                        "-----------------------")) {

                    if (empId == searchEmpId) {

                        payrollList.add(
                                new Payroll(
                                        empId,
                                        basicSalary,
                                        months,
                                        year
                                )
                        );

                        found = true;
                        break;
                    }
                }
            }

            fileScanner.close();
        }

        if (!found) {

            System.out.println(
                    "Payroll data not found.");

            break;
        }

    } catch (Exception e) {

        System.out.println(
                "Error reading payroll data: "
                        + e.getMessage());
    }

    reportService.generatePayrollReport(
            payrollList,
            loginUser
    );

    break;
                case 6:

    System.out.print("Enter Employee ID : ");
    int searchId = sc.nextInt();
    sc.nextLine();

    java.util.List<models.Employee> employeeList =
            new java.util.ArrayList<>();

    try {

        java.io.File empFile =
                new java.io.File("data/employees.txt");

        if (empFile.exists()) {

            java.util.Scanner empScanner =
                    new java.util.Scanner(empFile);

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

                    empName =
                            line.split(":", 2)[1].trim();

                } else if (line.startsWith("Department")) {

                    dept =
                            line.split(":", 2)[1].trim();

                } else if (line.startsWith("Designation")) {

                    desig =
                            line.split(":", 2)[1].trim();

                } else if (line.startsWith("Salary")) {

                    salary = Double.parseDouble(
                            line.split(":", 2)[1].trim());

                } else if (line.startsWith("Manager Name")) {

                    mgr =
                            line.split(":", 2)[1].trim();

                } else if (line.startsWith("Experience")) {

                    exp = Integer.parseInt(
                            line.split(":", 2)[1]
                                    .replaceAll("[^0-9]", "")
                                    .trim());

                } else if (line.startsWith("Promotion Status")) {

                    promo =
                            line.split(":", 2)[1].trim();

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
