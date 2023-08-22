package edu.kit.kastel.logic;

import edu.kit.kastel.entity.Priority;
import edu.kit.kastel.entity.SuperiorTasks;
import edu.kit.kastel.entity.Task;
import edu.kit.kastel.entity.TaskList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains all the tasks and lists for the application.
 * @author uhquw
 * @version 1.0.0
 */
public class DataStructure implements DataStructureCommands {
    private final SuperiorTasks superiorTasks;
    private final List<Task> tasks;
    private final List<TaskList> taskLists;

    /**
     * Constructs a new Data Structure.
     */
    public DataStructure() {
        superiorTasks = new SuperiorTasks();
        tasks = new ArrayList<>();
        taskLists = new ArrayList<>();
    }

    /**
     * Gives information about the amount of current tasks.
     * @return An Integer representing the current amount of tasks
     */
    public int taskAmount() {
        return tasks.size();
    }

    @Override
    public void add(final Task task) {
        superiorTasks.assign(task);
        tasks.add(task);
    }

    @Override
    public void addList(TaskList taskList) {
        taskLists.add(taskList);
    }

    @Override
    public String tag(int index, String tag) {
        tasks.get(index).tag(tag);
        return tasks.get(index).getName();
    }

    @Override
    public void tagList(int listIndex, String tag) {
        taskLists.get(listIndex).tag(tag);
    }

    @Override
    public String[] assign(int childIndex, int parentIndex) {
        String[] names = new String[2];
        names[0] = tasks.get(childIndex).getName();
        names[1] = tasks.get(parentIndex).getName();
        if (tasks.get(childIndex).getParent() == null) {
            superiorTasks.remove(tasks.get(childIndex));
        }
        tasks.get(parentIndex).assign(tasks.get(childIndex));
        return names;
    }

    @Override
    public String assignList(int index, int listIndex) {
        taskLists.get(listIndex).assign(tasks.get(index));
        tasks.get(index).assignList(listIndex);
        return tasks.get(index).getName();
    }

    @Override
    public String[] toggle(int index) {
        String[] taskInformation = new String[2];
        taskInformation[0] = tasks.get(index).getName();
        taskInformation[1] = String.valueOf(tasks.get(index).toggle(index + 1));
        return taskInformation;
    }

    @Override
    public String changeDate(int index, LocalDate date) {
        tasks.get(index).setDate(date);
        return tasks.get(index).getName();
    }

    @Override
    public String changePriority(int index, Priority priority) {
        Task changedTask = tasks.get(index);
        if (changedTask.getPriority() != priority) {
            changedTask.setPriority(priority);
            if (superiorTasks.isElement(changedTask)) {
                superiorTasks.remove(changedTask);
                superiorTasks.assign(changedTask);
            }
            List<Integer> assignedLists = changedTask.getAssignedLists();
            for (int listIndex : assignedLists) {
                taskLists.get(listIndex).changePriority(changedTask);
            }
        }

        return tasks.get(index).getName();
    }

    @Override
    public String[] delete(int index) {
        Task deletedTask = tasks.get(index);
        String[] deleteInformation = new String[2];
        deleteInformation[0] = deletedTask.getName();
        deleteInformation[1] = String.valueOf(deletedTask.delete(deletedTask));
        List<Integer> assignedLists = deletedTask.getAssignedLists();
        for (int listIndex : assignedLists) {
            taskLists.get(listIndex).delete(deletedTask);
        }
        return deleteInformation;
    }

    @Override
    public String[] restore(int index) {
        Task restoredTask = tasks.get(index);
        String[] restoreInformation = new String[2];
        restoreInformation[0] = restoredTask.getName();
        restoreInformation[1] = String.valueOf(restoredTask.restore(index + 1));
        if (restoredTask.getParent() == null) {
            superiorTasks.assign(restoredTask);
        }
        List<Integer> assignedLists = restoredTask.getAssignedLists();
        for (int listIndex : assignedLists) {
            taskLists.get(listIndex).restore(restoredTask);
        }
        return restoreInformation;
    }

    @Override
    public String show(int index) {
        return tasks.get(index).show(0);
    }

    @Override
    public String todo() {
        StringBuilder result = superiorTasks.todo(-1);
        return result.delete(0, 1).toString();
    }

    @Override
    public String list(int listIndex) {
        return taskLists.get(listIndex).list().delete(0, 1).toString();
    }

    @Override
    public String taggedWith(String tag) {
        return superiorTasks.taggedWith(tag);
    }

    @Override
    public String find(String name) {
        return superiorTasks.find(name);
    }

    @Override
    public String between(LocalDate startDate, LocalDate finishDate) {
        return superiorTasks.dateBetween(startDate, finishDate);
    }

    @Override
    public String before(LocalDate lastDate) {
        return superiorTasks.dateBefore(lastDate);
    }

    @Override
    public List<Integer> duplicates() {
        List<Integer> duplicateList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (duplicateList.contains(i)) {
                break;
            }
            for (int j = 0; j < tasks.size(); j++) {
                if (i != j && tasks.get(i).isDuplicate(tasks.get(j))) {
                    duplicateList.add(j);
                    if (!duplicateList.contains(i)) {
                        duplicateList.add(i);
                    }
                }
            }
        }
        Collections.sort(duplicateList);
        return duplicateList;
    }

    /**
     * Gives information if a list with the given name exists.
     * @param name The name of the list to be searched
     * @return The index of the list, if a list with this name exists {@code -1} if there is no list with this name
     */
    public int listIndex(String name) {
        for (int i = 0; i < taskLists.size(); i++) {
            if (name.equals(taskLists.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}