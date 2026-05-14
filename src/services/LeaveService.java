package services;

import data.DataStore;
import models.Leave;

import java.time.LocalDate;

public class LeaveService {

    public void applyLeave(
            int employeeId,
            String employeeName,
            LocalDate fromDate,
            LocalDate toDate,
            String reason
    ) {

        Leave leave = new Leave(
                employeeId,
                employeeName,
                fromDate,
                toDate,
                reason
        );

        DataStore.leaveList.add(leave);

        System.out.println("\nLeave Applied Successfully");
    }

    public void approveLeave(int employeeId) {

        for (Leave leave : DataStore.leaveList) {

            if (leave.getEmployeeId() == employeeId) {

                leave.approveLeave();

                System.out.println("\nLeave Approved");
            }
        }
    }

    public void showAllLeaves() {

        System.out.println("\n===== LEAVE REPORT =====");

        for (Leave leave : DataStore.leaveList) {

            System.out.println(leave);
            System.out.println("------------------------");
        }
    }
}
