package seedu.duke.ui;

public class Ui {
    private static final String PROGRAM_NAME = "FinText";

    public Ui() {
    }

    public void printGreeting() {
        System.out.println("Welcome to " + PROGRAM_NAME + ", your personal finance tracker.");
    }

    public void printBye() {
        System.out.println("Bye Bye!");
    }

}
