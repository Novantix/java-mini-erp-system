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
            Status status
    ) {

        Attendance attendance = new Attendance(
                employeeId,
                employeeName,
                LocalDate.now(),
                checkInTime,
                status
        );

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

        System.out.println("\n===== ATTENDANCE REPORT =====");

        for (Attendance attendance : DataStore.attendanceList) {

            System.out.println(attendance);
            System.out.println("-----------------------------");
        }
    }
}
