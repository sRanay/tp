package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.Parser;
import seedu.duke.ui.Ui;

import java.util.Scanner;

public class Duke {

    private static Ui ui;

    public Duke() {
        ui = new Ui();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.printGreeting();
        String userInput;
        while (true) {
            userInput = scanner.nextLine();
            try {
                Command command = new Parser().parse(userInput);
                command.execute(ui);

                if (command instanceof ExitCommand) {
                    break;
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
