package seedu.duke.command;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.AfterEach;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SummaryCommandTest {


    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    /**
     * Populates the StateManager with test income transactions.
     */
    private static void addInEntriesWithDates() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car /date 30102023").execute(ui);
            parser.parse("in carousell /amount 10 /goal car /date 31102023").execute(ui);
            parser.parse("in allowance /amount 300 /goal car /date 23102023").execute(ui);
            parser.parse("in red packet money /amount 150 /goal car /date 23092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Populates the StateManager with test expense transactions.
     */
    private static void addOutEntriesWithDates() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("out lunch /amount 7.50 /category food /date 30102023").execute(ui);
            parser.parse("out grocery /amount 20.80 /category food /date 31102023").execute(ui);
            parser.parse("out dinner /amount 10.50 /category food /date 23102023").execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date 18092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Test to ensure that the summary command will throw
     * Exception when called without /type.
     */
    @Test
    void execute_summaryWithoutType_throwsDukeException() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("summary");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void execute_summaryEmptyType_throwsDukeException() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("summary /type");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void execute_summaryInvalidType_throwsDukeException() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("summary /type invalid");
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void execute_incomeSummary_printTotalIncome() throws DukeException {
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("summary /type in");
        command.execute(ui);
        assertEquals("Good job! Total income so far: $960.00\n", outputStream.toString());
    }

    @Test
    void execute_incomeSummaryByDay_printTotalIncomeByDay() throws DukeException {
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "in");
                put("day", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Good job! Total income so far for Today: $10.00\n", outputStream.toString());
    }

    @Test
    void execute_incomeSummaryByWeek_printTotalIncomeByWeek() throws DukeException {
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "in");
                put("week", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Good job! Total income so far for This Week: $510.00\n", outputStream.toString());
    }

    @Test
    void execute_incomeSummaryByMonth_printTotalIncomeByMonth() throws DukeException {
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "in");
                put("month", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Good job! Total income so far for This Month: $810.00\n", outputStream.toString());
    }

    /**
     * Test to ensure that if multiple filters are indicated,
     * filter by day will take priority.
     */
    @Test
    void execute_incomeSummaryByDayWeekMonth_printTotalIncomeByDay() throws DukeException {
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "in");
                put("day", "");
                put("week", "");
                put("month", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Good job! Total income so far for Today: $10.00\n", outputStream.toString());
    }

    @Test
    void execute_expenseSummary_printTotalExpense() throws DukeException {
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("summary /type out");
        command.execute(ui);
        assertEquals("Wise spending! Total expense so far: $49.30\n", outputStream.toString());
    }

    @Test
    void execute_expenseSummaryByDay_printTotalExpenseByDay() throws DukeException {
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "out");
                put("day", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Wise spending! Total expense so far for Today: $20.80\n", outputStream.toString());
    }

    @Test
    void execute_expenseSummaryByWeek_printTotalExpenseByWeek() throws DukeException {
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "out");
                put("week", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Wise spending! Total expense so far for This Week: $28.30\n", outputStream.toString());
    }

    @Test
    void execute_expenseSummaryByMonth_printTotalExpenseByMonth() throws DukeException {
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "out");
                put("month", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Wise spending! Total expense so far for This Month: $38.80\n", outputStream.toString());
    }

    /**
     * Test to ensure that if multiple filters are indicated,
     * filter by day will take priority.
     */
    @Test
    void execute_expenseSummaryByDayWeekMonth_printTotalExpenseByDay() throws DukeException {
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        LocalDate currentDate = LocalDate.of(2023, 10, 31);
        String description = "";
        HashMap<String, String> argMaps = new HashMap<>() {{
                put("type", "out");
                put("day", "");
                put("week", "");
                put("month", "");
            }};
        Command command = new SummaryCommand(description, argMaps, currentDate);
        command.execute(ui);
        assertEquals("Wise spending! Total expense so far for Today: $20.80\n", outputStream.toString());
    }

}
