package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;

import java.util.HashMap;

public abstract class AddTransactionCommand extends Command {
    protected static final String AMOUNT_ARG = "amount";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";

    public AddTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    protected void throwIfInvalidDescOrArgs(String classificationKey, String missingClassificationPrompt) throws DukeException {
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
    }
}
