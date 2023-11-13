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

    private static final String EMPTY_STRING = "";

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
        assertEquals(EMPTY_STRING,
                parser.getDescription("in /amount 100 /goal car"));
    }

    /**
     * Test to verify that the getArguments method
     * will parse the argument and values correctly.
     */
    @Test
    void getArguments_validArguments_hashmapOfArguments() throws DukeException {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount 100 /goal car");
        assertEquals("100", args.get("amount"));
        assertEquals("car", args.get("goal"));
    }

    @Test
    void getArguments_noArgument_emptyHashMap() throws DukeException {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job");
        assertEquals(0, args.size());
    }

    @Test
    void getArguments_argumentWithoutValue_emptyString() throws DukeException {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount /goal");
        assertEquals(EMPTY_STRING, args.get("amount"));
        assertEquals(EMPTY_STRING, args.get("goal"));
    }

    @Test
    void getArguments_firstArgumentWithEmptyString_emptyString() throws DukeException {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount     /goal");
        assertEquals(EMPTY_STRING, args.get("amount"));
        assertEquals(EMPTY_STRING, args.get("goal"));
    }

    @Test
    void getArguments_secondArgumentWithEmptyString_emptyString() throws DukeException {
        Parser parser = new Parser();
        HashMap<String, String> args = parser.getArguments("in part time job /amount /goal    ");
        assertEquals(EMPTY_STRING, args.get("amount"));
        assertEquals(EMPTY_STRING, args.get("goal"));
    }

    @Test
    void parseDoublePositive() {
        assertEquals(Parser.parseNonNegativeDouble("18"), 18);
        assertEquals(Parser.parseNonNegativeDouble("18."), 18);
        assertEquals(Parser.parseNonNegativeDouble("0.5"), 0.5);
        assertEquals(Parser.parseNonNegativeDouble(".5"), 0.5);
        assertEquals(Parser.parseNonNegativeDouble("18.5"), 18.5);
        assertEquals(Parser.parseNonNegativeDouble("9999999.99"), 9_999_999.99);
    }

    @Test
    void parseDoublePositiveZero() {
        assertEquals(Parser.parseNonNegativeDouble("0"), 0);
        assertEquals(Parser.parseNonNegativeDouble("0.00"), 0);
    }

    @Test
    void parseDoubleNegativeZero() {
        assertNull(Parser.parseNonNegativeDouble("-0"));
    }

    @Test
    void parseNegativeDouble() {
        assertNull(Parser.parseNonNegativeDouble("-18.5"));
        assertNull(Parser.parseNonNegativeDouble("-18."));
        assertNull(Parser.parseNonNegativeDouble("-.5"));
        assertNull(Parser.parseNonNegativeDouble("0.000"));
        assertNull(Parser.parseNonNegativeDouble("10000000"));
    }

    /**
     * Test to verify that the getArguments method
     * throws an Exception when duplicate arguments are provided.
     */
    @Test
    void getArguments_duplicateArgsWithValues_throwsDukeException() {
        Parser parser = new Parser();
        assertThrows(DukeException.class, () -> {
            parser.getArguments("in part time job /amount 100 /goal car /goal house");
        });
    }


    @Test
    void getArguments_duplicateArgsWithOneEmptyValue_throwsDukeException() {
        Parser parser = new Parser();
        assertThrows(DukeException.class, () -> {
            parser.getArguments("in part time job /amount 100 /goal car /goal");
        });
    }

    @Test
    void getArguments_duplicateArgsWithBothEmptyValue_throwsDukeException() {
        Parser parser = new Parser();
        assertThrows(DukeException.class, () -> {
            parser.getArguments("in part time job /amount 100 /goal /goal");
        });
    }
}
