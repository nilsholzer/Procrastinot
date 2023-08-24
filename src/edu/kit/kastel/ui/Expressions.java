package edu.kit.kastel.ui;

import java.io.File;

/**
 * This class contains important Strings needed everywhere in the project.
 * @author uhquw
 * @version 1.0.1
 */
public final class Expressions {

    /**
     * Message being shown if you wanted to construct a class, that cannot be constructed.
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated";
    /**
     * Regex for a String representing a priority.
     */
    public static final String PRIORITY = " (HI|MD|LO)";
    /**
     * String "deleted" as a constant, so it can be used over the project.
     */
    public static final String DELETED = "deleted";
    /**
     * File seperator needed, so regex is correct.
     */
    static final String FILE_SEPERATOR = File.separator;
    /**
     * Marks the end of a Regex and serves that the given String only matches once.
     * The String can match multiple times, when the matching String is pasted multiple times behind the String itself.
     */
    static final String REG_END = "$";
    /**
     * Regex for a String with no new lines or whitespaces.
     */
    static final String NAME = " " + FILE_SEPERATOR + "S+";
    /**
     * Regex for a String representing a date in format: YYYY-MM-DD.
     */
    static final String DATE = " " + FILE_SEPERATOR  + "d{4}-" + FILE_SEPERATOR + "d{2}-" + FILE_SEPERATOR + "d{2}";
    /**
     * Regex for a String representing a priority with a leading whitespace.
     */
    static final String OPTIONAL_PRIORITY = PRIORITY + "?" + REG_END;
    /**
     * Regex for a String representing every Integer greater than zero.
     */
    static final String ID = " [1-9]" + FILE_SEPERATOR + "d*";
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
        throw new UnsupportedOperationException(UTILITY_CLASS_INSTANTIATION);
    }
}
