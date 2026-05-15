package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    public enum Status {
        PRESENT,
        ABSENT,
        HALF_DAY,
        OD
    }

    private int employeeId;
    private String employeeName;
    private LocalDate date;
    private LocalTime checkInTime;
    private Status status;

    public Attendance(
            int employeeId,
            String employeeName,
            LocalDate date,
            LocalTime checkInTime,
            Status status
    ) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.date = date;
        this.checkInTime = checkInTime;
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {

        return "\nEmployee ID   : " + employeeId
                + "\nEmployee Name : " + employeeName
                + "\nDate          : " + date
                + "\nCheck In Time : " + checkInTime
                + "\nStatus        : " + status;
    }
}