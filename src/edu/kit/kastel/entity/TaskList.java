package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.util.List;

/**
 * A list that contains tasks.
 * @author uhquw
 * @version 1.0.1
 */
public final class TaskList extends Entity {

    private final ListOfTasks listOfTasks;

    /**
     * Constructs a TaskList.
     * @param name The name of the TaskList
     */
    public TaskList(final String name) {
        super(name);
        listOfTasks = new ListOfTasks();
    }

    @Override
    public void assign(final Task task) {
        if (!listOfTasks.isInList(task)) {
            List<Task> childTasks = listOfTasks.childIsInList(task);
            for (Task childTask : childTasks) {
                listOfTasks.remove(childTask);
            }
            listOfTasks.assign(task);
        } else {
            throw new TaskException("Task is already in this list");
        }
    }

    /**
     * lists all the tasks, and their subtasks, which are in the TaskList in a given order.
     * @return A visual representation of all the tasks in that list
     */
    public String list() {
        return listOfTasks.show(-1).toString();
    }

    /**
     * Deletes a task from that list.
     * @param task The task that should be deleted
     */
    public void delete(final Task task) {
        if (listOfTasks.isElement(task)) {
            listOfTasks.remove(task);
        }
    }

    /**
     * Puts a deleted task back in the list again.
     * @param task The task to be restored
     */
    public void restore(final Task task) {
        if (!listOfTasks.isInList(task)) {
            listOfTasks.assign(task);
        }
    }
}
