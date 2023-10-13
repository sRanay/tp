package seedu.duke.classes;

import java.time.LocalDate;

public class Transaction {
    private String description;
    private int amount;
    private LocalDate date;

    public Transaction(String description, int amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
