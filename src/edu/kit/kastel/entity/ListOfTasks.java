package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.util.List;

public class ListOfTasks extends TaskContainer {
    private final List<List<Task>> taskList;

    public ListOfTasks() {
        taskList = getTasks();
    }

    @Override
    public StringBuilder todo(int whitespaceCount) {
        throw new TaskException("Cannot execute this method with a ListOfTask");
    }

    public Task childIsInList(Task searchedTask) {
        for (List<Task> priority : taskList) {
            for (Task task : priority) {
                if (task.isAParent(searchedTask)) {
                    return task;
                }
            }
        }
        return null;
    }
}
