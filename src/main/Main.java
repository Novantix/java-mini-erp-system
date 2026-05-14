package main;

import java.util.Scanner;
import models.User;
import services.EmployeeService;
import services.LoginService;
import services.RoleService;

public class Main {

    public static void main(String[] args) {

         Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        User loginUser = null;
        while (true) {
            // MAIN MENU
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. User Registration");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Logout");
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
                    // ADMIN
                    if (roleService.isAdmin(
                            loginUser.getRole()
                    )) {
                        System.out.println("\nWelcome Admin");
                        System.out.println("Admin Access Granted");
                        EmployeeService employeeService = new EmployeeService();
                        employeeService.employeeDashboard();
                    }
                    // EMPLOYEE
                    else if (roleService.isEmployee(
                            loginUser.getRole()
                    )) {
                        System.out.println("\nWelcome Employee");
                        System.out.println("Employee Access Granted");

                        // EMPLOYEE DASHBOARD
                        EmployeeService employeeService = new EmployeeService();
                        employeeService.employeeDashboard();
                        
                    }
                    
                    // OTHER ROLE
                    else {
                        System.out.println("\nWelcome User");
                        
                    }
                }
            }
            // ================= FORGOT PASSWORD =================
            else if (choice == 3) {
                loginService.forgotPassword();
            }
            // ================= LOGOUT =================
            else if (choice == 4) {
                System.out.println("\nLogout Successful"
                );
                break;
            }
            // INVALID
            else {
                System.out.println("\nInvalid Choice");
            }
        }
        sc.close();
    }
}