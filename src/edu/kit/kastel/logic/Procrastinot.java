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
public class Procrastinot implements ProcrastinotCommands {
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
            hasDate(name, date, id, prio);
        } else {
            hasDate(name, secondArg, id, prio);
        }
        return "added " + id + ": " + name;
    }

    private void hasDate(String name, String date, int id, Priority prio) {
        if (!date.isEmpty()) {
            dataStructure.add(new Task(id, name, prio, createDate(date)));
        } else {
            dataStructure.add(new Task(id, name, prio));
        }
    }

    @Override
    public String addList(String name) {
        if (dataStructure.containsList(name) != -1) {
            throw new TaskException("A list with that name already exists");
        }
        dataStructure.addList(new TaskList(name));
        return "added " + name;
    }

    @Override
    public String tag(int id, String tag) {
        taskAvailable(id);
        String name = dataStructure.tag(id - 1, tag);
        return "tagged " + name + " with " + tag;
    }

    @Override
    public String tagList(String name, String tag) {
        int listIndex = dataStructure.containsList(name);
        if (listIndex == -1) {
            throw new TaskException("No list with this name exists");
        }
        dataStructure.tagList(listIndex, tag);
        return "tagged " + name + " with " + tag;
    }

    @Override
    public String assign(int childId, int parentId) {
        taskAvailable(childId);
        taskAvailable(parentId);
        if (childId == parentId) {
            throw new TaskException("Cannot assign a task to it´s own");
        }
        String[] names = dataStructure.assign(childId - 1, parentId - 1);
        return "assigned " + names[0] + " to " + names[1];
    }

    @Override
    public String assignList(int id, String name) {
        taskAvailable(id);
        int listIndex = dataStructure.containsList(name);
        if (listIndex == -1) {
            throw new TaskException("A list with that name does not exist");
        }
        String taskName = dataStructure.assignList(id - 1, listIndex);
        return "assigned " + taskName + " to " + name;
    }

    @Override
    public String toggle(int id) {
        taskAvailable(id);
        String[] taskInformation = dataStructure.toggle(id - 1);
        return "toggled " + taskInformation[0] + " and " + taskInformation[1] + " subtasks";
    }

    @Override
    public String changeDate(int id, String date) {
        taskAvailable(id);
        String name = dataStructure.changeDate(id - 1, createDate(date));
        return "changed " + name + " to " + date;
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
            return "changed " + name + " to ";
        }
        return "changed " + name + " to " + priority;
    }



    @Override
    public String delete(int id) {
        taskAvailable(id);
        String[] outputInformation = dataStructure.delete(id - 1);
        return "deleted " + outputInformation[0] + " and " + outputInformation[1] + " subtasks";
    }

    @Override
    public String restore(int id) {
        taskAvailable(id);
        String[] outputInformation = dataStructure.restore(id - 1);
        return "restored " + outputInformation[0] + " and " + outputInformation[1] + " subtasks";
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
        int listIndex = dataStructure.containsList(name);
        if (listIndex == -1) {
            throw new TaskException("A list with that name does not exist");
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
        LocalDate finishDate = createDate(date).plusDays(7);
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
        result.append("Found ").append(duplicateList.size()).append(" duplicates: ");
        for (int i = 0; i < duplicateList.size() - 1; i++) {
            result.append(duplicateList.get(i) + 1).append(", ");
        }
        result.append(duplicateList.get(duplicateList.size() - 1) + 1);
        return result.toString();
    }

    private void taskAvailable(int id) {
        if (id > dataStructure.taskAmount()) {
            throw new TaskException("There is no task with the given ID");
        }
    }
}
