package seedu.duke.parser;

import seedu.duke.command.AddExpenseCommand;
import seedu.duke.command.AddIncomeCommand;
import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.DukeException;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private static final String SPACE_WITH_ARG_PREFIX = " /";
    private static final String ARG_PREFIX = "/";
    private static final String DELIM = " ";
    private static final String EMPTY_STRING = "";

    public Parser() {
    }

    public Command parse(String userInput) throws DukeException {
        String trimmedInput = userInput.trim();

        String commandWord = getCommandWord(trimmedInput);
        String description = getDescription(trimmedInput);
        HashMap<String, String> argsMap = getArguments(trimmedInput);

        return getCommand(commandWord, description, argsMap);
    }

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
        default:
            throw new DukeException("Sorry I do not understand your command");
        }
    }

    public String getCommandWord(String userInput) {
        return userInput.split(DELIM, 2)[0].toLowerCase();
    }

    public String getDescription(String userInput) {
        String[] splitInput = userInput.split(DELIM, 2);
        if (splitInput.length <= 1) {
            return EMPTY_STRING;
        }
        String description = splitInput[1].split(SPACE_WITH_ARG_PREFIX, 2)[0].trim();
        if (description.startsWith(ARG_PREFIX)) {
            return EMPTY_STRING;
        }
        return description;
    }

    public HashMap<String, String> getArguments(String userInput) {
        String[] splitInput = userInput.split(SPACE_WITH_ARG_PREFIX, 2);
        HashMap<String, String> argsMap = new HashMap<>();
        if (splitInput.length <= 1) {
            return argsMap;
        }
        String[] spitArgs = splitInput[1].split(DELIM);

        String argName = spitArgs[0];
        ArrayList<String> currentWords = new ArrayList<>();
        for (int i = 1; i < spitArgs.length; i++) {
            String word = spitArgs[i];
            if (word.startsWith(ARG_PREFIX)) {
                String argValue = convertArgValueListToString(currentWords);
                argsMap.put(argName, argValue);
                argName = word.substring(1);
                currentWords.clear();
            } else {
                currentWords.add(word);
            }
        }
        if (!currentWords.isEmpty()) {
            String argValue = convertArgValueListToString(currentWords);
            argsMap.put(argName, argValue);
        }
        return argsMap;
    }

    public String convertArgValueListToString(ArrayList<String> argValues) {
        return String.join(DELIM, argValues).trim();
    }

    public static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseNonNegativeDouble(String value) {
        Double parsedValue = parseDouble(value);
        if (parsedValue == null || parsedValue < 0) {
            return null;
        }

        return parsedValue;
    }
}
