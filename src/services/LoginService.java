package services;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import models.User;

public class LoginService {

    Scanner sc = new Scanner(System.in);
    String filePath = "data/users.txt";

    // ================= USERNAME VALIDATION =================
    public boolean isValidUsername(String username) {

        boolean hasAlphabet = false;

        for (int i = 0; i < username.length(); i++) {

            char ch = username.charAt(i);

            if (Character.isLetter(ch)) {
                hasAlphabet = true;

            } else if (Character.isDigit(ch)) {
                // allowed

            } else if (ch == ' ') {
                // allowed space

            } else {
                return false;
            }
        }

        return hasAlphabet;
    }

    // ================= PASSWORD VALIDATION =================
    public boolean isValidPassword(String password) {

        boolean alphabet = false;
        boolean number = false;
        boolean special = false;

        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (Character.isLetter(ch)) {
                alphabet = true;

            } else if (Character.isDigit(ch)) {
                number = true;

            } else {
                special = true;
            }
        }

        return alphabet && number && special;
    }

    // ================= CHECK USER EXISTS =================
    public boolean isUserExists(String username) {

        try {
            File file = new File(filePath);
            if (!file.exists()) return false;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                if (line.equalsIgnoreCase("Username : " + username)) {
                    fileScanner.close();
                    return true;
                }
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("File Error");
        }

        return false;
    }

    // ================= REGISTER USER =================
    public User registerUser() {

        System.out.println("\n===== USER REGISTRATION =====");

        String username;

        while (true) {

            System.out.print("Create Username : ");
            username = sc.nextLine().trim();

            if (!isValidUsername(username)) {
                System.out.println("Invalid Username!");
                continue;
            }

            if (isUserExists(username)) {
                System.out.println("Username Already Exists!");
                continue;
            }

            break;
        }

        String password;

        while (true) {

            System.out.print("Create Password : ");
            password = sc.nextLine().trim();

            if (isValidPassword(password)) break;

            System.out.println("Invalid Password!");
        }

        String role = "";

        while (true) {

            System.out.println("\n===== ROLE MENU =====");
            System.out.println("1. Admin");
            System.out.println("2. HR");

            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    role = "Admin";
                    break;

                } else if (choice == 2) {
                    role = "HR";
                    break;

                } else {
                    System.out.println("Invalid Choice!");
                }

            } catch (Exception e) {
                System.out.println("Enter Numbers Only!");
            }
        }

        String checkInTime = LocalTime.now().withNano(0).toString();
        String checkOutTime = LocalTime.now().plusHours(8).withNano(0).toString();

        User user = new User(username, password, role, checkInTime, checkOutTime);

        try {

            File folder = new File("data");
            if (!folder.exists()) folder.mkdir();

            File file = new File(filePath);
            if (!file.exists()) file.createNewFile();

            FileWriter writer = new FileWriter(file, true);

            writer.write("===== USER DATA =====\n");
            writer.write("Username : " + username + "\n");
            writer.write("Password : " + password + "\n");
            writer.write("Role : " + role + "\n");
            writer.write("Check-In : " + checkInTime + "\n");
            writer.write("Check-Out : " + checkOutTime + "\n");
            writer.write("----------------------\n");

            writer.close();

            System.out.println("\nRegistration Successful!");

        } catch (IOException e) {
            System.out.println("File Error");
        }

        return user;
    }

    // ================= LOGIN =================
    public User login() {

        int attempts = 0;
        long startTime = System.currentTimeMillis();

        while (attempts < 2) {

            long seconds = (System.currentTimeMillis() - startTime) / 1000;

            if (seconds > 30) {
                System.out.println("\nLogin Time Expired (30 Seconds)");
                return null;
            }

            System.out.println("\n===== LOGIN PAGE =====");

            System.out.print("Enter Username : ");
            String username = sc.nextLine().trim();

            System.out.print("Enter Password : ");
            String password = sc.nextLine().trim();

            try {

                File file = new File(filePath);
                Scanner fileScanner = new Scanner(file);

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();

                    if (line.equalsIgnoreCase("Username : " + username)) {

                        String passwordLine = fileScanner.nextLine();
                        String storedPassword = passwordLine.substring(11).trim();

                        String roleLine = fileScanner.nextLine();
                        String storedRole = roleLine.substring(7).trim();

                        if (storedPassword.equals(password)) {

                            System.out.println("\nLogin Successful!");

                            // ✅ REMOVED WELCOME LINE AS REQUESTED

                            fileScanner.close();

                            return new User(username, storedPassword, storedRole, "", "");
                        }
                    }
                }

                fileScanner.close();

            } catch (Exception e) {
                System.out.println("File Error");
            }

            attempts++;

            System.out.println("\nInvalid Username or Password");
            System.out.println("Remaining Attempts: " + (2 - attempts));
        }

        System.out.println("\nMaximum Login Attempts Reached");
        return null;
    }

    // ================= FORGOT PASSWORD =================
    public void forgotPassword() {

        System.out.println("\n===== RESET PASSWORD =====");

        System.out.print("Enter Username : ");
        String username = sc.nextLine().trim();

        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;

        try {

            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }

            fileScanner.close();

            for (int i = 0; i < lines.size(); i++) {

                if (lines.get(i).equalsIgnoreCase("Username : " + username)) {

                    System.out.print("Enter New Password : ");
                    String newPassword = sc.nextLine().trim();

                    lines.set(i + 1, "Password : " + newPassword);
                    found = true;
                    break;
                }
            }

            if (found) {

                FileWriter writer = new FileWriter(filePath);

                for (String data : lines) {
                    writer.write(data + "\n");
                }

                writer.close();

                System.out.println("\nPassword Updated Successfully!");

            } else {
                System.out.println("\nUsername Not Found!");
            }

        } catch (Exception e) {
            System.out.println("File Error");
        }
    }
}