package edu.kit.kastel.entity;

/**
 * The different states of a task.
 * @author uhquw
 * @version 1.0.0
 */
public enum State {
    /**
     * Marks a task as an opened task.
     */
    OPEN(" [ ] "),
    /**
     * Marks a task as a closed task.
     */
    CLOSED(" [x] "),
    /**
     * Marks the task as a deleted task.
     */
    DELETED("");

    /**
     * The abbreviation of the state.
     */
    public final String abbreviation;
    State(final String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
