package sn.niit.expense_tracker.domain;

public class User {

    private int id;
    private String username;
    private String accessCode;
    private double currentBalance = 0;
    private boolean loggedIn;

    // Constructors
    public User() {
        // Default constructor
    }

    public User(int id, String username, String accessCode, double currentBalance, boolean loggedIn) {
        this.id = id;
        this.username = username;
        this.accessCode = accessCode;
        this.currentBalance = currentBalance;
        this.loggedIn = loggedIn;
    }

    public User(String username, String accessCode, double currentBalance, boolean loggedIn) {
        this.username = username;
        this.accessCode = accessCode;
        this.currentBalance = currentBalance;
        this.loggedIn = loggedIn;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // Method to log the user in
    public void login() {
        this.loggedIn = true;
    }

    // Method to log the user out
    public void logout() {
        this.loggedIn = false;
    }
}

