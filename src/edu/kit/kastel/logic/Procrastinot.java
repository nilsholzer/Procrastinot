package edu.kit.kastel.logic;

import edu.kit.kastel.TaskException;
import edu.kit.kastel.entity.Priority;
import edu.kit.kastel.entity.Task;
import edu.kit.kastel.entity.TaskList;
import edu.kit.kastel.ui.Expressions;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles all commands and the logic for the application.
 * @author uhquw
 * @version 1.0.0
 */
public final class Procrastinot implements ProcrastinotCommands {
    private static final String ADDED_LIST = "added %1$s";
    private static final String ADDED = ADDED_LIST + ": %2$s";
    private static final String TAGGED = "tagged %1$s with %2$s";
    private static final String ASSIGNED = "assigned %1$s to %2$s";
    private static final String SUBTASKS = " %1$s and %2$s subtasks";
    private static final String TOGGLED = "toggled" + SUBTASKS;
    private static final String CHANGED = "changed %1$s to %2$s";
    private static final String DELETED = Expressions.DELETED + SUBTASKS;
    private static final String RESTORED = "restored" + SUBTASKS;
    private static final String DUPLICATED = "Found %s duplicates: ";
    private static final String COMMA = ", ";
    private static final String LIST_NOT_EXIST = "A list with that name does not exist";
    private static final int SEVEN = 7;
    private ExecutionState executionState;
    private final DataStructure dataStructure;

    /**
     * Constructs a new, running application.
     */
    public Procrastinot() {
        executionState = ExecutionState.RUNNING;
        dataStructure = new DataStructure();
    }

    @Override
    public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    @Override
    public void quit() {
        executionState = ExecutionState.CLOSED;
    }

    @Override
    public String add(String name, String secondArg, String date) {
        int id = dataStructure.taskAmount() + 1;
        Priority prio = Priority.NO_PRIORITY;
        Pattern patternPriority = Pattern.compile(Expressions.PRIORITY);
        Matcher matcherPriority = patternPriority.matcher(" " + secondArg);
        if (matcherPriority.matches()) {
            prio = Priority.valueOf(secondArg);
            taskConstructor(name, date, id, prio);
        } else {
            taskConstructor(name, secondArg, id, prio);
        }
        return String.format(ADDED, id, name);
    }

    private void taskConstructor(String name, String date, int id, Priority prio) {
        if (!date.isEmpty()) {
            dataStructure.add(new Task(id, name, prio, createDate(date)));
        } else {
            dataStructure.add(new Task(id, name, prio));
        }
    }

    @Override
    public String addList(String name) {
        if (dataStructure.listIndex(name) != -1) {
            throw new TaskException("A list with that name already exists");
        }
        dataStructure.addList(new TaskList(name));
        return String.format(ADDED_LIST, name);
    }

    @Override
    public String tag(int id, String tag) {
        taskAvailable(id);
        return String.format(TAGGED, dataStructure.tag(id - 1, tag), tag);
    }

    @Override
    public String tagList(String name, String tag) {
        int listIndex = dataStructure.listIndex(name);
        if (listIndex == -1) {
            throw new TaskException(LIST_NOT_EXIST);
        }
        dataStructure.tagList(listIndex, tag);
        return String.format(TAGGED, name, tag);
    }

    @Override
    public String assign(int childId, int parentId) {
        taskAvailable(childId);
        taskAvailable(parentId);
        if (childId == parentId) {
            throw new TaskException("Cannot assign a task to it´s own");
        }
        String[] names = dataStructure.assign(childId - 1, parentId - 1);
        return String.format(ASSIGNED, names[0], names[1]);
    }

    @Override
    public String assignList(int id, String name) {
        taskAvailable(id);
        int listIndex = dataStructure.listIndex(name);
        if (listIndex == -1) {
            throw new TaskException(LIST_NOT_EXIST);
        }
        String taskName = dataStructure.assignList(id - 1, listIndex);
        return String.format(ASSIGNED, taskName, name);
    }

    @Override
    public String toggle(int id) {
        taskAvailable(id);
        String[] taskInformation = dataStructure.toggle(id - 1);
        return String.format(TOGGLED, taskInformation[0], taskInformation[1]);
    }

    @Override
    public String changeDate(int id, String date) {
        taskAvailable(id);
        String name = dataStructure.changeDate(id - 1, createDate(date));
        return String.format(CHANGED, name, date);
    }

    private LocalDate createDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeException exception) {
            throw new TaskException("There is a problem with the given Date");
        }
    }

    @Override
    public String changePriority(int id, String priority) {
        taskAvailable(id);
        Priority prio = Priority.valueOf(priority);
        String name = dataStructure.changePriority(id - 1, prio);
        if (prio == Priority.NO_PRIORITY) {
            return String.format(CHANGED, name, prio.abbreviation);
        }
        return String.format(CHANGED, name, prio);
    }



    @Override
    public String delete(int id) {
        taskAvailable(id);
        String[] outputInformation = dataStructure.delete(id - 1);
        return String.format(DELETED, outputInformation[0], outputInformation[1]);
    }

    @Override
    public String restore(int id) {
        taskAvailable(id);
        String[] outputInformation = dataStructure.restore(id - 1);
        return String.format(RESTORED, outputInformation[0], outputInformation[1]);
    }

    @Override
    public String show(int id) {
        taskAvailable(id);
        return dataStructure.show(id - 1);
    }

    @Override
    public String todo() {
        return dataStructure.todo();
    }

    @Override
    public String list(String name) {
        int listIndex = dataStructure.listIndex(name);
        if (listIndex == -1) {
            throw new TaskException(LIST_NOT_EXIST);
        }
        return dataStructure.list(listIndex);
    }

    @Override
    public String taggedWith(String tag) {
        return dataStructure.taggedWith(tag);
    }

    @Override
    public String find(String name) {
        return dataStructure.find(name);
    }

    @Override
    public String upcoming(String date) {
        LocalDate startDate = createDate(date).minusDays(1);
        LocalDate finishDate = createDate(date).plusDays(SEVEN);
        return dataStructure.between(startDate, finishDate);
    }

    @Override
    public String before(String date) {
        return dataStructure.before(createDate(date).plusDays(1));
    }

    @Override
    public String between(String startDate, String finishDate) {
        LocalDate firstDate = createDate(startDate).minusDays(1);
        LocalDate lastDate = createDate(finishDate).plusDays(1);
        return dataStructure.between(firstDate, lastDate);
    }

    @Override
    public String duplicates() {
        List<Integer> duplicateList = dataStructure.duplicates();
        StringBuilder result = new StringBuilder();
        result.append(String.format(DUPLICATED, duplicateList.size()));
        if (!duplicateList.isEmpty()) {
            for (int i = 0; i < duplicateList.size() - 1; i++) {
                result.append(duplicateList.get(i) + 1).append(COMMA);
            }
            result.append(duplicateList.get(duplicateList.size() - 1) + 1);
        }

        return result.toString();
    }

    private void taskAvailable(int id) {
        if (id > dataStructure.taskAmount()) {
            throw new TaskException("There is no task with the given ID");
        }
    }
}
