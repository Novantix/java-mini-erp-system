package services;

import java.util.Scanner;
import models.User;
public class LoginService {
    Scanner sc = new Scanner(System.in);
    public User registerUser() {
        System.out.println("===== USER REGISTRATION =====");
        System.out.print("Create Username : ");
        String username = sc.nextLine();
        System.out.print("Create Password : ");
        String password = sc.nextLine();
        System.out.print("Enter Role (Admin/Employee) : ");
        String role = sc.nextLine();
        User user = new User(username, password, role);
        System.out.println("Registration Successful");
        return user;
    }
    public User login(User registeredUser) {
        System.out.println("\n===== LOGIN PAGE =====");
        System.out.print("Enter Username : ");
        String loginUsername = sc.nextLine();
        System.out.print("Enter Password : ");
        String loginPassword = sc.nextLine();
        if (registeredUser.getUsername().equals(loginUsername)
                && registeredUser.getPassword().equals(loginPassword)) {
            System.out.println("Login Successful");
            return registeredUser;
        } else {
            System.out.println("Invalid Username or Password");
            return null;
        }
    }
}