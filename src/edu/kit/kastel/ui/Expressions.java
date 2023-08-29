package edu.kit.kastel.ui;

/**
 * This class contains important Strings needed everywhere in the project.
 * @author uhquw
 * @version 1.1.0
 */
public final class Expressions {

    /**
     * Message being shown if you wanted to construct a class, that cannot be constructed.
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated";
    /**
     * Regex for a String representing a priority.
     */
    public static final String PRIORITY = "\\s+(HI|MD|LO)";
    /**
     * String "deleted" as a constant, so it can be used over the project.
     */
    public static final String DELETED = "deleted";
    /**
     * String "assign" as a constant, so it can be used over the project.
     */
    public static final String ASSIGN = "assign";
    /**
     * String "toggle" as a constant, so it can be used over the project.
     */
    public static final String TOGGLE = "toggle";
    /**
     * String "show" as a constant, so it can be used over the project.
     */
    public static final String SHOW = "show";
    /**
     * Marks the end of a Regex and serves that the given String only matches once.
     * The String can match multiple times, when the matching String is pasted multiple times behind the String itself.
     */
    private static final String END = "\\s*$";
    /**
     * Regex for a String with no new lines or whitespaces.
     */
    private static final String NAME = "\\s+\\S+";
    /**
     * Regex for a String representing a date in format: YYYY-MM-DD.
     */
    private static final String DATE = "\\s+\\d{4}-\\d{2}-\\d{2}";
    /**
     * Regex for a String representing every Integer greater than zero.
     */
    private static final String ID = "\\s+[1-9]\\d*";
    /**
     * Regex for a not empty String only containing letters and numbers.
     */
    private static final String TAG_REGEX = "\\s+[a-zA-Z0-9]+" + END;
    /**
     * Regex for a not empty String only containing letters.
     */
    private static final String LIST = "\\s+[a-zA-Z]+";
    private static final String START = "^\\s*";
    private static final String INSERT = "%s";
    private static final String ADD_COMMAND = START + "add" + NAME + "((" + PRIORITY + "|" + DATE + ")?|(" + PRIORITY + DATE + "))" + END;
    private static final String ADD_LIST_COMMAND = START + "add-list" + LIST + END;
    private static final String TAG_COMMAND = START + "tag" + INSERT + TAG_REGEX;
    private static final String ASSIGN_COMMAND = START + ASSIGN + ID + INSERT + END;
    private static final String ONLY_ID_COMMAND = START + INSERT + ID + END;
    private static final String CHANGE_COMMAND = START + "change-%1$s" + ID + "%2$s" +  END;
    private static final String ONE_WORD_COMMAND = START + INSERT + END;
    private static final String LIST_COMMAND = START + "list" + LIST + END;
    private static final String TAGGED_WITH_COMMAND = START + "tagged-with" + TAG_REGEX;
    private static final String FIND_COMMAND = START + "find" + NAME + END;
    private static final String DATE_COMMAND = START + INSERT + DATE + END;
    private static final String BETWEEN_COMMAND = START + "between" + DATE + DATE + END;
    private static final String DATE_NAME = "date";
    private static final String PRIORITY_NAME = "priority";
    private static final String OPTIONAL = "?";
    private Expressions() {
        throw new UnsupportedOperationException(UTILITY_CLASS_INSTANTIATION);
    }
    static String getAddCommand() {
        return ADD_COMMAND;
    }
    static String getAddListCommand() {
        return ADD_LIST_COMMAND;
    }
    static String getTagCommand() {
        return String.format(TAG_COMMAND, ID);
    }
    static String getTagListCommand() {
        return String.format(TAG_COMMAND, LIST);
    }
    static String getAssignCommand() {
        return String.format(ASSIGN_COMMAND, ID);
    }
    static String getAssignListCommand() {
        return String.format(ASSIGN_COMMAND, LIST);
    }
    static String getOnlyIdCommand(final String usage) {
        return String.format(ONLY_ID_COMMAND, usage);
    }
    static String getChangeDateCommand() {
        return String.format(CHANGE_COMMAND, DATE_NAME, DATE);
    }
    static String getChangePriorityCommand() {
        return String.format(CHANGE_COMMAND, PRIORITY_NAME, PRIORITY + OPTIONAL);
    }
    static String getOneWordCommand(final String usage) {
        return String.format(ONE_WORD_COMMAND, usage);
    }
    static String getListCommand() {
        return LIST_COMMAND;
    }
    static String getTaggedWithCommand() {
        return TAGGED_WITH_COMMAND;
    }
    static String getFindCommand() {
        return FIND_COMMAND;
    }
    static String getDateCommand(final String usage) {
        return String.format(DATE_COMMAND, usage);
    }
    static String getBetweenCommand() {
        return BETWEEN_COMMAND;
    }
}
