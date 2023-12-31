package edu.kit.kastel.ui;

import edu.kit.kastel.TaskException;
import edu.kit.kastel.logic.Procrastinot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type of commands.
 * @author uhquw
 * @version 1.1.0
 */
public enum Command {
    /**
     * This command adds a new task.
     */
    ADD(Expressions.getAddCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            String secondArg = "";
            String date = "";
            if (split.length > 2) {
                secondArg = split[2];
                if (split.length == FOUR) {
                    date = split[THREE];
                }
            }
            return procrastinot.add(split[1], secondArg, date);
        }
    },
    /**
     * This command adds a new list.
     */
    ADD_LIST(Expressions.getAddListCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.addList(split[1]);
        }
    },
    /**
     * This command tags a task with a tag.
     */
    TAG(Expressions.getTagCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            int id = Integer.parseInt(split[1]);
            return procrastinot.tag(id, split[2]);
        }
    },
    /**
     * This command tags a list with a tag.
     */
    TAG_LIST(Expressions.getTagListCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.tagList(split[1], split[2]);
        }
    },
    /**
     * This command assigns one task to another task.
     */
    ASSIGN(Expressions.getAssignCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            int childId = Integer.parseInt(split[1]);
            int parentId = Integer.parseInt(split[2]);
            return procrastinot.assign(childId, parentId);
        }
    },
    /**
     * This command assigns a task to a list.
     */
    ASSIGN_LIST(Expressions.getAssignListCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            int id = Integer.parseInt(split[1]);
            return procrastinot.assignList(id, split[2]);
        }
    },
    /**
     * This command toggles a task and all its subtasks.
     */
    TOGGLE(Expressions.getOnlyIdCommand(Expressions.TOGGLE)) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.toggle(Integer.parseInt(split[1]));
        }
    },
    /**
     * This command changes the deadline of a task.
     */
    CHANGE_DATE(Expressions.getChangeDateCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            int id = Integer.parseInt(split[1]);
            return procrastinot.changeDate(id, split[2]);
        }
    },
    /**
     * This command changes the priority of a task.
     */
    CHANGE_PRIORITY(Expressions.getChangePriorityCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            int id = Integer.parseInt(split[1]);
            if (split.length == 2) {
                return procrastinot.changePriority(id, NO_PRIORITY);
            }
            return procrastinot.changePriority(id, split[2]);
        }
    },
    /**
     * This command deletes a task and all its subtasks.
     */
    DELETE(Expressions.getOnlyIdCommand("delete")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.delete(Integer.parseInt(split[1]));
        }
    },
    /**
     * This command restores a task and all its subtasks.
     */
    RESTORE(Expressions.getOnlyIdCommand("restore")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.restore(Integer.parseInt(split[1]));
        }
    },
    /**
     * This command shows a task and all its subtasks.
     */
    SHOW(Expressions.getOnlyIdCommand(Expressions.SHOW)) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.show(Integer.parseInt(split[1]));
        }
    },
    /**
     * This command shows all tasks that are not completed.
     */
    TODO(Expressions.getOneWordCommand("todo")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            return procrastinot.todo();
        }
    },
    /**
     * This command shows all tasks, and their subtasks, that are part of a specific list.
     */
    LIST(Expressions.getListCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.list(split[1]);
        }
    },
    /**
     * This command shows all tasks, and their subtasks, that are tagged with a specific tag.
     */
    TAGGED_WITH(Expressions.getTaggedWithCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.taggedWith(split[1]);
        }
    },
    /**
     * This command lists all tasks, and their subtasks, whose name contains a specific String.
     */
    FIND(Expressions.getFindCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.find(split[1]);
        }
    },
    /**
     * This command lists all tasks, and their subtasks, whose deadline is within the next 7 days of the specific date.
     */
    UPCOMING(Expressions.getDateCommand("upcoming")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.upcoming(split[1]);
        }
    },
    /**
     * This command lists all tasks, and their subtasks, whose deadline is before and on the specific date.
     */
    BEFORE(Expressions.getDateCommand("before")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.before(split[1]);
        }
    },
    /**
     * This command lists all tasks, and their subtasks, whose deadline is in between the two specific dates.
     */
    BETWEEN(Expressions.getBetweenCommand()) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            String[] split = input.split(WHITESPACE);
            return procrastinot.between(split[1], split[2]);
        }
    },
    /**
     * This command finds tasks which have duplicates.
     */
    DUPLICATES(Expressions.getOneWordCommand("duplicates")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            return procrastinot.duplicates();
        }
    },
    /**
     * This command quits the application.
     */
    QUIT(Expressions.getOneWordCommand("quit")) {
        @Override
        String execute(String input, Procrastinot procrastinot) {
            procrastinot.quit();
            return null;
        }
    };
    private static final String WHITESPACE = "\\s+";
    private static final String NO_PRIORITY = "NO_PRIORITY";
    private static final int FOUR = 4;
    private static final int THREE = 3;
    private final Pattern pattern;

    Command(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * This method searches for the right command being executed & executes it.
     * @param input The input by the user, trying to execute a specific command
     * @param procrastinot The application where the command is being executed
     * @return A String representing the return of the command being executed
     * @throws TaskException if input does not match the pattern of any command
     */
    public static String executeCommand(final String input, final Procrastinot procrastinot) {
        for (final Command command : values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(input.trim(), procrastinot);
            }
        }
        throw new TaskException("Invalid Command or Command arguments, try again");
    }

    /**
     * Method that executes a command.
     * @param input The input by the user, which contains variables for the command
     * @param procrastinot The application where the command is being executed
     * @return A String to notify the user of the succeeded execution of the command
     */
    abstract String execute(String input, Procrastinot procrastinot);
}
