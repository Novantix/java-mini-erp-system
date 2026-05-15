package models;

public class Payroll {

    private int employeeId;
    //private String employeeName;
    private double salary;
    private String month;
    private int year;

    public Payroll(int employeeId, double salary, String month,int year) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.month = month;
        this.year = year;

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "employeeId=" + employeeId +
                ", salary=" + salary +
                ", month='" + month + '\'' +
                ", year=" + year +
                '}';
    }
}

