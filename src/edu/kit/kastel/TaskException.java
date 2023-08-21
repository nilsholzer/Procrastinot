package edu.kit.kastel;

/**
 * Exception that is used to catch wrong commands or command arguments.
 * @author uhquw
 * @version 1.0.0
 */
public final class TaskException extends IllegalArgumentException {
    /**
     * Constructs a new TaskException.
     * @param message   describes, why exception was thrown
     */
    public TaskException(final String message) {
        super("ERROR: " + message + "!");
    }
}
