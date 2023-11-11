package seedu.duke.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.duke.classes.StateManager;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListCommandTest {
    private static Parser parser = new Parser();
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static Ui ui = new Ui(outputStream);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    @Test
    void invalidList() throws DukeException {
        String userInput = "list";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void emptyListDescription() throws DukeException {
        String userInput = "list ";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void invalidListDescription_argumentExists() throws DukeException {
        String userInput = "list goal /type in";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }
    @Test
    void invalidArgument() throws DukeException{
        String userInput = "list /goal ABC";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }
    @Test
    void invalidListType() throws DukeException {
        String userInput = "list /type ABC";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void blankGoal() throws DukeException {
        String userInput = "list /type in /goal ";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }
    @Test
    void invalidGoal() throws DukeException {
        String userInput = "list /type in /goal ABC";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void blankCategory() throws DukeException {
        String userInput = "list /type out /category ";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void invalidCategory() throws DukeException {
        String userInput = "list /type out /category DEF";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    @Test
    void invalidCategoryGoal() throws DukeException {
        String userInput = "list /type in /goal ABC /category DEF";
        Command command = parser.parse(userInput);
        assertThrows(DukeException.class, () -> command.execute(ui));
    }

    private static void addInEntries() {
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("goal /add PS5 /amount 300").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car").execute(ui);
            parser.parse("in red packet money /amount 50 /goal PS5 /date 18092023").execute(ui);
            outputStream.reset();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addOutEntries() {
        try {
            parser.parse("out dinner /amount 10.50 /category food").execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date 18092023").execute(ui);
            outputStream.reset();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addInEntriesWithDates() {
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car /date " +
                    getFormattedCurrentDate()).execute(ui);
            parser.parse("in allowance job /amount 300 /goal car /date " +
                    getFormattedPrevWeekDate()).execute(ui);
            parser.parse("in red packet money /amount 150 /goal car /date " +
                    getFormattedPrevMonthDate()).execute(ui);
            outputStream.reset();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void addOutEntriesWithDates() {
        try {
            parser.parse("out lunch /amount 7.50 /category food /date " +
                    getFormattedCurrentDate()).execute(ui);
            parser.parse("out dinner /amount 10.50 /category food /date " +
                    getFormattedPrevWeekDate()).execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date " +
                    getFormattedPrevMonthDate()).execute(ui);
            outputStream.reset();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getFormattedCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DATE_FORMATTER);
    }

    private static String getFormattedPrevWeekDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate prevWeek = currentDate.minusDays(7);
        return prevWeek.format(DATE_FORMATTER);
    }

    private static String getFormattedPrevMonthDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate prevMonth = currentDate.minusMonths(1);
        return prevMonth.format(DATE_FORMATTER);
    }

    private static LocalDate getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }

    private static LocalDate getPrevWeekDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate prevWeek = currentDate.minusDays(7);
        return prevWeek;
    }


    public static boolean isInSameMonth(LocalDate date1, LocalDate date2) {
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue();
    }


    @Test
    void validInList() throws DukeException {
        addInEntries();
        LocalDate currentDate = LocalDate.now();
        Command command = parser.parse("list /type in");
        command.execute(ui);
        assertEquals("Alright! Displaying 2 transactions.\n" +
                        "=========================================== IN TRANSACTIONS ============================" +
                        "===============\n" +
                        "ID    Description                      Date         Amount       Goal                   " +
                        "Recurrence\n" +
                        "1     part-time job                    "+currentDate+"   500.00       car                   " +
                        " " +
                        "none\n" +
                        "2     red packet money                 2023-09-18   50.00        PS5                    " +
                        "none\n" +
                        "=========================================== IN TRANSACTIONS =============================" +
                        "==============\n"
                , outputStream.toString());

    }

    @Test
    void validFilteredInList() throws DukeException {
        addInEntries();
        LocalDate currentDate = LocalDate.now();
        Command command = parser.parse("list /type in /goal car");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "=========================================== IN TRANSACTIONS ============================" +
                        "===============\n" +
                        "ID    Description                      Date         Amount       Goal                   " +
                        "Recurrence\n" +
                        "1     part-time job                    "+currentDate+"   500.00       car                   "+
                        " " +
                        "none\n" +
                        "=========================================== IN TRANSACTIONS ============================" +
                        "===============\n"
                , outputStream.toString());

    }

    @Test
    void validOutList() throws DukeException {
        addOutEntries();
        LocalDate currentDate = LocalDate.now();
        Command command = parser.parse("list /type out");
        command.execute(ui);
        assertEquals("Alright! Displaying 2 transactions.\n" +
                        "========================================== OUT TRANSACTIONS ===========================" +
                        "================\n" +
                        "ID    Description                      Date         Amount       Category               " +
                        "Recurrence\n" +
                        "1     dinner                           "+currentDate+"   10.50        food                  " +
                        " " +
                        "none\n" +
                        "2     pokemon card pack                2023-09-18   10.50        games                  " +
                        "none\n" +
                        "========================================== OUT TRANSACTIONS ============================" +
                        "===============\n"
                , outputStream.toString());

    }

    @Test
    void validFilteredOutList() throws DukeException {
        addOutEntries();
        Command command = parser.parse("list /type out /category games");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "========================================== OUT TRANSACTIONS ==========================" +
                        "=================\n" +
                        "ID    Description                      Date         Amount       Category               " +
                        "Recurrence\n" +
                        "1     pokemon card pack                2023-09-18   10.50        games                  " +
                        "none\n" +
                        "========================================== OUT TRANSACTIONS ============================" +
                        "===============\n"
                , outputStream.toString());

    }

    @Test
    void execute_listIncomeByWeek_printCurrentWeekTransactions() throws DukeException {
        addInEntriesWithDates();
        Command command = parser.parse("list /type in /week");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "=========================================== IN TRANSACTIONS ==========================" +
                        "=================\n" +
                        "ID    Description                      Date         Amount       Goal                   " +
                        "Recurrence\n" +
                        "1     part-time job                    "+getCurrentDate()+"   500.00       car              " +
                        "      " +
                        "none\n" +
                        "=========================================== IN TRANSACTIONS ===========================" +
                        "================\n"
                , outputStream.toString());
    }

    @Test
    void execute_listExpenseByWeek_printCurrentWeekTransactions() throws DukeException {
        addOutEntriesWithDates();
        Command command = parser.parse("list /type out /week");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "========================================== OUT TRANSACTIONS ============================" +
                        "===============\n" +
                        "ID    Description                      Date         Amount       Category               " +
                        "Recurrence\n" +
                        "1     lunch                            "+getCurrentDate()+"   7.50         food             " +
                        "      " +
                        "none\n" +
                        "========================================== OUT TRANSACTIONS ============================" +
                        "===============\n"
                , outputStream.toString());
    }

    @Test
    void execute_listIncomeByMonth_printCurrentMonthTransactions() throws DukeException {
        addInEntriesWithDates();
        Command command = parser.parse("list /type in /month");
        command.execute(ui);
        if (isInSameMonth(getCurrentDate(), getPrevWeekDate())) {
            assertEquals("Alright! Displaying 2 transactions.\n" +
                            "=========================================== IN TRANSACTIONS ============================" +
                            "===============\n" +
                            "ID    Description                      Date         Amount       Goal                   " +
                            "Recurrence\n" +
                            "1     part-time job                    "+getCurrentDate()+"   500.00       car          " +
                            "          none\n" +
                            "2     allowance job                    "+getPrevWeekDate()+"   300.00       car         " +
                            "           none\n" +
                            "=========================================== IN TRANSACTIONS ===========================" +
                            "================\n"
                    , outputStream.toString());
        } else {
            assertEquals("Alright! Displaying 1 transaction.\n" +
                            "=========================================== IN TRANSACTIONS ============================" +
                            "===============\n" +
                            "ID    Description                      Date         Amount       Goal                   " +
                            "Recurrence\n" +
                            "1     part-time job                    "+getCurrentDate()+"   500.00       car          " +
                            "          none\n" +
                            "=========================================== IN TRANSACTIONS ===========================" +
                            "================\n"
                    , outputStream.toString());
        }
    }

    @Test
    void execute_listExpenseByMonth_printCurrentMonthTransactions() throws DukeException {
        addOutEntriesWithDates();
        Command command = parser.parse("list /type out /month");
        command.execute(ui);
        if (isInSameMonth(getCurrentDate(), getPrevWeekDate())) {
            assertEquals("Alright! Displaying 2 transactions.\n" +
                            "========================================== OUT TRANSACTIONS ============================" +
                            "===============\n" +
                            "ID    Description                      Date         Amount       Category               " +
                            "Recurrence\n" +
                            "1     lunch                            "+getCurrentDate()+"   7.50         food         " +
                            "          " +
                            "none\n" +
                            "2     dinner                           "+getPrevWeekDate()+"   10.50        food        " +
                            "           none\n" +
                            "========================================== OUT TRANSACTIONS ============================" +
                            "===============\n"
                    , outputStream.toString());
        } else {
            assertEquals("Alright! Displaying 1 transaction.\n" +
                            "========================================== OUT TRANSACTIONS ===========================" +
                            "================\n" +
                            "ID    Description                      Date         Amount       Category               " +
                            "Recurrence\n" +
                            "1     lunch                            "+getCurrentDate()+"   7.50         food         " +
                            "          none\n" +
                            "========================================== OUT TRANSACTIONS ===========================" +
                            "================\n"
                    , outputStream.toString());
        }
    }

    @Test
    void execute_listIncomeByWeekAndMonth_printCurrentWeekTransactions() throws DukeException {
        addInEntriesWithDates();
        Command command = parser.parse("list /type in /week /month");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "=========================================== IN TRANSACTIONS ============================" +
                        "===============\n" +
                        "ID    Description                      Date         Amount       Goal                   " +
                        "Recurrence\n" +
                        "1     part-time job                    "+getCurrentDate()+"   500.00       car          " +
                        "          none\n" +
                        "=========================================== IN TRANSACTIONS ===========================" +
                        "================\n"
                , outputStream.toString());
    }

    @Test
    void execute_listExpenseByWeekAndMonth_printCurrentWeekTransactions() throws DukeException {
        addOutEntriesWithDates();
        Command command = parser.parse("list /type out /week /month");
        command.execute(ui);
        assertEquals("Alright! Displaying 1 transaction.\n" +
                        "========================================== OUT TRANSACTIONS ===========================" +
                        "================\n" +
                        "ID    Description                      Date         Amount       Category               " +
                        "Recurrence\n" +
                        "1     lunch                            "+getCurrentDate()+"   7.50         food         " +
                        "          none\n" +
                        "========================================== OUT TRANSACTIONS ===========================" +
                        "================\n"
                , outputStream.toString());
    }

    @Test
    void execute_listGoalStatus() throws DukeException {
        addInEntries();
        Command command = parser.parse("list goal");
        command.execute(ui);
        assertEquals("==================================== Goals Status ======================" +
                        "==============\n" +
                "Name                   Amount                 Progress\n" +
                "PS5                    50.00/300.00           [===                 ] 16.67%\n" +
                "car                    500.00/5000.00         [==                  ] 10.00%\n" +
                "==================================== Goals Status ====================================\n"
                , outputStream.toString());
    }

    @Test
    void execute_listCategoryStatus() throws DukeException {
        addOutEntries();
        Command command = parser.parse("list category");
        command.execute(ui);
        assertEquals("============== Categories Status ===============\n" +
                "Name                   Amount\n" +
                "food                   10.50\n" +
                "games                  10.50\n" +
                "============== Categories Status ===============\n", outputStream.toString());
    }

}
