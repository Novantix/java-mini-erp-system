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

        while (true) {

            User loginUser = null;

            System.out.println("\n===== MAIN MENU =====");
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

                if (loginUser != null) {

                    // ADMIN LOGIN
                    if (roleService.isAdmin(
                            loginUser.getRole())) {

                        System.out.println(
                                "\nWelcome Admin");

                        System.out.println(
                                "Admin Access Granted");

                        EmployeeService employeeService =
                                new EmployeeService();

                        employeeService.employeeDashboard();
                    }

                    // EMPLOYEE LOGIN
                    else if (roleService.isEmployee(
                            loginUser.getRole())) {

                        System.out.println(
                                "\nWelcome Employee");

                        System.out.println(
                                "Employee Access Granted");

                        EmployeeService employeeService =
                                new EmployeeService();

                        employeeService.employeeDashboard();
                    }

                    // NORMAL USER
                    else {

                        System.out.println(
                                "\nWelcome User");
                    }
                }
            }

            // FORGOT PASSWORD
            else if (choice == 3) {

                loginService.forgotPassword();
            }

            // EXIT
            else if (choice == 4) {

                System.out.println("Program Closed");
                break;
            }

            else {

                System.out.println("Invalid Choice");
            }
        }

        sc.close();
    }
}