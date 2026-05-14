package services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import models.User;
import services.AuditService;

public class AuditService {

    private List<String> auditLogs = new ArrayList<>();

    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private static final String AUDIT_FILE
            = "data/audit_log.txt";

    // Constructor Loads existing logs from file
    public AuditService() {
        loadLogsFromFile();
    }

    private void loadLogsFromFile() {
        try {
            File file = new File(AUDIT_FILE);
            if (file.exists()) {
                List<String> lines = Files.readAllLines(
                        Paths.get(AUDIT_FILE));
                auditLogs.addAll(lines);
            }
        } catch (Exception e) {
            System.out.println(
                    "[AuditService Warning] "
                    + "Could not load previous audit logs: "
                    + e.getMessage());
        }
    }

    private boolean isLoggedIn(User user) {
        if (user == null) {
            System.out.println(
                    "[Access Denied] "
                    + "No user is logged in. "
                    + "Please login first.");
            return false;
        }
        return true;
    }

    private boolean hasRole(User user,
            String... allowedRoles) {
        for (String role : allowedRoles) {
            if (user.getRole().equalsIgnoreCase(role)) {
                return true;
            }
        }
        System.out.println(
                "[Access Denied] User '"
                + user.getUsername()
                + "' with role '"
                + user.getRole()
                + "' is not allowed to perform this action.");
        return false;
    }

    public void logAction(String username, String action) {
        try {
            if (username == null
                    || username.trim().isEmpty()) {
                username = "SYSTEM";
            }
            if (action == null
                    || action.trim().isEmpty()) {
                System.out.println(
                        "[AuditService] "
                        + "Cannot log empty action.");
                return;
            }

            String timestamp = LocalDateTime.now()
                    .format(FORMATTER);
            String logEntry
                    = "[" + timestamp + "] "
                    + "USER: " + username.toUpperCase()
                    + " | ACTION: " + action;

            auditLogs.add(logEntry);

            // Save to file immediately
            appendToFile(logEntry);

        } catch (Exception e) {
            System.out.println(
                    "[AuditService Error] logAction : "
                    + e.getMessage());
        }
    }

    // ─────────────────────────────────────
    // 2. View All Logs
    // ADMIN only
    // ─────────────────────────────────────
    public void viewAllLogs(User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) {
                return;
            }
            if (!hasRole(loggedInUser, "ADMIN")) {
                return;
            }

            if (auditLogs.isEmpty()) {
                System.out.println(
                        "[Audit] No audit logs available.");
                return;
            }

            System.out.println(
                    "\n========== AUDIT LOG ==========");
            System.out.println(
                    "Total Entries : " + auditLogs.size());
            System.out.println(
                    "--------------------------------");
            for (String log : auditLogs) {
                System.out.println(log);
            }
            System.out.println(
                    "================================\n");

        } catch (Exception e) {
            System.out.println(
                    "[AuditService Error] viewAllLogs : "
                    + e.getMessage());
        }
    }

    // ─────────────────────────────────────
    // 3. View Logs by Username
    // ADMIN only
    // ─────────────────────────────────────
    public void viewLogsByUser(String targetUsername,
            User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) {
                return;
            }
            if (!hasRole(loggedInUser, "ADMIN")) {
                return;
            }

            if (targetUsername == null
                    || targetUsername.trim().isEmpty()) {
                System.out.println(
                        "[Audit] Username cannot be empty.");
                return;
            }

            List<String> filtered = new ArrayList<>();
            for (String log : auditLogs) {
                if (log.toUpperCase().contains(
                        "USER: "
                        + targetUsername.toUpperCase())) {
                    filtered.add(log);
                }
            }

            if (filtered.isEmpty()) {
                System.out.println(
                        "[Audit] No logs found for user: "
                        + targetUsername);
                return;
            }

            System.out.println(
                    "\n===== LOGS FOR USER: "
                    + targetUsername.toUpperCase()
                    + " =====");
            for (String log : filtered) {
                System.out.println(log);
            }
            System.out.println(
                    "===========================================\n");

        } catch (Exception e) {
            System.out.println(
                    "[AuditService Error] viewLogsByUser : "
                    + e.getMessage());
        }
    }

    // ─────────────────────────────────────
    // 4. View Logs by Date
    // ADMIN and MANAGER
    // ─────────────────────────────────────
    public void viewLogsByDate(String date,
            User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) {
                return;
            }
            if (!hasRole(loggedInUser,
                    "ADMIN", "MANAGER")) {
                return;
            }

            if (date == null
                    || date.trim().isEmpty()) {
                System.out.println(
                        "[Audit] Date cannot be empty. "
                        + "Use format: dd-MM-yyyy");
                return;
            }

            List<String> filtered = new ArrayList<>();
            for (String log : auditLogs) {
                if (log.contains(date)) {
                    filtered.add(log);
                }
            }

            if (filtered.isEmpty()) {
                System.out.println(
                        "[Audit] No logs found for date: "
                        + date);
                return;
            }

            System.out.println(
                    "\n===== LOGS FOR DATE: "
                    + date + " =====");
            for (String log : filtered) {
                System.out.println(log);
            }
            System.out.println(
                    "=====================================\n");

        } catch (Exception e) {
            System.out.println(
                    "[AuditService Error] viewLogsByDate : "
                    + e.getMessage());
        }
    }

    // ─────────────────────────────────────
    // 5. Export Logs to File
    // ADMIN only
    // Called by BackupService Module 8
    // ─────────────────────────────────────
    public void exportLogsToFile(User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) {
                return;
            }
            if (!hasRole(loggedInUser, "ADMIN")) {
                return;
            }

            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Overwrite mode for full export
            FileWriter fw = new FileWriter(
                    AUDIT_FILE, false);
            fw.write(
                    "========== FULL AUDIT LOG EXPORT"
                    + " ==========\n");
            fw.write(
                    "Total Entries : "
                    + auditLogs.size() + "\n");
            fw.write(
                    "--------------------------------------------\n");
            for (String log : auditLogs) {
                fw.write(log + "\n");
            }
            fw.write(
                    "============================================\n");
            fw.close();

            System.out.println(
                    "[Audit] Logs exported to : "
                    + AUDIT_FILE);

            logAction(loggedInUser.getUsername(),
                    "Exported audit logs to " + AUDIT_FILE);

        } catch (IOException e) {
            System.out.println(
                    "[AuditService Error] exportLogsToFile : "
                    + e.getMessage());
        }
    }

    // 6. Clear All Logs  by  ADMIN only
    public void clearAllLogs(User loggedInUser) {
        try {
            if (!isLoggedIn(loggedInUser)) {
                return;
            }
            if (!hasRole(loggedInUser, "ADMIN")) {
                return;
            }

            int count = auditLogs.size();
            auditLogs.clear();

            String timestamp = LocalDateTime.now()
                    .format(FORMATTER);
            String clearEntry
                    = "[" + timestamp + "] "
                    + "USER: "
                    + loggedInUser.getUsername()
                            .toUpperCase()
                    + " | ACTION: Cleared all audit logs ("
                    + count + " entries removed)";

            auditLogs.add(clearEntry);

            FileWriter fw = new FileWriter(
                    AUDIT_FILE, false);
            fw.write(clearEntry + "\n");
            fw.close();

            System.out.println(
                    "[Audit] All logs cleared by admin: "
                    + loggedInUser.getUsername());

        } catch (Exception e) {
            System.out.println(
                    "[AuditService Error] clearAllLogs : "
                    + e.getMessage());
        }
    }

    public int getTotalLogCount() {
        return auditLogs.size();
    }

    // Get All Logs
    // Called by BackupService not properly ......
    public List<String> getAllLogs() {
        return new ArrayList<>(auditLogs);
    }

    // Private Helper
    private void appendToFile(String logEntry) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Append mode
            FileWriter fw = new FileWriter(
                    AUDIT_FILE, true);
            fw.write(logEntry + "\n");
            fw.close();

        } catch (IOException e) {
            // Silent fail — log saved in memory
            System.out.println(
                    "[AuditService Warning] "
                    + "Could not write to audit file: "
                    + e.getMessage());
        }
    }
}
