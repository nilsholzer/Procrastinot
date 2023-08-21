package edu.kit.kastel.ui;

/**
 * This class contains important Strings needed everywhere in the project.
 * @author uhquw
 * @version 1.0.0
 */
public final class Expressions {
    /**
     * Regex for a String with no new lines or whitespaces.
     */
    public static final String NAME = " \\S+";
    /**
     * Regex for a String representing a date in format: YYYY-MM-DD.
     */
    public static final String DATE = " \\d{4}-\\d{2}-\\d{2}";
    /**
     * Regex for a String representing a priority.
     */
    public static final String PRIORITY = " (HI|MD|LO)";
    /**
     * Regex for a String representing a priority with a leading whitespace.
     */
    public static final String OPTIONAL_PRIORITY = "( HI| MD| LO)?";
    /**
     * Regex for a String representing every Integer greater than zero.
     */
    public static final String ID = " [1-9]\\d*";
    /**
     * Regex for a not empty String only containing letters and numbers.
     */
    public static final String TAG = " \\w[^_]+";
    /**
     * Regex for a not empty String only containing letters.
     */
    public static final String LIST = " [A-z]+";
    /**
     * Regex for a String beginning with "assign", followed by an id.
     */
    public static final String ASSIGN = "^assign" + ID;

    private Expressions() {
    }
}
