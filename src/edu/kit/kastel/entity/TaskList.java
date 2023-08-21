package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

public class TaskList extends Entity {

    private final PriorityList tasks;

    public TaskList(final String name) {
        super(name);
        tasks = new PriorityList();
    }

    @Override
    public void assign(Task task) {
        if (!tasks.isInList(task)) {
            Task childTask = tasks.childIsInList(task);
            if (childTask != null) {
                tasks.remove(childTask);
            }
            tasks.assign(task);
        } else {
            throw new TaskException("Task is already in this list");
        }
    }

    public StringBuilder list() {
        return tasks.show(-1);
    }

    @Override
    public int delete(Task task) {
        if (tasks.isElement(task)) {
            tasks.remove(task);
        }
        return 1;
    }

    public void restore(Task task) {
        if (!tasks.isInList(task)) {
            tasks.assign(task);
        }
    }
}
