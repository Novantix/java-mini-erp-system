package models;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int employeeId;
    private String employeeName;
    private String department;
    private String designation;
    private double salary;
    private String managerName;
    private int yearsOfExperience;
    private String promotionStatus;

    // Default constructor
    public Employee() {
        this.promotionStatus = "Not Promoted";
    }

    // Parameterized constructor
    public Employee(int employeeId, String employeeName, String department,
                    String designation, double salary, String managerName,
                    int yearsOfExperience, String promotionStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.managerName = managerName;
        this.yearsOfExperience = yearsOfExperience;
        this.promotionStatus = promotionStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(String promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    @Override
    public String toString() {
        return "Employee ID      : " + employeeId + "\n" +
               "Employee Name    : " + employeeName + "\n" +
               "Department       : " + department + "\n" +
               "Designation      : " + designation + "\n" +
               "Salary           : " + salary + "\n" +
               "Manager Name     : " + managerName + "\n" +
               "Experience       : " + yearsOfExperience + " years\n" +
               "Promotion Status : " + promotionStatus;
    }
}