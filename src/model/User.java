package model;

import java.time.LocalDateTime;

public abstract class User {

    protected int userId;
    protected String fullName;
    protected String email;
    protected String password;

    protected String username;
    protected boolean loggedIn = false;

    // Track failed logins and lockouts (UC-1.1)
    private int failedLoginAttempts = 0;
    private LocalDateTime lockoutUntil = null;
    private String status = "ACTIVE";

    public User() {
    }

    public User(int userId,
                String fullName,
                String email,
                String password) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;

    }

    public boolean isLockedOut() {
        return lockoutUntil != null && lockoutUntil.isAfter(LocalDateTime.now());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getLockoutUntil() {
        return lockoutUntil;
    }

    public void setLockoutUntil(LocalDateTime lockoutUntil) {
        this.lockoutUntil = lockoutUntil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void login() {
        this.loggedIn = true;
    }

    public void logout() {
        this.loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}