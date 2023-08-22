package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.util.ArrayList;
import java.util.List;

/**
 * A TaskContainer containing only tasks that are part of a list.
 * @author uhquw
 * @version 1.0.0
 */
public class ListOfTasks extends TaskContainer {
    private final List<List<Task>> taskList;

    /**
     * Constructs a TaskContainer that contains only tasks that are part of a list.
     */
    public ListOfTasks() {
        taskList = getTasks();
    }

    @Override
    public StringBuilder todo(int whitespaceCount) {
        throw new TaskException("Cannot execute this method with a ListOfTask");
    }

    /**
     * Checks if children of the given task are in this list.
     * @param searchedTask The task, whose children are being searched for
     * @return A list of tasks containing the children of the given task, that are in this list
     */
    public List<Task> childIsInList(Task searchedTask) {
        List<Task> childTasks = new ArrayList<>();
        for (List<Task> priority : taskList) {
            for (Task task : priority) {
                if (task.isAParent(searchedTask)) {
                    childTasks.add(task);
                }
            }
        }
        return childTasks;
    }
}
