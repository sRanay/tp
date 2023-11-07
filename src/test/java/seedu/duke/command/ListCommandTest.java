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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    @AfterEach
    void clearStateManager() {
        StateManager.clearStateManager();
    }

    @Test
    void invalidList() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void invalidListType() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list /type abc";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void invalidGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list /type in /goal ABC";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void invalidCategory() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list /type out /category DEF";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    @Test
    void invalidCategoryGoal() throws DukeException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        String userInput = "list /type in /goal ABC /category DEF";
        HashMap<String, String> args = parser.getArguments(userInput);
        String commandWord = parser.getDescription(userInput);
        ListCommand command = new ListCommand(commandWord, args);
        assertThrows(DukeException.class, () -> {
            command.execute(ui);
        });
    }

    private static void addInEntries() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("goal /add PS5 /amount 300").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car").execute(ui);
            parser.parse("in red packet money /amount 50 /goal PS5 /date 18092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addOutEntries() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("out dinner /amount 10.50 /category food").execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date 18092023").execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void addInEntriesWithDates() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("goal /add car /amount 5000").execute(ui);
            parser.parse("in part-time job /amount 500 /goal car /date " +
                    getFormattedCurrentDate()).execute(ui);
            parser.parse("in allowance job /amount 300 /goal car /date " +
                    getFormattedPrevWeekDate()).execute(ui);
            parser.parse("in red packet money /amount 150 /goal car /date " +
                    getFormattedPrevMonthDate()).execute(ui);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void addOutEntriesWithDates() {
        Parser parser = new Parser();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        try {
            parser.parse("out lunch /amount 7.50 /category food /date " +
                    getFormattedCurrentDate()).execute(ui);
            parser.parse("out dinner /amount 10.50 /category food /date " +
                    getFormattedPrevWeekDate()).execute(ui);
            parser.parse("out pokemon card pack /amount 10.50 /category games /date " +
                    getFormattedPrevMonthDate()).execute(ui);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        clearStateManager();
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        clearStateManager();
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        clearStateManager();
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("list /type in /month");
        command.execute(ui);
        if (isInSameMonth(getCurrentDate(), getPrevWeekDate())) {
            assertEquals("Alright! Displaying 1 transaction.\n" +
                            "=========================================== IN TRANSACTIONS ============================" +
                            "===============\n" +
                            "ID    Description                      Date         Amount       Goal                   " +
                            "Recurrence\n" +
                            "1     part-time job                    "+getCurrentDate()+"   500.00       car          " +
                            "          none\n" +
                            "2     allowance job                    "+getPrevWeekDate()+"   500.00       car         " +
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
        clearStateManager();
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
        Command command = parser.parse("list /type out /month");
        command.execute(ui);
        if (isInSameMonth(getCurrentDate(), getPrevWeekDate())) {
            assertEquals("Alright! Displaying 1 transaction.\n" +
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
        clearStateManager();
        addInEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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
        clearStateManager();
        addOutEntriesWithDates();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Parser parser = new Parser();
        Ui ui = new Ui(outputStream);
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

}
