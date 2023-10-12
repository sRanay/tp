package seedu.duke.ui;

public class Ui {
    final String PROGRAM_NAME = "FinText";

    final String SEPARATOR = "____________________________________________________________";

    public Ui() {
    }

    public void printGreeting() {
        System.out.println("Welcome to " + PROGRAM_NAME + ", your personal finance tracker.");
    }

    public void printBye() {
        System.out.println("Bye Bye!");
    }

}
