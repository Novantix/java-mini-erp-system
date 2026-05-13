package services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;
import models.User;
public class LoginService {
    Scanner sc = new Scanner(System.in);
    // REGISTER USER
    public User registerUser() {
        System.out.println("\n===== USER REGISTRATION =====");
        System.out.print("Create Username : ");
        String username = sc.nextLine();
        System.out.print("Create Password : ");
        String password = sc.nextLine();
        System.out.print("Enter Role (Admin/Employee) : ");
        String role = sc.nextLine();
        // CHECK-IN TIME
        String checkInTime = LocalTime.now()
                .withNano(0)
                .toString();
        // CHECK-OUT TIME
        String checkOutTime = LocalTime.now()
                .plusHours(8)
                .withNano(0)
                .toString();
        System.out.println("Check-In Time  : " + checkInTime);
        System.out.println("Check-Out Time : " + checkOutTime);
        // CREATE USER OBJECT
        User user = new User(
                username,
                password,
                role,
                checkInTime,
                checkOutTime
        );
        // STORE DATA INTO FILE
        try {
            FileWriter writer =
                    new FileWriter("src/data/users.txt", true);
            writer.write("===== USER DATA =====\n");
            writer.write("Username : " + username + "\n");
            writer.write("Password : " + password + "\n");
            writer.write("Role : " + role + "\n");
            writer.write("Check-In : " + checkInTime + "\n");
            writer.write("Check-Out : " + checkOutTime + "\n");
            writer.write("----------------------\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("File Error");
        }
        System.out.println("Registration Successful");
        return user;
    }
    // LOGIN USER
    public User login() {
        while (true) {
            System.out.println("\n===== LOGIN PAGE =====");
            System.out.print("Enter Username : ");
            String username = sc.nextLine();
            System.out.print("Enter Password : ");
            String password = sc.nextLine();
            try {
                File file = new File("src/data/users.txt");
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    // CHECK USERNAME
                    if (line.equals("Username : " + username)) {
                        // READ PASSWORD
                        String passwordLine =
                                fileScanner.nextLine();
                        String storedPassword =
                                passwordLine.substring(11);
                        // READ ROLE
                        String roleLine =
                                fileScanner.nextLine();
                        String role =
                                roleLine.substring(7);
                        // CHECK PASSWORD
                        if (storedPassword.equals(password)) {
                            System.out.println(
                                    "\nLogin Successful");
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
                // INVALID LOGIN
                System.out.println(
                        "\nInvalid Username or Password");
                // SHOW MENU AGAIN
                System.out.println("\n1. Try Login Again");
                System.out.println("2. Back To Main Menu");
                System.out.print("Enter Choice : ");
                int choice = sc.nextInt();
                sc.nextLine();
                // BACK TO MAIN MENU
                if (choice == 2) {
                    return null;
                }
            } catch (Exception e) {
                System.out.println("File Error");
            }
        }
    }
    // FORGOT PASSWORD
    public void forgotPassword() {
        System.out.println("\n===== FORGOT PASSWORD =====");
        System.out.print("Enter Username : ");
        String username = sc.nextLine();
        try {
            File file = new File("src/data/users.txt");
            Scanner fileScanner = new Scanner(file);
            boolean found = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // CHECK USERNAME
                if (line.equals("Username : " + username)) {
                    // READ PASSWORD LINE
                    String passwordLine =
                            fileScanner.nextLine();
                    System.out.println(passwordLine);
                    found = true;
                    break;
                }
            }
            if (found == false) {

                System.out.println("Username Not Found");
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("File Error");
        }
    }
}