package seedu.duke.ui;

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

    public Ui() {
    }

    public void printTableRow(ArrayList<String> rowValues) {
        printTableRow(rowValues, null);
    }

    public void printTableRow(ArrayList<String> rowValues, String[] headers) {
        ArrayList<Integer> colWidths = printTableHeader(headers);
        if (colWidths == null) {
            colWidths = getDefaultColWidths(rowValues.size());
        }

        print(formatColumnValues(colWidths, rowValues));
    }

    public ArrayList<Integer> printTableHeader(String[] headers) {
        if (headers == null) {
            return null;
        }

        ArrayList<Integer> colWidths = (ArrayList<Integer>) Arrays.stream(headers)
                .parallel()
                .map(header -> Math.max(header.length(), COLUMN_WIDTH))
                .collect(Collectors.toList());
        List<String> headerList = Arrays.asList(headers);
        print(formatColumnValues(colWidths, new ArrayList<>(headerList)));
        return colWidths;
    }

    public String formatAmount(Double value) {
        return String.format(AMOUNT_FORMAT, value);
    }

    public void print(String value) {
        System.out.println(value);
    }

    public void printGreeting() {
        System.out.println("Welcome to " + PROGRAM_NAME + ", your personal finance tracker.");
    }

    public void printBye() {
        System.out.println("Bye Bye!");
    }

    private ArrayList<Integer> getDefaultColWidths(int length) {
        return (ArrayList<Integer>) IntStream.range(0, length)
                .mapToObj(i -> COLUMN_WIDTH)
                .collect(Collectors.toList());
    }

    private String formatColumnValues(ArrayList<Integer> colWidths, ArrayList<String> colValues) {
        assert colWidths.size() >= colValues.size();
        ArrayList<String> finalValues = new ArrayList<>(colValues.size());
        for (int i = 0; i < colValues.size(); ++i) {
            int maxWidth = colWidths.get(i);
            String truncatedValue = colValues.get(i);
            if (truncatedValue.length() > maxWidth) {
                truncatedValue = truncatedValue.substring(0, maxWidth - ELLIPSIS.length()) + ELLIPSIS;
            } else {
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
