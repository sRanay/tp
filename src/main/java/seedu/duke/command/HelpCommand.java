package seedu.duke.command;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.duke.ui.Ui;

public class HelpCommand extends Command {
    private static final String LINE_DIVIDER = "";
    private static final String[] FULL_LIST_HEADERS = {"Command", "Description"};
    private static final Integer[] CUSTOM_COLUMN_WIDTH = {10, 1000};
    private static final String HELP_COMMAND = "help";
    private static final String HELP_DESCRIPTION = "Shows a list of all the commands available to the user";
    private static final String IN_COMMAND = "in";
    private static final String IN_DESCRIPTION = "Adds an income towards goal";
    private static final String OUT_COMMAND = "out";
    private static final String OUT_DESCRIPTION = "Adds an expense for a category";
    private static final String DELETE_COMMAND = "delete";
    private static final String DELETE_DESCRIPTION = "Delete a specific transaction based on the index in the list";
    private static final String LIST_COMMAND = "list";
    private static final String LIST_DESCRIPTION = "Shows a list of all added transactions based on type";


    public HelpCommand(String command, HashMap<String, String> args) {
        super(command, args);
    }

    public ArrayList<String> convertCommandList(String command, String description) {
        ArrayList<String> tableData = new ArrayList<String>();
        tableData.add(command);
        tableData.add(description);
        return tableData;
    }

    public ArrayList<String> printHelpDescription() {
        ArrayList<String> help = convertCommandList(HELP_COMMAND, HELP_DESCRIPTION);
        return help;
    }

    public ArrayList<String> printInDescription() {
        ArrayList<String> in = convertCommandList(IN_COMMAND, IN_DESCRIPTION);
        return in;
    }

    public ArrayList<String> printOutDescription() {
        ArrayList<String> out = convertCommandList(OUT_COMMAND, OUT_DESCRIPTION);
        return out;
    }

    public ArrayList<String> printDeleteDescription() {
        ArrayList<String> delete = convertCommandList(DELETE_COMMAND, DELETE_DESCRIPTION);
        return delete;
    }

    public ArrayList<String> printListDescription() {
        ArrayList<String> list = convertCommandList(LIST_COMMAND, LIST_DESCRIPTION);
        return list;
    }

    public ArrayList<ArrayList<String>> printFullList(ArrayList<ArrayList<String>> helpList) {
        helpList.add(printHelpDescription());
        helpList.add(printInDescription());
        helpList.add(printOutDescription());
        helpList.add(printDeleteDescription());
        helpList.add(printListDescription());
        assert helpList != null;
        return helpList;
    }

    // public void printHelpUsage() {
    //     print("Usage: help\n");
    // }

    // public void printInUsage() {
    //     print("Usage: in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]\n");
    // }

    // public void printOutUsage() {
    //     print("Usage: out DESCRIPTION /amount  AMOUNT /category CATEGORY [/date DATE in DDMMYYYY]\n");
    // }

    // public void printDeleteUsage() {
    //     print("Usage: delete INDEX /type (in | out)\n");
    // }

    // public void printListUsage() {
    //     print("Usage: list /type (in | out) [/goal GOAL] [/category CATEGORY]\n");
    // }

    @Override
    public void execute(Ui ui) {
        ArrayList<ArrayList<String>> helpList = new ArrayList<ArrayList<String>>();
        if(getDescription().isBlank()) {
            printFullList(helpList);
            ui.print(LINE_DIVIDER);
            ui.printTableRows(helpList, FULL_LIST_HEADERS, CUSTOM_COLUMN_WIDTH);
            ui.print(LINE_DIVIDER);
        } else {
            switch(getDescription()) {
            case "help":
                break;
            case "in":
                break;
            case "out":
                break;
            case "delete":
                break;
            case "list":
                break;
            default:
                System.out.println("NO SUCH COMMANDS");
                break;
            }
            System.out.println("Works");
        }
    }
}
