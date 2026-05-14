package models;

import java.time.LocalDate;

public class Leave {

    private int employeeId;
    private String employeeName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private String status;

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

    public int getEmployeeId() {
        return employeeId;
    }

    public void approveLeave() {
        this.status = "APPROVED";
    }

    @Override
    public String toString() {

        return "\nEmployee ID   : " + employeeId
                + "\nEmployee Name : " + employeeName
                + "\nFrom Date     : " + fromDate
                + "\nTo Date       : " + toDate
                + "\nReason        : " + reason
                + "\nStatus        : " + status;
    }
}
