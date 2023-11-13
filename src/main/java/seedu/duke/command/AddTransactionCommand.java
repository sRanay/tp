package seedu.duke.command;

import seedu.duke.classes.Transaction;
import seedu.duke.classes.TransactionRecurrence;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class AddTransactionCommand extends Command {
    protected static final Integer[] HEADERS_WIDTH = {
        Ui.LIST_COLUMN_WIDTH, Ui.COLUMN_WIDTH, Ui.COLUMN_WIDTH, Ui.TYPE_WIDTH, Ui.COLUMN_WIDTH
    };
    protected static final String AMOUNT_ARG = "amount";
    protected static final String DATE_ARG = "date";
    protected static final String RECURRENCE_ARG = "recurrence";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";
    private static final String BAD_DATE = "Invalid date specified...";
    private static final String BAD_RECURRENCE = "Invalid recurrence period specified...";
    private static final String BAD_RECURRENCE_DATE = "Cannot specify date for recurring transaction" +
            " to be larger than 1 period in the past...";
    private boolean isValidated = false;

    protected AddTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    /**
     * Prepares a transaction object based on user input.
     * @return Transaction object to be added to either Expense or Income
     */
    protected Transaction prepareTransaction() {
        assert isValidated;

        String description = getDescription();
        Double amount = Parser.parseNonNegativeDouble(getArg(AMOUNT_ARG));
        LocalDate date = Parser.parseDate(getArg(DATE_ARG));
        Transaction transaction = new Transaction(description, amount, date);

        String recurrenceValue = getArg(RECURRENCE_ARG);
        if (recurrenceValue != null) {
            TransactionRecurrence recurrence = TransactionRecurrence.getRecurrence(recurrenceValue);
            transaction.setRecurrence(recurrence);
        }
        return transaction;
    }

    /**
     * Validates user input
     * @throws DukeException if user input is invalid
     */
    protected void throwIfInvalidDescOrArgs()
            throws DukeException {
        assert getDescription() != null;
        assert getArgs() != null;
        throwIfEmptyDesc();
        throwIfInvalidAmount();
        throwIfInvalidDate();
        throwIfInvalidRecurrence();
        isValidated = true;
    }

    /**
     * Throws error if user does not enter input in description field
     * @throws DukeException if user does not enter any input in description
     */
    private void throwIfEmptyDesc() throws DukeException {
        if (getDescription().isBlank()) {
            throw new DukeException(MISSING_DESC);
        }
    }

    /**
     * Throws an exception if /amount is invalid
     * @throws DukeException when no amount is entered or invalid amount
     */

    private void throwIfInvalidAmount() throws DukeException {
        String amountArg = getArg(AMOUNT_ARG);
        if (amountArg == null || amountArg.isBlank()) {
            throw new DukeException(MISSING_AMOUNT);
        }

        Double amount = Parser.parseNonNegativeDouble(amountArg);
        if (amount == null) {
            throw new DukeException(BAD_AMOUNT);
        }
    }

    /**
     * Throws exception if date is in an invalid format
     * @throws DukeException if user input for date is invalid
     */
    private void throwIfInvalidDate() throws DukeException {
        String date = getArg(DATE_ARG);
        LocalDate parsedDate = Parser.parseDate(date);
        if (date != null && parsedDate == null) {
            throw new DukeException(BAD_DATE);
        }
    }

    /**
     * Validate if user has entered correct input for recurrence
     * @throws DukeException if user input is incorrect
     */
    private void throwIfInvalidRecurrence() throws DukeException {
        String recurrence = getArg(RECURRENCE_ARG);
        if (recurrence == null) {
            return;
        }

        TransactionRecurrence parsedRecurrence = TransactionRecurrence.getRecurrence(recurrence);
        if (parsedRecurrence == null) {
            throw new DukeException(BAD_RECURRENCE);
        }

        String date = getArg(DATE_ARG);
        LocalDate parsedDate = Parser.parseDate(date);
        if (parsedRecurrence != TransactionRecurrence.NONE && parsedDate != null) {
            LocalDate nextDate = TransactionRecurrence.getNextRecurrenceDate(parsedRecurrence, parsedDate);
            if (!nextDate.isAfter(LocalDate.now())) {
                throw new DukeException(BAD_RECURRENCE_DATE);
            }
        }
    }
}
