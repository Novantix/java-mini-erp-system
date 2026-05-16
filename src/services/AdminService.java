package services;

import java.util.Scanner;

public class AdminService {

    Scanner sc = new Scanner(System.in);

    FileService fileService = new FileService();

    public void adminMenu() {

        int choice;

        do {

            System.out.println(
                    "\n========== ADMIN PANEL ==========");

            System.out.println("1. View Users");
            System.out.println("2. Add User");
            System.out.println("3. Delete User By ID");
            System.out.println("4. View Reports");
            System.out.println("5. View Audit Logs");
            System.out.println("0. Exit");

            System.out.println(
                    "=================================");

            System.out.print(
                    "Enter Choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    fileService.readData(
                            "data/users.txt");

                    break;

                case 2:

                    addUser();

                    break;

                case 3:

                    System.out.print(
                            "Enter User ID  : ");

                    int deleteId = sc.nextInt();
                    sc.nextLine();

                    fileService.deleteUserById(
                            "data/users.txt",
                            deleteId
                    );

                    break;

                case 4:

                    System.out.println(
                            "\n===== USERS REPORT =====");

                    fileService.readData(
                            "data/users.txt");

                    break;

                case 5:
                    System.out.println("\n===== AUDIT LOGS =====");
                    fileService.readData("data/audit_log.txt");
                    break;


                case 0:

                    System.out.println(
                            "\nExiting Admin Panel...");
                    break;

                default:

                    System.out.println(
                            "\nInvalid Choice");
            }

        } while (choice != 0);
    }

    // ADD USER
    public void addUser() {

        System.out.print(
                "Enter User ID : ");

        int id = sc.nextInt();
        sc.nextLine();

        System.out.print(
                "Enter Username : ");

        String username = sc.nextLine();

        System.out.print(
                "Enter Role : ");

        String role = sc.nextLine();

        String data
                = "ID : " + id
                + " | Username : "
                + username
                + " | Role : "
                + role;

        fileService.saveData(
                "data/users.txt",
                data
        );
    }
}
