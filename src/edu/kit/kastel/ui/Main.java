
package edu.kit.kastel.ui;

import edu.kit.kastel.TaskException;
import edu.kit.kastel.logic.Procrastinot;

import java.util.Scanner;


/**
 * Main class that runs the application.
 * @author uhquw
 * @version 1.00
 */
public final class Main {
    private static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated";

    private Main() {
        throw new IllegalArgumentException(UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main method used as entry point.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        final Procrastinot procrastinot = new Procrastinot();
        Scanner commandScanner = new Scanner(System.in);
        while (procrastinot.isActive()) {
            final String command = commandScanner.nextLine();
            try {
                final String output = Command.executeCommand(command, procrastinot);
                if (output != null) {
                    System.out.println(output);
                }
            } catch (final TaskException exception) {
                System.out.println(exception.getMessage());
            }
        }
        commandScanner.close();
    }
}
