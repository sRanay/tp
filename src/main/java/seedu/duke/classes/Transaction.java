package seedu.duke.classes;

import java.time.LocalDate;

public class Transaction {
    private String description;
    private Double amount;
    private LocalDate date;
    private TransactionRecurrence recurrence;
    private boolean hasGeneratedNextRecurrence = false;

    public Transaction(String description, Double amount, LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.recurrence = TransactionRecurrence.NONE;
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

    public TransactionRecurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(TransactionRecurrence recurrence) {
        this.recurrence = recurrence;
    }

    public boolean getHasGeneratedNextRecurrence() {
        return hasGeneratedNextRecurrence;
    }

    public void setHasGeneratedNextRecurrence(boolean hasGeneratedNextRecurrence) {
        this.hasGeneratedNextRecurrence = hasGeneratedNextRecurrence;
    }

    public boolean shouldGenerateNextRecurrence() {
        if (getRecurrence() == TransactionRecurrence.NONE || getHasGeneratedNextRecurrence()) {
            return false;
        }

        return !TransactionRecurrence.getNextRecurrenceDate(getRecurrence(), getDate()).isAfter(LocalDate.now());
    }

    public Transaction generateNextRecurrence() {
        if (!shouldGenerateNextRecurrence()) {
            return null;
        }

        LocalDate nextDate = TransactionRecurrence.getNextRecurrenceDate(getRecurrence(), getDate());
        Transaction nextTransaction = new Transaction(getDescription(), getAmount(), nextDate);
        nextTransaction.setRecurrence(getRecurrence());
        return nextTransaction;
    }
}
