package edu.kit.kastel.ui;

/**
 * This class contains important Strings needed everywhere in the project.
 * @author uhquw
 * @version 1.0.0
 */
public final class Expressions {
    /**
     * Regex for a String representing a priority.
     */
    public static final String PRIORITY = " (HI|MD|LO)";
    /**
     * Marks the end of a Regex and serves that the given String only matches once.
     * The String can match multiple times, when the matching String is pasted multiple times behind the String itself.
     */
    static final String REG_END = "$";
    /**
     * Regex for a String with no new lines or whitespaces.
     */
    static final String NAME = " \\S+";
    /**
     * Regex for a String representing a date in format: YYYY-MM-DD.
     */
    static final String DATE = " \\d{4}-\\d{2}-\\d{2}";
    /**
     * Regex for a String representing a priority with a leading whitespace.
     */
    static final String OPTIONAL_PRIORITY = PRIORITY + "?" + REG_END;
    /**
     * Regex for a String representing every Integer greater than zero.
     */
    static final String ID = " [1-9]\\d*";
    /**
     * Regex for a not empty String only containing letters and numbers.
     */
    static final String TAG_REGEX = " [a-zA-Z0-9]+" + REG_END;
    /**
     * Regex for a not empty String only containing letters.
     */
    static final String LIST = " [a-zA-Z]+";
    /**
     * Regex for a String beginning with "assign", followed by an id.
     */
    static final String ASSIGN = "^assign" + ID;
    static final String TAG = "^tag";


    private Expressions() {
    }
}
