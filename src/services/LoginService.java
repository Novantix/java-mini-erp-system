package services;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import models.User;

public class LoginService {

    Scanner sc = new Scanner(System.in);

    // FILE PATH
    String filePath = "data/users.txt";

    // ================= USERNAME VALIDATION =================
    public boolean isValidUsername(String username) {

        /*
           Username Must Contain:
           1 Alphabet
           1 Number
           1 Special Character
           Numbers Only NOT Allowed
        */

        boolean alphabet = false;
        boolean number = false;
        boolean special = false;

        for (int i = 0; i < username.length(); i++) {

            char ch = username.charAt(i);

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

    // ================= PASSWORD VALIDATION =================
    public boolean isValidPassword(String password) {

        /*
           Password Must Contain:
           1 Alphabet
           1 Number
           1 Special Character
           Numbers Only NOT Allowed
        */

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

    // ================= REGISTER USER =================
    public User registerUser() {

        System.out.println("\n===== USER REGISTRATION =====");

        String username;

        // USERNAME VALIDATION
        while (true) {

            System.out.print("Create Username : ");
            username = sc.nextLine().trim();

            if (isValidUsername(username)) {

                break;

            } else {

                System.out.println(
                        "Invalid Username!"
                );

                System.out.println(
                        "Username Must Contain Alphabet + Number + Special Character"
                );
            }
        }

        String password;

        // PASSWORD VALIDATION
        while (true) {

            System.out.print("Create Password : ");
            password = sc.nextLine().trim();

            if (isValidPassword(password)) {

                break;

            } else {

                System.out.println(
                        "Invalid Password!"
                );

                System.out.println(
                        "Password Must Contain Alphabet + Number + Special Character"
                );
            }
        }

        // ROLE VALIDATION
        String role;

        while (true) {

            System.out.print("Enter Role (Admin/Employee) : ");
            role = sc.nextLine().trim();

            // ACCEPT ONLY ADMIN OR EMPLOYEE
            if (role.equalsIgnoreCase("Admin") ||
                    role.equalsIgnoreCase("Employee")) {

                break;

            } else {

                System.out.println(
                        "Invalid Role! Enter only Admin or Employee."
                );
            }
        }

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

            // CREATE data FOLDER
            File folder = new File("data");

            if (!folder.exists()) {

                folder.mkdir();
            }

            // CREATE FILE
            File file = new File(filePath);

            if (!file.exists()) {

                file.createNewFile();
            }

            FileWriter writer =
                    new FileWriter(file, true);

            writer.write("===== USER DATA =====\n");
            writer.write("Username : " + username + "\n");
            writer.write("Password : " + password + "\n");
            writer.write("Role : " + role + "\n");
            writer.write("Check-In : " + checkInTime + "\n");
            writer.write("Check-Out : " + checkOutTime + "\n");
            writer.write("----------------------\n");

            writer.close();

            System.out.println(
                    "Registration Successful"
            );

        } catch (IOException e) {

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
            String username = sc.nextLine().trim();

            System.out.print("Enter Password : ");
            String password = sc.nextLine().trim();

            // ROLE OPTION
            System.out.print("Enter Role : ");
            String roleInput = sc.nextLine().trim();

            try {

                File file = new File(filePath);

                Scanner fileScanner = new Scanner(file);

                while (fileScanner.hasNextLine()) {

                    String line = fileScanner.nextLine();

                    // CHECK USERNAME
                    if (line.equalsIgnoreCase(
                            "Username : " + username
                    )) {

                        // PASSWORD
                        String passwordLine =
                                fileScanner.nextLine();

                        String storedPassword =
                                passwordLine.substring(11).trim();

                        // ROLE
                        String roleLine =
                                fileScanner.nextLine();

                        String storedRole =
                                roleLine.substring(7).trim();

                        // VALID PASSWORD + ROLE
                        if (storedPassword.equals(password)
                                &&
                                storedRole.equalsIgnoreCase(roleInput)) {

                            System.out.println(
                                    "\nLogin Successful"
                            );

                            System.out.println(
                                    "Welcome " + storedRole
                            );

                            fileScanner.close();

                            return new User(
                                    username,
                                    storedPassword,
                                    storedRole,
                                    "",
                                    ""
                            );
                        }
                    }
                }

                fileScanner.close();

            } catch (Exception e) {

                System.out.println("File Error");
            }

            attempts++;

            System.out.println(
                    "\nInvalid Username, Password or Role"
            );

            System.out.println(
                    "Remaining Attempts : "
                            + (2 - attempts)
            );
        }

        System.out.println(
                "\nMaximum Login Attempts Reached"
        );

        return null;
    }

    // ================= FORGOT PASSWORD =================
    public void forgotPassword() {

        System.out.println("\n===== RESET PASSWORD =====");

        System.out.print("Enter Username : ");
        String username = sc.nextLine().trim();

        System.out.print("Enter Role : ");
        String roleInput = sc.nextLine().trim();

        ArrayList<String> lines = new ArrayList<>();

        boolean found = false;

        try {

            File file = new File(filePath);

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                lines.add(line);
            }

            fileScanner.close();

            for (int i = 0; i < lines.size(); i++) {

                if (lines.get(i).equalsIgnoreCase(
                        "Username : " + username
                )) {

                    String roleLine =
                            lines.get(i + 2);

                    String storedRole =
                            roleLine.substring(7).trim();

                    // AUTHENTICATION
                    if (storedRole.equalsIgnoreCase(
                            roleInput
                    )) {

                        String newPassword;

                        while (true) {

                            System.out.print(
                                    "Enter New Password : "
                            );

                            newPassword =
                                    sc.nextLine().trim();

                            if (isValidPassword(newPassword)) {

                                break;

                            } else {

                                System.out.println(
                                        "Password Must Contain Alphabet + Number + Special Character"
                                );
                            }
                        }

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
                        new FileWriter(filePath);

                for (String data : lines) {

                    writer.write(data + "\n");
                }

                writer.close();

                System.out.println(
                        "\nPassword Updated Successfully"
                );

            } else {

                System.out.println(
                        "\nAuthentication Failed"
                );
            }

        } catch (Exception e) {

            System.out.println("File Error");
        }
    }
}