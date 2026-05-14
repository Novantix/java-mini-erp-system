package models;

public class Payroll {

    private int employeeId;
    //private String employeeName;
    private double salary;
    private String yrmonth;

    public Payroll(int employeeId, double salary, String yrmonth) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.yrmonth = yrmonth;

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

    public String getYrmonth() {
        return yrmonth;
    }

    public void setYrmonth(String yrmonth) {
        this.yrmonth = yrmonth;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "employeeId=" + employeeId +
                ", salary=" + salary +
                ", yrmonth=" + yrmonth +
                '}';
    }
}

