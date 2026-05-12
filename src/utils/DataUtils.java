package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    public static String getCurrentDateTime() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getCurrentMonth() {
        return LocalDate.now()
            .format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
    }

    public static String getCurrentYear() {
        return LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy"));
    }
}