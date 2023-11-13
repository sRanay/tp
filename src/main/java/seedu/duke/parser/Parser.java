package seedu.duke.parser;

import seedu.duke.command.AddExpenseCommand;
import seedu.duke.command.AddIncomeCommand;
import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.RemoveTransactionCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.ExportCommand;
import seedu.duke.command.CategoryCommand;
import seedu.duke.command.GoalCommand;
import seedu.duke.command.SummaryCommand;
import seedu.duke.command.EditTransactionCommand;
import seedu.duke.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;


public class Parser {
    public static final String DATE_INPUT_PATTERN = "ddMMyyyy";
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN);
    private static final String SPACE_WITH_ARG_PREFIX = " /";
    private static final String ARG_PREFIX = "/";
    private static final String DELIM = " ";
    private static final String EMPTY_STRING = "";
    private static final Pattern DBL_POS_PATTERN = Pattern.compile("^\\d*.?\\d{0,2}$");
    private static final Double DBL_POS_ZERO = 0.0;
    private static final Double DBL_TEN_MILLION = 10_000_000.0;
    private static final int SPLIT_LIMIT = 2;

    private static final String DUPLICATE_KEY_MSG = "Duplicate arguments detected. Refer to help for command usage.";

    public Parser() {
    }


    /**
     * Parses user input into command, description and arguments hashmap.
     *
     * @param userInput the user input.
     * @return Command to be executed.
     * @throws DukeException if invalid command is supplied.
     */
    public Command parse(String userInput) throws DukeException {
        String trimmedInput = userInput.trim();

        String commandWord = getCommandWord(trimmedInput);
        String description = getDescription(trimmedInput);
        HashMap<String, String> argsMap = getArguments(trimmedInput);

        return getCommand(commandWord, description, argsMap);
    }

    /**
     * Instantiates the Command with description and hashmap of arguments.
     *
     * @param commandWord the command word
     * @param description the description
     * @param argsMap     hashmap of arguments
     * @return Command object
     * @throws DukeException if invalid command is supplied.
     */
    public Command getCommand(String commandWord, String description,
                              HashMap<String, String> argsMap) throws DukeException {
        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "in":
            return new AddIncomeCommand(description, argsMap);
        case "out":
            return new AddExpenseCommand(description, argsMap);
        case "list":
            return new ListCommand(description, argsMap);
        case "delete":
            return new RemoveTransactionCommand(description, argsMap);
        case "help":
            return new HelpCommand(description, argsMap);
        case "export":
            return new ExportCommand(description, argsMap);
        case "category":
            return new CategoryCommand(description, argsMap);
        case "goal":
            return new GoalCommand(description, argsMap);
        case "summary":
            return new SummaryCommand(description, argsMap);
        case "edit":
            return new EditTransactionCommand(description, argsMap);
        default:
            throw new DukeException("Sorry I do not understand your command");
        }
    }

    /**
     * Splits the user input and returns the command word.
     *
     * @param userInput the user input.
     * @return String the command word.
     */
    public String getCommandWord(String userInput) {
        return userInput.split(DELIM, SPLIT_LIMIT)[0].toLowerCase();
    }

    /**
     * Splits the user input and returns the description.
     * Returns empty string if no description is found.
     *
     * @param userInput the user input.
     * @return String the description.
     */
    public String getDescription(String userInput) {
        String[] splitInput = userInput.split(DELIM, SPLIT_LIMIT);
        if (splitInput.length <= 1) {
            return EMPTY_STRING;
        }
        String description = splitInput[1].split(SPACE_WITH_ARG_PREFIX, SPLIT_LIMIT)[0].trim();
        if (description.startsWith(ARG_PREFIX)) {
            return EMPTY_STRING;
        }
        return description;
    }

    /**
     * Returns a hashmap of arguments from the user input.
     * If no argument is supplied, empty hashmap will be returned.
     *
     * @param userInput the user input.
     * @return HashMap of arguments.
     * @throws DukeException if duplicate arguments exist.
     */
    public HashMap<String, String> getArguments(String userInput) throws DukeException {
        String[] splitInput = userInput.split(SPACE_WITH_ARG_PREFIX, SPLIT_LIMIT);
        HashMap<String, String> argsMap = new HashMap<>();
        if (splitInput.length <= 1) {
            return argsMap;
        }
        String[] spitArgs = splitInput[1].split(DELIM);

        String argName = spitArgs[0];
        ArrayList<String> currentWords = new ArrayList<>();
        boolean hasArgValue = false;
        for (int i = 1; i < spitArgs.length; i++) {
            String word = spitArgs[i];
            if (word.startsWith(ARG_PREFIX)) {
                checkIfKeyExist(argName, argsMap);
                String argValue = convertArgValueListToString(currentWords);
                argsMap.put(argName, argValue);
                argName = word.substring(1);
                currentWords.clear();
                hasArgValue = false;
            } else {
                currentWords.add(word);
                hasArgValue = true;
            }
        }
        if (!currentWords.isEmpty() || !argsMap.containsKey(argName) || !hasArgValue) {
            checkIfKeyExist(argName, argsMap);
            String argValue = convertArgValueListToString(currentWords);
            argsMap.put(argName, argValue);
        }
        return argsMap;
    }

    /**
     * Converts arraylist of argument values to String separated
     * by whitespace.
     *
     * @param argValues arraylist of argument values.
     * @return String value.
     */
    public String convertArgValueListToString(ArrayList<String> argValues) {
        if (argValues.isEmpty()) {
            return EMPTY_STRING;
        }
        return String.join(DELIM, argValues).trim();
    }

    /**
     * Checks if argument already exist.
     *
     * @param argName argument name.
     * @param argsMap hashmap of arguments.
     * @throws DukeException if the argument exists.
     */
    public static void checkIfKeyExist(String argName, HashMap<String, String> argsMap) throws DukeException {
        if (argsMap.containsKey(argName)) {
            throw new DukeException(DUPLICATE_KEY_MSG);
        }
    }

    /**
     * Parses a double from string
     * @param value String to be parsed
     * @return parsed value if valid otherwise {@code null}
     */
    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parses a double and ensures that the value is not negative and not larger than
     * or equal to ten million. Additionally, enforces input only has at most 2 decimal
     * places.
     * @param value String to be parsed
     * @return parsed value if valid otherwise {@code null}
     */
    public static Double parseNonNegativeDouble(String value) {
        Double parsedValue = parseDouble(value);
        if (parsedValue == null
                || !DBL_POS_PATTERN.matcher(value).matches()
                || parsedValue.compareTo(DBL_POS_ZERO) < 0
                || parsedValue.compareTo(DBL_TEN_MILLION) >= 0
        ) {
            return null;
        }

        return parsedValue;
    }

    /**
     * Parses a date (in {@value DATE_INPUT_PATTERN} format) from string
     * @param value Date string to be parsed
     * @return LocalDate value if valid otherwise {@code null}
     */
    public static LocalDate parseDate(String value) {
        if (value == null) {
            return null;
        }

        try {
            return LocalDate.parse(value, DATE_INPUT_FORMATTER);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

}
