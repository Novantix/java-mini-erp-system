package services;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import models.User;
public class LoginService {
    Scanner sc = new Scanner(System.in);
    // ================= REGISTER USER =================
    public User registerUser() {
        System.out.println("\n===== USER REGISTRATION =====");
        System.out.print("Create Username : ");
        String username = sc.nextLine();
        System.out.print("Create Password : ");
        String password = sc.nextLine();
        System.out.print("Enter Role (Admin/Employee) : ");
        String role = sc.nextLine();
        // CHECK-IN TIME
        String checkInTime =
                LocalTime.now()
                        .withNano(0)
                        .toString();
        // CHECK-OUT TIME
        String checkOutTime =
                LocalTime.now()
                        .plusHours(8)
                        .withNano(0)
                        .toString();
        System.out.println("Check-In Time  : " + checkInTime);
        System.out.println("Check-Out Time : " + checkOutTime);
        User user = new User(
                username,
                password,
                role,
                checkInTime,
                checkOutTime
        );
        // SAVE DATA
        try {
            FileWriter writer =
                    new FileWriter(
                            "data/users.txt",
                            true
                    );
            writer.write("===== USER DATA =====\n");
            writer.write("Username : " + username + "\n");
            writer.write("Password : " + password + "\n");
            writer.write("Role : " + role + "\n");
            writer.write("Check-In : " + checkInTime + "\n");
            writer.write("Check-Out : " + checkOutTime + "\n");
            writer.write("----------------------\n");
            writer.close();
            System.out.println(
                    "Registration Successful");
        }
        catch (IOException e) {
            System.out.println("File Error");
        }
        return user;
    }
    // ================= LOGIN =================
    public User login() {
        int attempts = 0;
        long startTime =
                System.currentTimeMillis();
        while (attempts < 2) {
            long currentTime =
                    System.currentTimeMillis();
            long seconds =
                    (currentTime - startTime) / 1000;
            // 30 SEC LIMIT
            if (seconds > 30) {
                System.out.println(
                        "\nLogin Time Expired (30 Seconds)"
                );
                return null;
            }
            System.out.println("\n===== LOGIN PAGE =====");
            System.out.print("Enter Username : ");
            String username = sc.nextLine();
            System.out.print("Enter Password : ");
            String password = sc.nextLine();
            try {
                File file = new File("data/users.txt");
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    // CHECK USERNAME
                    if (line.equals("Username : " + username)) {
                        // PASSWORD
                        String passwordLine = fileScanner.nextLine();
                        String storedPassword = passwordLine.substring(11);
                        // ROLE
                        String roleLine = fileScanner.nextLine();
                        String role = roleLine.substring(7);
                        // VALID PASSWORD
                        if (storedPassword.equals(password)) {
                            System.out.println("\nLogin Successful");
                            fileScanner.close();
                            return new User(
                                    username,
                                    storedPassword,
                                    role,
                                    "",
                                    ""
                            );
                        }
                    }
                }
                fileScanner.close();
            }
            catch (Exception e) {
                System.out.println("File Error");
            }
            attempts++;
            System.out.println("\nInvalid Username or Password");
            System.out.println("Remaining Attempts : " + (2 - attempts)
            );
        }
        System.out.println("\nMaximum Login Attempts Reached");
        return null;
    }
    // ================= FORGOT PASSWORD =================
    public void forgotPassword() {
        System.out.println("\n===== RESET PASSWORD =====");
        System.out.print("Enter Username : ");
        String username = sc.nextLine();
        System.out.print("Enter Role : ");
        String roleInput = sc.nextLine();
        ArrayList<String> lines = new ArrayList<>();
        boolean found = false;
        try {
            File file = new File("data/users.txt");
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                lines.add(line);
            }
            fileScanner.close();
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals("Username : " + username
                )) {
                    String roleLine = lines.get(i + 2);
                    String storedRole = roleLine.substring(7);
                    // AUTHENTICATION
                    if (storedRole.equalsIgnoreCase(
                            roleInput
                    )) {
                        System.out.print("Enter New Password : ");
                        String newPassword = sc.nextLine();
                        lines.set(
                                i + 1,
                                "Password : " + newPassword
                        );
                        found = true;
                        break;
                    }
                }
            }
            // UPDATE FILE
            if (found) {
                FileWriter writer =
                        new FileWriter(
                                "data/users.txt"
                        );
                for (String data : lines) {
                    writer.write(data + "\n");
                }
                writer.close();
                System.out.println("\nPassword Updated Successfully");
            }
            else {System.out.println("\nAuthentication Failed");
            }
        }
        catch (Exception e) {
            System.out.println("File Error");
        }
    }
}