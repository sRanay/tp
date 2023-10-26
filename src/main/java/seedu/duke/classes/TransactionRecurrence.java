package seedu.duke.classes;

import java.time.LocalDate;

public enum TransactionRecurrence {
    NONE, DAILY, WEEKLY, MONTHLY;

    private static final String NONE_STR = "none";
    private static final String DAILY_STR = "daily";
    private static final String WEEKLY_STR = "weekly";
    private static final String MONTHLY_STR = "monthly";

    public static TransactionRecurrence getRecurrence(String recurrence) {
        assert recurrence != null;
        String cleanedRecurrence = recurrence.strip().toLowerCase();
        switch (cleanedRecurrence) {
        case NONE_STR:
            return TransactionRecurrence.NONE;
        case DAILY_STR:
            return TransactionRecurrence.DAILY;
        case WEEKLY_STR:
            return TransactionRecurrence.WEEKLY;
        case MONTHLY_STR:
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

    @Override
    public String toString() {
        switch (this) {
        case NONE:
            return NONE_STR;
        case DAILY:
            return DAILY_STR;
        case WEEKLY:
            return WEEKLY_STR;
        case MONTHLY:
            return MONTHLY_STR;
        default:
            return null;
        }
    }
}
