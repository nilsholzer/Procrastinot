package edu.kit.kastel.entity;

/**
 * The different states of a task.
 * @author uhquw
 * @version 1.1.0
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
    private final String visualization;
    State(final String visualization) {
        this.visualization = visualization;
    }

    /**
     * Gets the visualization of a state.
     * @return A String represeting the visualization
     */
    public String getVisualization() {
        return visualization;
    }
}
