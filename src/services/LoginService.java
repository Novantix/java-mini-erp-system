package services;

import java.util.Scanner;
import models.User;

public class LoginService {

    public void login(User user) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Login Username: ");
        String loginUsername = sc.nextLine();

        System.out.print("Login Password: ");
        String loginPassword = sc.nextLine();

        if (user.getUsername().equals(loginUsername)
                && user.getPassword().equals(loginPassword)) {

            user.setLoggedIn(true);
            System.out.println("Login Successful");

        } else {

            System.out.println("Invalid Username or Password");
        }
    }
}