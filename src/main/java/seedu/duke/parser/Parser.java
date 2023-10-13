package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
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
        return splitInput[1].split(SPACE_WITH_ARG_PREFIX, 2)[0].trim();
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
}
