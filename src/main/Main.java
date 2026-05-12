package main;
import services.EmployeeService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Java Mini ERP System");
        EmployeeService employeeService = new EmployeeService();
        employeeService.employeeDashboard();
        
    }
}
    