package seedu.duke.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StorageTest {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private Storage storage;

    @BeforeEach
    void initialise() {
        storage = new Storage();
    }

    @AfterEach()
    void clearState() {
        storage = null;
    }
    @Test
    void validRowWithEmptyValues() {
        String[] row = {"TEST1", ""};
        assertEquals(false, storage.validRow(row));
        row[0] = "";
        row[1] = "TEST";
        assertEquals(false, storage.validRow(row));
    }

    @Test
    void validRowWithBlankValues() {
        String[] row = {"TEST1", " "};
        assertEquals(false, storage.validRow(row));
        row[0] = "    ";
        row[1] = "Test1";
        assertEquals(false, storage.validRow(row));
    }

    @Test
    void validRowWithCorrectValues() {
        String[] row = {"TEST1", "TEST2"};
        assertEquals(true, storage.validRow(row));
    }

    @Test
    void validDateWithWrongFormat() {
        String dateStr = "25-10-2023";
        String testFileName = "filename";
        assertThrows(DukeException.class, () -> {
            storage.validDate(dateStr, testFileName);
        });
    }

    @Test
    void validDateWithNotDateString() {
        String dateStr = "TEST";
        String testFileName = "filename";
        assertThrows(DukeException.class, () -> {
            storage.validDate(dateStr, testFileName);
        });
    }

    @Test
    void validDateWithCorrectDateString()  throws DukeException {
        String dateStr = "25/10/2023";
        String testFileName = "filename";
        LocalDate date = LocalDate.parse("25/10/2023", FORMATTER);
        assertEquals(date ,storage.validDate(dateStr, testFileName));
    }
}
