package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;

import java.util.HashMap;

public abstract class AddTransactionCommand extends Command {
    private static final String AMOUNT_ARG = "amount";
    private static final String MISSING_DESC = "Description cannot be empty...";
    private static final String MISSING_AMOUNT = "Amount cannot be empty...";
    private static final String BAD_AMOUNT = "Invalid amount value specified...";

    public AddTransactionCommand(String description, HashMap<String, String> args) {
        super(description, args);
    }

    protected void throwIfInvalidDescOrArgs(String classification, String missingClassification) throws DukeException {
        // TODO:
        //  Ensure category is non-null - after V1.0
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

        String category = getArg(classification);
        if (category == null || category.isBlank()) {
            throw new DukeException(missingClassification);
        }
    }
}
