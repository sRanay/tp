package seedu.duke.classes;

import java.time.LocalDate;

public class Transaction {
    private String description;
    private Double amount;
    private LocalDate date;

    public Transaction(String description, Double amount, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
