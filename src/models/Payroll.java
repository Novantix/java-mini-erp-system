package models;

public class Payroll {

    private int employeeId;
    private double salary;
    private String months;
    private int year;

    public Payroll(int employeeId, double salary, String months,int year) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.months = months;
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
        return months;
    }

    public void setMonth(String months) {
        this.months = months;
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
                ", month='" + months + '\'' +
                ", year=" + year +
                '}';
    }
}

