package main;
import java.util.Scanner;
import models.Purchase;
import models.Supplier;
import models.User;
import services.LoginService;
import services.PurchaseService;
import services.RoleService;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService();
        RoleService roleService = new RoleService();
        while (true) {
            User loginUser = null;
            // MAIN MENU
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
                // ROLE VALIDATION
                if (loginUser != null) {
                    if (roleService.isAdmin(
                            loginUser.getRole())) {
                        System.out.println(
                                "\nWelcome Admin");
                        System.out.println(
                                "Admin Access Granted");
                    } else if (roleService.isEmployee(
                            loginUser.getRole())) {
                        System.out.println(
                                "\nWelcome Employee");
                        System.out.println(
                                "Employee Access Granted");
                    } else {
                        System.out.println(
                                "\nWelcome User");
                    }
                    // STOP PROGRAM AFTER SUCCESS LOGIN
                    break;
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
       
          PurchaseService service = new PurchaseService();

        Supplier supplier1 =
                new Supplier(1, "Ramesh", "Laptop", 50);

        Supplier supplier2 =
                new Supplier(2, "Suresh", "Mouse", 100);

        service.addSupplier(supplier1);
        service.addSupplier(supplier2);

        Purchase purchase1 =
                new Purchase(101, "Laptop", 2, 90000);

        service.addPurchase(purchase1);

        System.out.println("\nSUPPLIER DETAILS");
        service.viewSuppliers();

        System.out.println("\nPURCHASE DETAILS");
        service.viewPurchases();

        System.out.println("\nSTOCK CHECK");
        service.checkStockAvailability("Laptop");
         sc.close();
        
    }
}