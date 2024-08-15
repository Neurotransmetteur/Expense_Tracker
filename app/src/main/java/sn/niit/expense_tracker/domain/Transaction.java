package sn.niit.expense_tracker.domain;

public class Transaction {
    private String date;
    private String category;
    private String description;
    private double total;
    private TransactionType type;

    public enum TransactionType {
        EXPENSE,
        INCOME
    }

    public Transaction(String date, String category, String description, double total, TransactionType type) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.total = total;
        this.type = type;
    }

    // Getters and setters can be added as needed
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}

