package seedu.duke.command;

import seedu.duke.classes.Transaction;
import seedu.duke.classes.TransactionRecurrence;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;

import java.util.HashMap;

public abstract class AddTransactionCommand extends Command {
    protected static final String AMOUNT_ARG = "amount";
    protected static final String RECURRENCE_ARG = "recurrence";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";
    private static final String BAD_RECURRENCE = "Invalid recurrence period specified...";
    private boolean isValidated = false;

    public AddTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    protected Transaction prepareTransaction() {
        assert isValidated;

        String description = getDescription();
        Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG));
        Transaction transaction = new Transaction(description, amount, null);

        String recurrenceValue = getArg(RECURRENCE_ARG);
        if (recurrenceValue != null) {
            TransactionRecurrence recurrence = TransactionRecurrence.getRecurrence(recurrenceValue);
            transaction.setRecurrence(recurrence);
        }

        return transaction;
    }

    protected void throwIfInvalidDescOrArgs(String classificationKey, String missingClassificationPrompt)
            throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;

        if (getDescription().isBlank()) {
            throw new DukeException(MISSING_DESC);
        }

        String amountArg = getArg(AMOUNT_ARG);
        if (amountArg == null) {
            throw new DukeException(MISSING_AMOUNT);
        }

        Double amount = Parser.parseNonNegativeDouble(amountArg);
        if (amount == null) {
            throw new DukeException(BAD_AMOUNT);
        }

        String assignedClassification = getArg(classificationKey);
        if (assignedClassification == null || assignedClassification.isBlank()) {
            throw new DukeException(missingClassificationPrompt);
        }

        String recurrence = getArg(RECURRENCE_ARG);
        if (recurrence != null && TransactionRecurrence.getRecurrence(recurrence) == null) {
            throw new DukeException(BAD_RECURRENCE);
        }

        isValidated = true;
    }
}
