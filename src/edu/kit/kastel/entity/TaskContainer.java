package edu.kit.kastel.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that contains task and sorts them according to their priority.
 * @author uhquw
 * @version 1.1.0
 */
public abstract class TaskContainer {
    private final List<List<Task>> tasks;
    private final List<Task> high;
    private final List<Task> mid;
    private final List<Task> low;
    private final List<Task> noPriority;

    /**
     * Constructs a new TaskContainer and creates all the lists.
     */
    protected TaskContainer() {
        tasks = new ArrayList<>();
        high = new ArrayList<>();
        tasks.add(high);
        mid = new ArrayList<>();
        tasks.add(mid);
        low = new ArrayList<>();
        tasks.add(low);
        noPriority = new ArrayList<>();
        tasks.add(noPriority);
    }

    /**
     * Checks if the given task is contained in a list matching his priority.
     * @param task The task to be sought
     * @return A boolean if the task is in one of the lists
     */
    public boolean isElement(final Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            return high.contains(task);
        } else if (priority == Priority.MD) {
            return mid.contains(task);
        } else if (priority == Priority.LO) {
            return low.contains(task);
        } else {
            return noPriority.contains(task);
        }
    }

    /**
     * Inserts the given task in the list matching his priority.
     * @param task The task being inserted
     */
    public void assign(final Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            high.add(task);
        } else if (priority == Priority.MD) {
            mid.add(task);
        } else if (priority == Priority.LO) {
            low.add(task);
        } else {
            noPriority.add(task);
        }
    }

    /**
     * Removes the task from the list matching his priority.
     * @param task The task to remove
     */
    public void remove(final Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            high.remove(task);
        } else if (priority == Priority.MD) {
            mid.remove(task);
        } else if (priority == Priority.LO) {
            low.remove(task);
        } else {
            noPriority.remove(task);
        }
    }
    /**
     * Visualizes all the tasks in a specifc visualization, if their state is opened or one of their subtasks´ state is opened.
     * @param whitespaceCount The amount of whitespaces, times 2, being added in front of the visualization
     * @return A String containing the visualization of all the tasks fitting the description
     */
    public StringBuilder todo(final int whitespaceCount) {
        int updatedWhitespaceCount = whitespaceCount + 1;
        StringBuilder result = new StringBuilder();
        for (List<Task> priorities : tasks) {
            for (Task task : priorities) {
                if (task.hasOpenedChildren()) {
                    result.append(System.lineSeparator()).append(task.todo(updatedWhitespaceCount));
                }
            }
        }
        return result;
    }

    /**
     * Checks if the given task is in one of the lists or is one of the children of a task in this list.
     * @param searchedTask The task to be sought
     * @return A boolean representing if the task is in this list or not
     */
    public boolean isInList(final Task searchedTask) {
        for (List<Task> priorities : tasks) {
            for (Task task : priorities) {
                if (task.isInList(searchedTask)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets all the lists and their tasks in them.
     * @return A list of lists that contains all the tasks
     */
    protected List<List<Task>> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
    /**
     * Visualizes all the tasks in a specifc visualization.
     * @param whitespaceCount The amount of whitespaces, times 2, being added in front of the visualization
     * @return A String containing the visualization of all the tasks in that class
     */
    abstract StringBuilder show(int whitespaceCount);
}
