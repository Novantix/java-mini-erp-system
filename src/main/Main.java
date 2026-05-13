package main;

import models.User;
import services.LoginService;
import services.RoleService;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        User registeredUser = loginService.registerUser();
        User loginUser = loginService.login(registeredUser);
        if (loginUser != null) {
            if (roleService.isAdmin(loginUser.getRole())) {
                System.out.println("Welcome Admin");
                System.out.println("Admin Access Granted");
            } else if (roleService.isEmployee(loginUser.getRole())) {
                System.out.println("Welcome Employee");
                System.out.println("Employee Access Granted");
            } else {
                System.out.println("Invalid Role");
            }
        } else {
            System.out.println("Login Failed");
        }
    }
}
    