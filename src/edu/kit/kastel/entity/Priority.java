package edu.kit.kastel.entity;

/**
 * The different priorities of a task.
 * @author uhquw
 * @version 1.1.1
 */
public enum Priority {
    /**
     * Marks a task as a high priority task.
     */
    HI(" [HI]"),
    /**
     * Marks a task as a mid priority task.
     */
    MD(" [MD]"),
    /**
     * Marks the task as a low priority task.
     */
    LO(" [LO]"),
    /**
     * Marks the task as a task with no priority.
     */
    NO_PRIORITY("");

    /**
     * The abbreviation of the priority.
     */
    private final String visualization;
    Priority(final String visualization) {
        this.visualization = visualization;
    }

    /**
     * Gets the abbreviation of a priority.
     * @return A String representing the visualization
     */
    public String getVisualization() {
        return visualization;
    }
}
