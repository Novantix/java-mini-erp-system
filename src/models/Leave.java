package models;

import java.time.LocalDate;

public class Leave {

    private int employeeId;
    private String employeeName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private String status;

    // Constructor
    public Leave(
            int employeeId,
            String employeeName,
            LocalDate fromDate,
            LocalDate toDate,
            String reason
    ) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = "PENDING";
    }

    // Getters
    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    // Approve Leave
    public void approveLeave() {
        this.status = "APPROVED";
    }

    // Reject Leave
    public void rejectLeave() {
        this.status = "REJECTED";
    }

    @Override
    public String toString() {

        return "Employee ID : " + employeeId
                + "\nEmployee Name : " + employeeName
                + "\nFrom Date : " + fromDate
                + "\nTo Date : " + toDate
                + "\nReason : " + reason
                + "\nStatus : " + status;
    }
}
