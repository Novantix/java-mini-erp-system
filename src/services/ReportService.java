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
import data.DataStore;
import models.Attendance;
import models.Leave;
import models.Report;
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



    // Generate Employee Report  →  ADMIN, HR, MANAGER



public void generateEmployeeReport(List<Employee> employees, User loggedInUser , int searchId) {

    try {
        if (!isLoggedIn(loggedInUser)) return;
        if (!hasRole(loggedInUser, "ADMIN", "MANAGER", "HR")) return;
        if (employees == null || employees.isEmpty()) {
            System.out.println("[Report] No employee data available.");
            return;
        }
        Employee foundEmployee = null;
        for (Employee emp : employees) {

            if (emp.getEmployeeId() == searchId) {
                foundEmployee = emp;
                break;
            }
        }
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

    // Generate Daily Report       -> ADMIN, HR, MANAGER, EMPLOYEE

    public void generateDailyReport(List<Employee> employees, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            String today = LocalDate.now().format(DATE_FORMATTER);
            StringBuilder content = new StringBuilder();
            content.append("=========== DAILY REPORT ===========\n");
            content.append("Report Date : ").append(today)
                        .append("\n\n");
            int empCount = 0;

try {
java.io.File file = new java.io.File("data/employees.txt");
    if (file.exists()) {
        java.util.Scanner scanner = new java.util.Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Employee ID")) {
                empCount++;
            }
        }
        scanner.close();
    }
} catch (Exception e) {
    System.out.println(
            "[Report Error] "
            + e.getMessage());
}
int attendanceCount = data.DataStore.attendanceList.size();
int leaveCount =data.DataStore.leaveList.size();
int auditCount =auditService.getTotalLogCount();

content.append("Employees on Record : ")
       .append(empCount)
       .append("\n");

content.append("Attendance Records  : ")
       .append(attendanceCount)
       .append("\n");

content.append("Leave Requests      : ")
       .append(leaveCount)
       .append("\n");

content.append("Audit Log Entries   : ")
       .append(auditCount)
       .append("\n");

content.append("\n====================================");

            Report report = new Report(reportIdCounter++, "DAILY", loggedInUser.getUsername(), content.toString());
            reportStore.add(report);
            System.out.println(report);
    try {

    java.io.File dir = new java.io.File("data");

    if (!dir.exists()) {
        dir.mkdirs();
    }

    java.io.FileWriter fw = new java.io.FileWriter( "data/daily_report.txt", true);

    fw.write(report.toString());
    fw.write("\n\n");
    fw.close();
} catch (Exception e) {
    System.out.println(
            "[Daily Report Save Error] "
                    + e.getMessage());
}
    auditService.logAction(loggedInUser.getUsername(), "Generated Daily Report for " + today + " [ID: " + report.getReportId() + "]");
        } catch (Exception e) {
            System.out.println("[ReportService Error] generateDailyReport : " + e.getMessage());
        }
    }


    // Generate Monthly Report     -> ADMIN, MANAGER
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

            int empCount = 0;
            double totalSalary = 0;
try {

    java.io.File file = new java.io.File("data/employees.txt");

    if (file.exists()) {
        java.util.Scanner scanner =new java.util.Scanner(file);
        while (scanner.hasNextLine()) {

            String line =scanner.nextLine();
            if (line.startsWith("Employee ID")) {
                empCount++;
            }
            else if (line.startsWith("Salary")) {
                double salary = Double.parseDouble(line.split(":", 2)[1].trim());
                totalSalary += salary;
            }
        }
        scanner.close();
    }

} catch (Exception e) {
    System.out.println("[Monthly Report Error] "  + e.getMessage());
}

content.append("Total Employees     : ")
       .append(empCount)
       .append("\n");

content.append("Total Salary Paid   : Rs. ")
       .append(String.format("%.2f", totalSalary))
       .append("\n");

content.append("Total Audit Logs    : ")
       .append(auditService.getTotalLogCount())
       .append("\n");

content.append("Attendance Records  : ")
       .append(data.DataStore.attendanceList.size())
       .append("\n");

content.append("Leave Requests      : ")
       .append(data.DataStore.leaveList.size())
       .append("\n");
        Report report = new Report(reportIdCounter++, "MONTHLY", loggedInUser.getUsername(), content.toString());
        reportStore.add(report);
        try {

    java.io.File dir = new java.io.File("data");
    if (!dir.exists()) {
        dir.mkdirs();
    }
    java.io.FileWriter fw = new java.io.FileWriter( "data/monthly_report.txt",true);
    fw.write(report.toString());
    fw.write("\n\n");
    fw.close();

} catch (Exception e) {
    System.out.println("[Monthly Report Save Error] " + e.getMessage());
}
            System.out.println(report);
            auditService.logAction(loggedInUser.getUsername(), "Generated Monthly Report for " + month + " [ID: " + report.getReportId() + "]");
        } catch (Exception e) {
            System.out.println("[ReportService Error] generateMonthlyReport : " + e.getMessage());
        }
    }

    // View All Reports  →  ADMIN 
    public void viewAllReports(User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            if (!hasRole(loggedInUser, "ADMIN", "MANAGER" ,"HR")) return;

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

    //  Export Report to File  →  ADMIN only

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

    //Data Summary -> ADMIN, HR, MANAGER
    public void printDataSummary(List<Employee> employees, User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) return;
            int employeeCount = 0;
        double totalSalary = 0;

        try {
            java.io.File file = new java.io.File( "data/employees.txt");

            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                while (scanner.hasNextLine()) {
                    String line =  scanner.nextLine();

                    if (line.startsWith("Employee ID")) {
                        employeeCount++;
                    }

                    else if (line.startsWith("Salary")) {
                        double salary =Double.parseDouble(line.split(":", 2)[1]
                                                .trim());
                        totalSalary += salary;
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println(
                    "[Summary Error] "
                            + e.getMessage());
        }
            System.out.println("\n========== DATA SUMMARY ==========");
            System.out.println("Logged in as  : "
                + loggedInUser.getUsername()
                + " ["
                + loggedInUser.getRole()
                + "]");
            System.out.println("----------------------------------");
try {

    java.io.File file = new java.io.File( "data/employees.txt");
    if (file.exists()) {

        java.util.Scanner scanner = new java.util.Scanner(file);
        while (scanner.hasNextLine()) {
            String line =  scanner.nextLine();
            if (line.startsWith("Employee ID")) {

                employeeCount++;
            }

            else if (line.startsWith(
                    "Salary")) {

                double salary =Double.parseDouble(line.split(":", 2)[1]
                                        .trim());
                totalSalary += salary;
            }
        }
        scanner.close();
    }
} catch (Exception e) {
    System.out.println("[Summary Error] "+ e.getMessage());
}

System.out.println( "Total Employees  : "+ employeeCount);

System.out.println("Total Salary     : Rs. "+ String.format("%.2f",totalSalary));

System.out.println("Attendance Count : "+ data.DataStore.attendanceList.size());

System.out.println("Leave Requests   : "+ data.DataStore.leaveList.size());

System.out.println("Generated Reports: "+ reportStore.size());

System.out.println("Audit Logs       : "+ auditService.getTotalLogCount());
System.out.println( "==================================");


            auditService.logAction(loggedInUser.getUsername(), "Viewed Data Summary");
        } catch (Exception e) {
            System.out.println("[ReportService Error] printDataSummary : " + e.getMessage());
        }
    }


    // Generate Payroll Report     -> ADMIN, HR
    
    
    public void generatePayrollReport(List<Payroll> payrollList,
                                  User loggedInUser) {

    try {
        if (!isLoggedIn(loggedInUser)) return;
        if (!hasRole(loggedInUser, "ADMIN", "HR")) return;
        if (payrollList == null || payrollList.isEmpty()) {
            System.out.println( "[Payroll Report] No payroll data available.");
            return;
        }
        StringBuilder content = new StringBuilder();
        content.append("=========== PAYROLL REPORT ===========\n\n");
        double totalNetSalary = 0;
        for (Payroll payroll : payrollList) {
            double salary = payroll.getSalary();
            double pf =payrollService.calculatePF(salary);

            double tax =payrollService.calculateTax(salary);

            double bonus =payrollService.calculateBonus(salary);

            double netSalary =salary - pf - tax + bonus;

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
        auditService.logAction(loggedInUser.getUsername(),
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
    public List<Report> getReportStore() { return reportStore; }

    // Generate Attendance Report  -> ADMIN, HR, MANAGER
    public void generateAttendanceReport( User loggedInUser) {
    try {
        if (!isLoggedIn(loggedInUser)) {
            return;
        }
        if (!hasRole(loggedInUser,"ADMIN","MANAGER","HR")) {
            return;
        }
        StringBuilder content = new StringBuilder();
        int presentCount = 0;
        int absentCount = 0;
        int halfDayCount = 0;
        int odCount = 0;

        int approvedLeaves = 0;
        int rejectedLeaves = 0;
        int pendingLeaves = 0;
        content.append("=========== ATTENDANCE REPORT ===========\n\n");
        content.append("----- ATTENDANCE DETAILS -----\n");

        if (data.DataStore.attendanceList.isEmpty()) {
            content.append( "No attendance records found.\n");
        } else {
            for (models.Attendance attendance : data.DataStore.attendanceList) {
                switch (attendance.getStatus()) {

        case PRESENT:
            presentCount++;
            break;

        case ABSENT:
            absentCount++;
            break;

        case HALF_DAY:
            halfDayCount++;
            break;

        case OD:
            odCount++;
            break;
    }
                content.append(attendance)
                        .append("\n");

                content.append( "-----------------------------------\n");
            }
        }

        // Leave Section
        content.append("\n----- LEAVE DETAILS -----\n");
        if (data.DataStore.leaveList.isEmpty()) {
            content.append( "No leave records found.\n");
        } else {
            for (models.Leave leave: data.DataStore.leaveList) {
                if (leave.getStatus()
            .equalsIgnoreCase("APPROVED")) {
        approvedLeaves++;
    }
    else if (leave.getStatus().equalsIgnoreCase("REJECTED")) {
        rejectedLeaves++;
    }
    else {
        pendingLeaves++;
    }
                content.append(leave).append("\n");
                content.append("-----------------------------------\n");
            }
        }
        // Create Report
        Report report = new Report(
                reportIdCounter++,
                "ATTENDANCE",
                loggedInUser.getUsername(),
                content.toString()
        );

        reportStore.add(report);
        System.out.println(report);
        // Save Report To File
        try {

            java.io.File dir =
                    new java.io.File("reports");

            if (!dir.exists()) {

                dir.mkdirs();
            }

            String fileName ="data/Attendance_Report_"+ report.getReportId()+ ".txt";
            java.io.FileWriter fw = new java.io.FileWriter(fileName);
            fw.write(report.toString());
            fw.close();
            System.out.println("\n[Report Saved Successfully]");
            System.out.println("File Location : "+ fileName);

        } catch (Exception e) {

            System.out.println(
                    "[File Export Error] "
                            + e.getMessage());
        }
        // Audit Log
        auditService.logAction(
                loggedInUser.getUsername(),
                "Generated Attendance Report [ID: "
                        + report.getReportId()
                        + "]"
        );

    } catch (Exception e) {

        System.out.println("[Attendance Report Error] "+ e.getMessage());
    }
}
public void generateSalesReport(User loggedInUser,
                                int supplierId,
                                String productName) {

    try {
        if (!isLoggedIn(loggedInUser)) return;
        if (!hasRole(loggedInUser,"ADMIN","MANAGER")) return;
        java.io.File file =new java.io.File("data/suppliers.txt");
        if (!file.exists()) {
            System.out.println("Supplier File Not Found");
            return;
        }
        java.util.Scanner scanner =new java.util.Scanner(file);
        boolean found = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Supplier ID")) {
                int id = Integer.parseInt(
                        line.split(":")[1].trim());

                String supplierName =
                        scanner.nextLine().split(":")[1].trim();

                String fileProduct =
                        scanner.nextLine().split(":")[1].trim();

                int stock =Integer.parseInt(scanner.nextLine().split(":")[1].trim());

                if (id == supplierId  && fileProduct.equalsIgnoreCase(productName)) {

                    String stockStatus;
if (stock <= 5) {
    stockStatus = "LOW STOCK";
} else {
    stockStatus = "AVAILABLE";
}
StringBuilder content =new StringBuilder();
content.append(
        "=========== SALES REPORT ===========\n\n");
content.append(
        "Generated By : ")
       .append(loggedInUser.getUsername())
       .append("\n");
content.append(
        "Supplier ID  : ")
       .append(id)
       .append("\n");

content.append(
        "Supplier Name: ")
       .append(supplierName)
       .append("\n");

content.append(
        "Product Name : ")
       .append(fileProduct)
       .append("\n");

content.append(
        "Stock Quantity : ")
       .append(stock)
       .append("\n");

content.append(
        "Stock Status : ")
       .append(stockStatus)
       .append("\n");

content.append(
        "Audit Entries : ")
       .append(auditService.getTotalLogCount())
       .append("\n");

content.append(
        "\n====================================");

Report report = new Report(reportIdCounter++,"SALES",
        loggedInUser.getUsername(),
        content.toString()
);

reportStore.add(report);
System.out.println(report);


auditService.logAction(
        loggedInUser.getUsername(),
        "Generated Sales Report [ID: "
                + report.getReportId()
                + "]"
);
found = true;
break;
}
}
}

scanner.close();

if (!found) {
System.out.println("Sales Report Not Found");
        }

} catch (Exception e) {
      System.out.println("[ReportService Error] "+ e.getMessage());
    }
}


//View Daily Reports  -> ADMIN, HR, MANAGER


public void viewDailyReports(
        User loggedInUser) {

    try {
        if (!isLoggedIn(loggedInUser)) {
            return;
        }
        if (!hasRole(loggedInUser, "ADMIN" , "MANAGER","HR")) return;
        java.io.File file =new java.io.File("data/daily_report.txt");
        if (!file.exists()) {
            System.out.println("\nNo Daily Reports Found");
            return;
        }
        java.util.Scanner scanner =new java.util.Scanner(file);
        int lineCount = 0;
        System.out.println("\n====================================");
        System.out.println("  DAILY REPORT HISTORY");
        System.out.println("====================================");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            lineCount++;
        }
        scanner.close();
        System.out.println("====================================");
        System.out.println("Total Report Lines : "+ lineCount);
        System.out.println("Viewed By          : "+ loggedInUser.getUsername());
        System.out.println("====================================");

        // AUDIT LOG
        auditService.logAction(
                loggedInUser.getUsername(),
                "Viewed Daily Reports"
        );

    } catch (Exception e) {

        System.out.println(
                "[ReportService Error] "
                        + e.getMessage());
    }
}


// View Monthly Reports    -> ADMIN, MANAGER


public void viewMonthlyReports( User loggedInUser) {
    try {
        if (!isLoggedIn(loggedInUser)) {
            return;
        }
        java.io.File file =new java.io.File("data/monthly_report.txt");
        if (!file.exists()) {
            System.out.println("\nNo Monthly Reports Found");
            return;
        }
        java.util.Scanner scanner =new java.util.Scanner(file);
        int lineCount = 0;
        System.out.println("\n====================================");
        System.out.println("      MONTHLY REPORT HISTORY");
        System.out.println("====================================");
        while (scanner.hasNextLine()) {
            String line =scanner.nextLine();
            System.out.println(line);
            lineCount++;
        }
        scanner.close();
        System.out.println("====================================");
        System.out.println("Total Report Lines : " + lineCount);
        System.out.println("Viewed By          : "+ loggedInUser.getUsername());
        System.out.println( "====================================");

        // AUDIT LOG
        auditService.logAction(
                loggedInUser.getUsername(),
                "Viewed Monthly Reports"
        );

    } catch (Exception e) {
        System.out.println(
                "[ReportService Error] "
                        + e.getMessage());
    }
}
}