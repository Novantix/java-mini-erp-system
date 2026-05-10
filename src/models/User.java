package models;

import java.util.Scanner;

public class User {

    private int userId;
    private String username;
    private String password;
    private String role;
    private boolean isLoggedIn;

    public void getInput() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        userId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Username: ");
        username = sc.nextLine();

        System.out.print("Enter Password: ");
        password = sc.nextLine();

        System.out.print("Enter Role: ");
        role = sc.nextLine();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}