package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.time.LocalDate;
import java.util.List;

public class SuperiorTasks extends TaskContainer {
    private static final String ILLEGAL_ACCESS = "Cannot execute this method with a SuperiorTask";
    private final List<List<Task>> superiorTasks;

    public SuperiorTasks() {
        superiorTasks = getTasks();
    }

    @Override
    public StringBuilder show(int whitespaceCount) {
        throw new TaskException(ILLEGAL_ACCESS);
    }

    @Override
    public boolean isInList(Task searchedTask) {
        throw new TaskException(ILLEGAL_ACCESS);
    }

    public String find(String name) {
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
        return "nein";
    }

    public String taggedWith(String tag) {
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
        return "nein";
    }

    public String dateBetween(LocalDate startDate, LocalDate finishDate) {
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
        return "Nein!";
    }

    public String dateBefore(LocalDate lastDate) {
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
        return "Nein!";
    }
}
