package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;
import edu.kit.kastel.ui.Expressions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A task used in the application. Two tasks are identical, if their name and their deadline is identical.
 * @author uhquw
 * @version 1.3.5
 */
public final class Task extends Entity implements TaskInterface {
    private static final String DATE_SEPARATOR = "-";
    private static final String DELETED_ERROR = "You cannot %s a deleted task";
    private static final String ERROR_ASSIGN = Expressions.ASSIGN + " a task to";
    private static final int TEN = 10;
    private static final int HUNDRED = 100;
    private static final int THOUSAND = 1000;
    private static final String TAG_OPENER = ": (";
    private static final String COMMA = ", ";
    private static final String TAG_CLOSER = ")";
    private static final String OPENER = ":";
    private static final String DATE_OPENER = " --> ";
    private final String name;
    private final int id;
    private Task parent;
    private LocalDate date;
    private Priority priority;
    private final Children children;
    private State currentState;
    private State restoreState;
    private final List<Integer> assignedLists;
    private final List<String> tags;

    /**
     * Constructs a task with no deadline.
     * @param id The unique id of a task
     * @param name The name of the task
     * @param priority The priority of the task
     */
    public Task(final int id, final String name, final Priority priority) {
        this(id, name, priority, null);
    }

    /**
     * Constructs a task with a deadline.
     * @param id The unique id of a task
     * @param name The name of a task
     * @param priority The priority of task
     * @param date The deadline of a task
     */
    public Task(final int id, final String name, final Priority priority, final LocalDate date) {
        super(name);
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
        parent = null;
        children = new Children();
        currentState = State.OPEN;
        assignedLists = new ArrayList<>();
        tags = getTags();
    }

    @Override
    public void assign(final Task task) {
        deletedTask(ERROR_ASSIGN);
        if (!children.isElement(task)) {
            task.getAssigned(this);
            children.assign(task);
        } else {
            throw new TaskException("Task is already assigned to given task");
        }
    }

    private void getAssigned(final Task parentTask) {
        deletedTask(Expressions.ASSIGN);
        if (parent != null)  {
            parent.removeChild(this);
        }
        parent = parentTask;
    }

    private void removeChild(final Task task) {
        children.remove(task);
    }

    @Override
    public int toggle(final int toggleId) {
        deletedTask(Expressions.TOGGLE);
        if (this.id == toggleId) {
            if (currentState.equals(State.OPEN)) {
                currentState = State.CLOSED;
            } else {
                currentState = State.OPEN;
            }
            return children.toggle(toggleId);
        } else {
            currentState = parent.currentState;
            return children.toggle(toggleId) + 1;
        }
    }

    @Override
    public List<Integer> delete(final int deleteId, final List<Integer> list) {
        deletedTask(Expressions.DELETED);
        List<Integer> copyList = new ArrayList<>(list);
        restoreState = currentState;
        currentState = State.DELETED;
        if (!assignedLists.isEmpty()) {
            copyList.add(this.id);
        }
        if (this.id == deleteId) {
            if (parent != null) {
                parent.removeChild(this);
            }
            copyList.add(0, 0);
        } else {
            int childrenAmount = copyList.get(0);
            copyList.set(0, childrenAmount + 1);
        }
        return children.delete(deleteId, copyList);
    }

    @Override
    public List<Integer> restore(final int restoreId, final List<Integer> list) {
        if (currentState != State.DELETED) {
            throw new TaskException("You cannot restore an active task");
        }
        List<Integer> copyList = new ArrayList<>(list);
        currentState = restoreState;
        if (!assignedLists.isEmpty()) {
            copyList.add(this.id);
        }
        if (this.id == restoreId) {
            if (parent != null) {
                if (parent.currentState != State.DELETED) {
                    parent.assign(this);
                } else {
                    parent.removeChild(this);
                    parent = null;
                }
            }
            copyList.add(0, 0);
        } else {
            int childrenAmount = copyList.get(0) + 1;
            copyList.set(0, childrenAmount);
        }
        return children.restore(restoreId, copyList);
    }

    /**
     * Lists the task and all its subtasks indented.
     * @param whitespaceCount The amount of whitespaces, times 2, put in front of the task
     * @return A String containing the task and all its subtasks put to a String
     */
    public String show(final int whitespaceCount) {
        deletedTask(Expressions.SHOW);

        return toString(whitespaceCount) + children.show(whitespaceCount);
    }

    /**
     * Transforms a task in a String. Beginning with the amount of whitespaces, then showing if the task is done or not.
     * After that it´s showing the tasks name. If the task´s priority is not No_Priority it´s abreviation is shown.
     * If the task contains tags their are shown too.
     * If the task has a deadline it is being appended at the end of the vizualisation.
     * @param whitespaceCount The amount of whitespace, times 2, put in front of the visualization of the task
     * @return A visualization of the task following a specific formation
     */
    private String toString(final int whitespaceCount) {
        StringBuilder result = new StringBuilder();
        result.append("  ".repeat(Math.max(0, whitespaceCount)));
        result.append(DATE_SEPARATOR).append(currentState.getVisualization()).append(name).append(priority.getVisualization());
        if (!tags.isEmpty()) {
            result.append(TAG_OPENER);
            for (int tagCount = 0; tagCount < tags.size() - 1; tagCount++) {
                result.append(tags.get(tagCount)).append(COMMA);
            }
            result.append(tags.get(tags.size() - 1)).append(TAG_CLOSER);
        }
        if (date != null) {
            if (tags.isEmpty()) {
                result.append(OPENER);
            }
            result.append(DATE_OPENER);
            if (date.getYear() < THOUSAND) {
                result.append(0);
                if (date.getYear() < HUNDRED) {
                    result.append(0);
                    if (date.getYear() < TEN) {
                        result.append(0);
                    }
                }
            }
            result.append(date.getYear()).append(DATE_SEPARATOR);
            if (date.getMonthValue() < TEN) {
                result.append(0);
            }
            result.append(date.getMonthValue()).append(DATE_SEPARATOR);
            if (date.getDayOfMonth() < TEN) {
                result.append(0);
            }
            result.append(date.getDayOfMonth());
        }
        return result.toString();
    }

    /**
     * Checks if a task´s currentState is deleted or not.
     * @param errorMessage The specific error Message if the task is deleted
     */
    private void deletedTask(final String errorMessage) {
        if (currentState == State.DELETED) {
            throw new TaskException(String.format(DELETED_ERROR, errorMessage));
        }
    }

    @Override
    public boolean hasOpenedChildren() {
        return currentState == State.OPEN || children.hasOpenedChildren();
    }

    /**
     * Lists a task and all its subtasks, if all of them have themselves subtasks whose currentState is openend, or open themselves.
     * @param whitespaceCount The amount of whitespaces, times 2, put in front of the visualization of the task
     * @return A String containing the task and all, some or none of its subtasks visualised
     */
    public StringBuilder todo(final int whitespaceCount) {
        StringBuilder result = new StringBuilder();
        result.append(toString(whitespaceCount));
        result.append(children.todo(whitespaceCount));
        return result;
    }

    /**
     * Checks if the given task is this task or is one of the children of the task.
     * @param task The task being searched for
     * @return A boolean if the task is this task or one of the children is/contains this task
     */
    public boolean isInList(final Task task) {
        return task == this || children.isInList(task);
    }

    /**
     * Checks if the given task is a parent of a specific degree of this task.
     * @param searchedTask The task to look for
     * @return A boolean representing if the task is a parent of a specific degree
     */
    public boolean isAParent(final Task searchedTask) {
        Task parentSearcher = this;
        while (true) {
            if (parentSearcher.parent == null) {
                return false;
            } else if (searchedTask == parentSearcher.parent) {
                return true;
            }
            parentSearcher = parentSearcher.parent;
        }
    }

    /**
     * Gets the priority of a task.
     * @return The priority of the task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Changes the priority of a task and changes its position in the parents children list.
     * @param priority The priority the tasks priority should be changed to
     */
    public void setPriority(final Priority priority) {
        if (parent != null) {
            parent.removeChild(this);
            this.priority = priority;
            parent.assign(this);
        } else {
            this.priority = priority;
        }
    }

    /**
     * Changes the deadline of a task.
     * @param date The value the deadline should be changed to
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    @Override
    public void assignList(final int listIndex) {
        assignedLists.add(listIndex);
        children.assignList(listIndex);
    }

    /**
     * Gets the indices of the lists, the task got assigned to.
     * @return A list containing Integers representing the indices of the lists, the task got assigned to
     */
    public List<Integer> getAssignedLists() {
        return Collections.unmodifiableList(assignedLists);
    }
    @Override
    public List<Task> find(final String name) {
        List<Task> list = new ArrayList<>();
        if (this.name.contains(name)) {
            list.add(this);
        } else {
            list.addAll(children.find(name));
        }
        return list;
    }

    @Override
    public List<Task> taggedWith(final String tag) {
        List<Task> list = new ArrayList<>();
        for (String taskTag : tags) {
            if (taskTag.equals(tag)) {
                list.add(this);
            }
        }
        if (list.isEmpty()) {
            list.addAll(children.taggedWith(tag));
        }
        return list;
    }

    @Override
    public List<Task> dateBetween(final LocalDate startDate, final LocalDate finishDate) {
        if (currentState == State.DELETED) {
            return Collections.emptyList();
        }
        List<Task> list = new ArrayList<>();
        if (date != null && date.isBefore(finishDate) && date.isAfter(startDate)) {
            list.add(this);
        } else {
            list.addAll(children.dateBetween(startDate, finishDate));
        }
        return list;
    }

    @Override
    public List<Task> dateBefore(final LocalDate lastDate) {
        if (currentState == State.DELETED) {
            return Collections.emptyList();
        }
        List<Task> list = new ArrayList<>();
        if (date != null && date.isBefore(lastDate)) {
            list.add(this);
        } else {
            list.addAll(children.dateBefore(lastDate));
        }
        return list;
    }

    /**
     * Checks if a task has the same name as another task and if they have the same deadline.
     * If only one of the tasks has a deadline or none of it does, the deadline is irrelevant.
     * @param task The task this task is being compared to
     * @return A boolean if the requirements apply
     */
    public boolean isDuplicate(final Task task) {
        return ((date == null || task.date == null) || date.equals(task.date)) && name.equals(task.getName());
    }

    /**
     * Gets the parent of a task.
     * @return A task that is representing the parent of a task
     */
    public Task getParent() {
        return parent;
    }

    /**
     * Gets the currrent State of a Task.
     * @return A State Enum that represents the current State of a Task
     */
    public State getCurrentState() {
        return currentState;
    }
}
