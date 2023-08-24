package edu.kit.kastel.logic;

import edu.kit.kastel.entity.Priority;
import edu.kit.kastel.entity.State;
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
 * @version 1.0.1
 */
public final class DataStructure implements DataStructureCommands {
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
    public void addList(final TaskList taskList) {
        taskLists.add(taskList);
    }

    @Override
    public String tag(final int index, final String tag) {
        tasks.get(index).tag(tag);
        return tasks.get(index).getName();
    }

    @Override
    public void tagList(final int listIndex, final String tag) {
        taskLists.get(listIndex).tag(tag);
    }

    @Override
    public String[] assign(final int childIndex, final int parentIndex) {
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
    public String assignList(final int index, final int listIndex) {
        taskLists.get(listIndex).assign(tasks.get(index));
        tasks.get(index).assignList(listIndex);
        return tasks.get(index).getName();
    }

    @Override
    public String[] toggle(final int index) {
        String[] taskInformation = new String[2];
        taskInformation[0] = tasks.get(index).getName();
        taskInformation[1] = String.valueOf(tasks.get(index).toggle(index + 1));
        return taskInformation;
    }

    @Override
    public String changeDate(final int index, final LocalDate date) {
        tasks.get(index).setDate(date);
        return tasks.get(index).getName();
    }

    @Override
    public String changePriority(final int index, final Priority priority) {
        Task changedTask = tasks.get(index);
        List<Integer> assignedLists = changedTask.getAssignedLists();
        if (changedTask.getPriority() != priority) {
            if (superiorTasks.isElement(changedTask)) {
                superiorTasks.remove(changedTask);
                for (int listIndex :assignedLists) {
                    taskLists.get(listIndex).delete(changedTask);
                }
                changedTask.setPriority(priority);
                superiorTasks.assign(changedTask);
                for (int listIndex :assignedLists) {
                    taskLists.get(listIndex).restore(changedTask);
                }
            } else {
                for (int listIndex :assignedLists) {
                    taskLists.get(listIndex).delete(changedTask);
                }
                changedTask.setPriority(priority);
                for (int listIndex :assignedLists) {
                    taskLists.get(listIndex).restore(changedTask);
                }
            }
        }

        return tasks.get(index).getName();
    }

    @Override
    public String[] delete(final int index) {
        Task deletedTask = tasks.get(index);
        String[] deleteInformation = new String[2];
        deleteInformation[0] = deletedTask.getName();
        List<Integer> deletedList = deletedTask.delete(index + 1, new ArrayList<>());
        deleteInformation[1] = String.valueOf(deletedList.get(0));
        if (deletedList.size() > 1) {
            List<Integer> deletedListTasks = deletedList.stream().skip(1).toList();
            for (int taskIndex : deletedListTasks) {
                List<Integer> assignedLists = tasks.get(taskIndex - 1).getAssignedLists();
                for (int listIndex : assignedLists) {
                    taskLists.get(listIndex).delete(tasks.get(taskIndex - 1));
                }
            }
        }
        if (superiorTasks.isElement(deletedTask)) {
            superiorTasks.remove(deletedTask);
        }
        return deleteInformation;
    }

    @Override
    public String[] restore(final int index) {
        Task restoredTask = tasks.get(index);
        String[] restoreInformation = new String[2];
        restoreInformation[0] = restoredTask.getName();
        List<Integer> restoredList = restoredTask.restore(index + 1, new ArrayList<>());
        restoreInformation[1] = String.valueOf(restoredList.get(0));
        if (restoredTask.getParent() == null && !superiorTasks.isElement(restoredTask)) {
            superiorTasks.assign(restoredTask);
        }
        if (restoredList.size() > 1) {
            List<Integer> restoredListTasks = restoredList.stream().skip(1).toList();
            for (int taskIndex : restoredListTasks) {
                Task listTask = tasks.get(taskIndex - 1);
                List<Integer> assignedLists = listTask.getAssignedLists();
                for (int listIndex : assignedLists) {
                    taskLists.get(listIndex).restore(listTask);
                }
            }
        }
        return restoreInformation;
    }

    @Override
    public String show(final int index) {
        return tasks.get(index).show(0);
    }

    @Override
    public String todo() {
        StringBuilder result = superiorTasks.todo(-1);
        return result.delete(0, 1).toString();
    }

    @Override
    public String list(final int listIndex) {
        return taskLists.get(listIndex).list();
    }

    @Override
    public String taggedWith(final String tag) {
        return superiorTasks.taggedWith(tag);
    }

    @Override
    public String find(final String name) {
        return superiorTasks.find(name);
    }

    @Override
    public String between(final LocalDate startDate, final LocalDate finishDate) {
        return superiorTasks.dateBetween(startDate, finishDate);
    }

    @Override
    public String before(final LocalDate lastDate) {
        return superiorTasks.dateBefore(lastDate);
    }

    @Override
    public List<Integer> duplicates() {
        List<Integer> duplicateList = new ArrayList<>();
        for (int searchedTaskIndex = 0; searchedTaskIndex < tasks.size(); searchedTaskIndex++) {
            if (duplicateList.contains(searchedTaskIndex) || tasks.get(searchedTaskIndex).getCurrentState() == State.DELETED) {
                continue;
            }
            for (int comparedTaskIndex = 0; comparedTaskIndex < tasks.size(); comparedTaskIndex++) {
                if (searchedTaskIndex != comparedTaskIndex && tasks.get(searchedTaskIndex).isDuplicate(tasks.get(comparedTaskIndex))
                        && tasks.get(comparedTaskIndex).getCurrentState() != State.DELETED) {
                    duplicateList.add(comparedTaskIndex);
                    if (!duplicateList.contains(searchedTaskIndex)) {
                        duplicateList.add(searchedTaskIndex);
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
    public int listIndex(final String name) {
        for (int i = 0; i < taskLists.size(); i++) {
            if (name.equals(taskLists.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
}
