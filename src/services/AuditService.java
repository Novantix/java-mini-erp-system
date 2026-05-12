package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditService {
    private List<String> sessionLogs = new ArrayList<>();
    private static final String AUDIT_FILE_PATH = "src/reports/audit_logs.txt";                  // have to create the file ...
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Audit Log Management
    public void logAction(String username, String action) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] User: " + username + " | Action: " + action;
        sessionLogs.add(logEntry);
        saveLogToFile(logEntry);
    }

    private void saveLogToFile(String logEntry) {
        try {
            File file = new File(AUDIT_FILE_PATH);
            file.getParentFile().mkdirs(); // Ensures 'src/reports' directory exists
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(logEntry + "\n");
            }
        } catch (IOException e) {
            System.out.println("[Audit Error] Could not save log to file: " + e.getMessage());
        }
    }

    public void displaySessionLogs() {
        System.out.println("\n=== Current Session Audit Logs ===");
        sessionLogs.forEach(System.out::println);
        System.out.println("==================================\n");
    }
}