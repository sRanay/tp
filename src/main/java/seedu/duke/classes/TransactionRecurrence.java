package seedu.duke.classes;

import java.time.LocalDate;

public enum TransactionRecurrence {
    NONE, DAILY, WEEKLY, MONTHLY;

    public static TransactionRecurrence getRecurrence(String recurrence) {
        assert recurrence != null;
        String cleanedRecurrence = recurrence.strip().toLowerCase();
        switch (cleanedRecurrence) {
        case "none":
            return TransactionRecurrence.NONE;
        case "daily":
            return TransactionRecurrence.DAILY;
        case "weekly":
            return TransactionRecurrence.WEEKLY;
        case "monthly":
            return TransactionRecurrence.MONTHLY;
        default:
            return null;
        }
    }

    public static LocalDate getNextRecurrenceDate(TransactionRecurrence recurrence, LocalDate current) {
        switch (recurrence) {
        case DAILY:
            return current.plusDays(1);
        case WEEKLY:
            return current.plusWeeks(1);
        case MONTHLY:
            return current.plusMonths(1);
        default:
            return current;
        }
    }
}
