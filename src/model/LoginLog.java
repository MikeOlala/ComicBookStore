package model;

import java.time.LocalDateTime;

public class LoginLog {
    private int logId;
    private String email;
    private String method;
    private String status;
    private LocalDateTime timestamp;

    public LoginLog() {}

    public LoginLog(int logId, String email, String method, String status, LocalDateTime timestamp) {
        this.logId = logId;
        this.email = email;
        this.method = method;
        this.status = status;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] LoginLog #%d - Email: \"%s\" | Method: %s | Status: %s",
                timestamp, logId, email, method, status);
    }

    // Getters and Setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
