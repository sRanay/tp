package seedu.duke.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ui {
    private static final String ELLIPSIS = "...";
    private static final String PROGRAM_NAME = "FinText";
    private static final char FILLER_CHAR = ' ';
    private static final int COLUMN_WIDTH = 10;
    private static final int SPACE_BETWEEN_COLS = 3;
    private static final String AMOUNT_FORMAT = "%.2f";
    private static final char LINE_DELIMITER = '\n';

    private final OutputStream outputStream;

    public Ui() {
        outputStream = System.out;
    }

    public Ui(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void printTableRow(ArrayList<String> rowValues) {
        printTableRow(rowValues, null, null);
    }

    public void printTableRow(ArrayList<String> rowValues, String[] headers) {
        printTableRow(rowValues, headers, null);
    }

    public void printTableRow(ArrayList<String> rowValues, String[] headers, Integer[] customWidths) {
        assert rowValues != null;
        ArrayList<Integer> colWidths = printTableHeader(headers, customWidths);
        if (colWidths == null) {
            colWidths = getDefaultColWidths(rowValues.size());
            colWidths = mergeColWidths(colWidths, customWidths);
        }
        print(formatColumnValues(colWidths, rowValues));
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows) {
        printTableRows(rows, null);
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows, String[] headers) {
        printTableRows(rows, headers, null);
    }

    public void printTableRows(ArrayList<ArrayList<String>> rows, String[] headers, Integer[] customWidths) {
        assert rows != null;
        ArrayList<Integer> colWidths = printTableHeader(headers, customWidths);
        if (rows.isEmpty()) {
            return;
        }

        if (colWidths == null) {
            colWidths = getDefaultColWidths(rows.get(0).size());
            colWidths = mergeColWidths(colWidths, customWidths);
        }

        for (ArrayList<String> rowValues : rows) {
            print(formatColumnValues(colWidths, rowValues));
        }
    }

    public ArrayList<Integer> printTableHeader(String[] headers, Integer[] customWidths) {
        if (headers == null) {
            return null;
        }

        ArrayList<Integer> colWidths = (ArrayList<Integer>) Arrays.stream(headers)
                .parallel()
                .map(header -> Math.max(header.length(), COLUMN_WIDTH))
                .collect(Collectors.toList());
        colWidths = mergeColWidths(colWidths, customWidths);
        List<String> headerList = Arrays.asList(headers);
        print(formatColumnValues(colWidths, new ArrayList<>(headerList)));
        return colWidths;
    }

    public String formatAmount(Double value) {
        return String.format(AMOUNT_FORMAT, value);
    }

    public void print(String value) {
        try {
            outputStream.write(value.getBytes(StandardCharsets.UTF_8));
            outputStream.write(LINE_DELIMITER);
            outputStream.flush();
        } catch (IOException e) {
            // Fail quietly for now
        }
    }

    public void printGreeting() {
        print("Welcome to " + PROGRAM_NAME + ", your personal finance tracker.");
    }

    public void printBye() {
        print("Bye Bye!");
    }

    private ArrayList<Integer> getDefaultColWidths(int length) {
        return (ArrayList<Integer>) IntStream.range(0, length)
                .mapToObj(i -> COLUMN_WIDTH)
                .collect(Collectors.toList());
    }

    private ArrayList<Integer> mergeColWidths(ArrayList<Integer> colWidths, Integer[] customWidths) {
        assert colWidths != null;
        if (customWidths == null) {
            return colWidths;
        }

        assert colWidths.size() <= customWidths.length;
        for (int i = 0; i < colWidths.size(); ++i) {
            colWidths.add(i, Math.max(colWidths.get(i), customWidths[i]));
        }

        return colWidths;
    }

    private String formatColumnValues(ArrayList<Integer> colWidths, ArrayList<String> colValues) {
        assert colWidths != null;
        assert colValues != null;
        assert colWidths.size() >= colValues.size();

        ArrayList<String> finalValues = new ArrayList<>(colValues.size());
        int lastIdx = colValues.size() - 1;
        for (int i = 0; i < colValues.size(); ++i) {
            int maxWidth = colWidths.get(i);
            String truncatedValue = colValues.get(i);
            if (truncatedValue.length() > maxWidth) {
                truncatedValue = truncatedValue.substring(0, maxWidth - ELLIPSIS.length()) + ELLIPSIS;
            } else if (i != lastIdx) {
                char[] fillerChars = new char[maxWidth - truncatedValue.length()];
                Arrays.fill(fillerChars, FILLER_CHAR);
                truncatedValue += new String(fillerChars);
            }
            finalValues.add(i, truncatedValue);
        }

        char[] spacer = new char[SPACE_BETWEEN_COLS];
        Arrays.fill(spacer, FILLER_CHAR);
        return String.join(new String(spacer), finalValues);
    }
}
