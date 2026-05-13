package services;
public class RoleService {
    // CHECK ADMIN
    public boolean isAdmin(String role) {
        if (role.equalsIgnoreCase("admin")) {
            return true;
        } else {
            return false;
        }
    }
    // CHECK EMPLOYEE
    public boolean isEmployee(String role) {
        if (role.equalsIgnoreCase("employee")) {
            return true;
        } else {
            return false;
        }
    }
}