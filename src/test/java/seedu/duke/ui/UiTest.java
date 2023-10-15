package seedu.duke.ui;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UiTest {

    @Test
    public void printTestPrintTableRows() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String[] headers = new String[]{"Header 1", "Header 2"};
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("Hi");
        row.add("Test print");
        rows.add(row);
        ui.printTableRows(rows, headers);
        assertEquals(
                "Header 1     Header 2  \n" +
                        "Hi           Test print\n",
                outputStream.toString()
        );
    }
}
