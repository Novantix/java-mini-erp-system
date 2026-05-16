package services;

import data.DataStore;
import models.Attendance;
import models.Attendance.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceService {

    public void markAttendance(
            int employeeId,
            String employeeName,
            LocalTime checkInTime,
            Status status) {

        Attendance attendance = new Attendance(
                employeeId,
                employeeName,
                LocalDate.now(),
                checkInTime,
                status);

        DataStore.attendanceList.add(attendance);

        System.out.println("\nAttendance Marked Successfully");

        if (checkInTime.isAfter(LocalTime.of(9, 30))) {

            System.out.println("Late Mark Applied");
        }

        if (status == Status.HALF_DAY) {

            System.out.println("Half Day Recorded");
        }

        if (status == Status.OD) {

            System.out.println("On Duty Recorded");
        }
    }

    public void showAllAttendance() {

        System.out.println("\n========== ATTENDANCE REPORT ==========");

        if (DataStore.attendanceList.isEmpty()) {
            System.out.println("No attendance records found.");
            System.out.println("========================================");
            return;
        }

        int totalPresent = 0;
        int totalAbsent = 0;
        int totalHalfDay = 0;
        int totalOD = 0;

        for (Attendance attendance : DataStore.attendanceList) {
            System.out.println(attendance);
            System.out.println("---------------------------------------");

            switch (attendance.getStatus()) {
                case PRESENT:
                    totalPresent++;
                    break;
                case ABSENT:
                    totalAbsent++;
                    break;
                case HALF_DAY:
                    totalHalfDay++;
                    break;
                case OD:
                    totalOD++;
                    break;
            }
        }

        int totalAttended = totalPresent + totalHalfDay + totalOD;
        int totalNotAttended = totalAbsent;
        int grandTotal = DataStore.attendanceList.size();

        System.out.println("\n======== ATTENDANCE SUMMARY ========");
        System.out.println("Total Records     : " + grandTotal);
        System.out.println("------------------------------------");
        System.out.println("Present           : " + totalPresent);
        System.out.println("Half Day          : " + totalHalfDay);
        System.out.println("On Duty (OD)      : " + totalOD);
        System.out.println("Absent            : " + totalAbsent);
        System.out.println("------------------------------------");
        System.out.println("Total ATTENDED    : " + totalAttended);
        System.out.println("Total NOT ATTENDED: " + totalNotAttended);
        System.out.println("====================================\n");
    }
}
