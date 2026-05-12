package services;
public class RoleService {
    public boolean isAdmin(String role) {
        if (role.equalsIgnoreCase("admin")) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isEmployee(String role) {
        if (role.equalsIgnoreCase("employee")) {
            return true;
        } else {
            return false;
        }
    }
}