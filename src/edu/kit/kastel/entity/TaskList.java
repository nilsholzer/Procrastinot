package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

public class TaskList extends Entity {

    private final ListOfTasks listOfTasks;

    public TaskList(final String name) {
        super(name);
        listOfTasks = new ListOfTasks();
    }

    @Override
    public void assign(Task task) {
        if (!listOfTasks.isInList(task)) {
            Task childTask = listOfTasks.childIsInList(task);
            if (childTask != null) {
                listOfTasks.remove(childTask);
            }
            listOfTasks.assign(task);
        } else {
            throw new TaskException("Task is already in this list");
        }
    }

    public StringBuilder list() {
        return listOfTasks.show(-1);
    }

    public void delete(Task task) {
        if (listOfTasks.isElement(task)) {
            listOfTasks.remove(task);
        }
    }

    public void restore(Task task) {
        if (!listOfTasks.isInList(task)) {
            listOfTasks.assign(task);
        }
    }
}
