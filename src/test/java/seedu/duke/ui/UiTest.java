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
                "Header 1     Header 2\n" +
                        "Hi           Test print\n",
                outputStream.toString()
        );
    }

    @Test
    public void printTableWithCustomWidths() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        String[] headers = new String[]{"Header 1", "Header 2"};
        Integer[] widths = new Integer[]{20, 20};
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("Hi");
        row.add("Test print");
        rows.add(row);
        ui.printTableRows(rows, headers, widths);
        assertEquals(
                "Header 1               Header 2\n" +
                        "Hi                     Test print\n",
                outputStream.toString()
        );
    }

    @Test
    public void printTableNoHeader() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("Hi");
        row.add("Test print");
        rows.add(row);
        ui.printTableRows(rows);
        assertEquals(
                "Hi           Test print\n",
                outputStream.toString()
        );
    }

    @Test
    public void printTableNoHeaderWithCustomWidths() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        Integer[] widths = new Integer[]{20, 20};
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("Hi");
        row.add("Test print");
        rows.add(row);
        ui.printTableRows(rows, null, widths);
        assertEquals(
                "Hi                     Test print\n",
                outputStream.toString()
        );
    }

    @Test
    public void printTableNoHeaderWithCustomWidthsSmallerThanDefaultWidths() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Ui ui = new Ui(outputStream);
        Integer[] widths = new Integer[]{5, 5};
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("Hi");
        row.add("Test print");
        rows.add(row);
        ui.printTableRows(rows, null, widths);
        assertEquals(
                "Hi      Te...\n",
                outputStream.toString()
        );
    }
}
