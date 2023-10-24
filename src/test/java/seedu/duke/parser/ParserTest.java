package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddIncomeCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParserTest {

    @Test
    void parse_validIncome_incomeCommand() throws DukeException {
        Parser parser = new Parser();
        parser.parse("goal /add car /amount 5000");
        Command command = parser.parse("in job /amount 100 /goal car");
        assertEquals(AddIncomeCommand.class, command.getClass());
    }

    @Test
    void parse_invalidCommand_exceptionThrown() {
        Parser parser = new Parser();
        assertThrows(DukeException.class, () -> {
            Command command = parser.parse("invalid command");
        });
    }

    @Test
    void getCommandWord_validCommand_commandInLowerCase() {
        Parser parser = new Parser();
        assertEquals("delete", parser.getCommandWord("Delete 1 /type in"));
    }

    @Test
    void getDescription_validDescription_trimmedDescription() {
        Parser parser = new Parser();
        assertEquals("part time job",
                parser.getDescription("in   part time job   /amount 100 /goal car"));
    }

    @Test
    void getDescription_emptyDescription_emptyString() {
        Parser parser = new Parser();
        assertEquals("",
                parser.getDescription("in"));
    }

    @Test
    void getDescription_emptyDescriptionWithArguments_emptyString() {
        Parser parser = new Parser();
        assertEquals("",
                parser.getDescription("in /amount 100 /goal car"));
    }

    @Test
    void getArguments_validArguments_hashmapOfArguments() {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount 100 /goal car");
        assertEquals("100", args.get("amount"));
        assertEquals("car", args.get("goal"));
    }

    @Test
    void getArguments_noArgument_emptyHashMap() {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job");
        assertEquals(0, args.size());
    }

    @Test
    void getArguments_argumentWithoutValue_nullValue() {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount /goal");
        assertNull(args.get("amount"));
        assertNull(args.get("goal"));
    }

}
