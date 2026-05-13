package models;
public class User {
    private String username;
    private String password;
    private String role;
    private String checkInTime;
    private String checkOutTime;
    // CONSTRUCTOR
    public User(String username,
                String password,
                String role,
                String checkInTime,
                String checkOutTime) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }
    // GETTERS
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public String getCheckInTime() {
        return checkInTime;
    }
    public String getCheckOutTime() {
        return checkOutTime;
    }
    // SETTERS
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}