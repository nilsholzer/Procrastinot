package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.time.LocalDate;
import java.util.List;

/**
 * A TaskContainer, which only contains tasks with no parent.
 * @author uhquw
 * @version 1.1.0
 */
public final class SuperiorTasks extends TaskContainer {
    private static final String EMPTY = "";
    private static final String ILLEGAL_ACCESS = "Cannot execute this method with a SuperiorTask";
    private final List<List<Task>> superiorTasks;

    /**
     * Constructs a list only containing tasks with no parents.
     */
    public SuperiorTasks() {
        superiorTasks = getTasks();
    }

    @Override
    public StringBuilder show(final int whitespaceCount) {
        throw new TaskException(ILLEGAL_ACCESS);
    }

    @Override
    public boolean isInList(final Task searchedTask) {
        throw new TaskException(ILLEGAL_ACCESS);
    }

    /**
     * Prints out all tasks and their subtasks, whose name contains the given String.
     * @param name The String that is searched for
     * @return A String containing all the tasks and their subtasks, whose name contains the given String
     */
    public String find(final String name) {
        StringBuilder result = new StringBuilder();
        for (List<Task> priority : superiorTasks) {
            for (Task task : priority) {
                List<Task> taskList = task.find(name);
                for (Task foundTask : taskList) {
                    result.append(System.lineSeparator()).append(foundTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return EMPTY;
    }

    /**
     * Prints out all the tasks and their subtasks, whose tags contain the given tag.
     * @param tag The tag that is searched for
     * @return A String containing all the tasks and their subtasks, whose tags contain the given tag
     */
    public String taggedWith(final String tag) {
        StringBuilder result = new StringBuilder();
        for (List<Task> priority : superiorTasks) {
            for (Task task : priority) {
                List<Task> taskList = task.taggedWith(tag);
                for (Task foundTask : taskList) {
                    result.append(System.lineSeparator()).append(foundTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return EMPTY;
    }

    /**
     * Prints out all the tasks and their subtasks, whose deadline is in between both the given dates.
     * @param startDate The date one day before the deadline should at least be
     * @param finishDate The date one day after the deadline should at latest be
     * @return A String containing all the tasks and their subtasks, whose deadline is in between the given dates.
     */
    public String dateBetween(final LocalDate startDate, final LocalDate finishDate) {
        StringBuilder result = new StringBuilder();
        for (List<Task> priority : superiorTasks) {
            for (Task task : priority) {
                List<Task> list = task.dateBetween(startDate, finishDate);
                for (Task betweenTask : list) {
                    result.append(System.lineSeparator()).append(betweenTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return EMPTY;
    }

    /**
     * Prints out all the tasks and their subtasks, whose deadline is before the given date.
     * @param lastDate The date one day after the deadline should at latest be
     * @return A String containing all of the tasks and their subtasks, whose deadline is before the given date
     */
    public String dateBefore(final LocalDate lastDate) {
        StringBuilder result = new StringBuilder();
        for (List<Task> priority : superiorTasks) {
            for (Task task : priority) {
                List<Task> list = task.dateBefore(lastDate);
                for (Task beforeTask : list) {
                    result.append(System.lineSeparator()).append(beforeTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return EMPTY;
    }
}
