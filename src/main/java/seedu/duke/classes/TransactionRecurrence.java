package seedu.duke.classes;

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
}
