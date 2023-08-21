package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Task extends Entity {
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

    public Task(final int id, final String name, final Priority priority) {
        this(id, name, priority, null);
    }
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
    public void assign(Task task) {
        isDeleted("assign a task to");
        if (!children.isElement(task)) {
            task.getAssigned(this);
            children.assign(task);
        } else {
            throw new TaskException("Task is already assigned to given task");
        }
    }

    private void getAssigned(Task parentTask) {
        if (parent != null)  {
            parent.removeChild(this);
        }
        parent = parentTask;
    }

    private void removeChild(Task task) {
        children.remove(task);
    }

    public int toggle(final int id) {
        isDeleted("toggle");
        if (this.id == id) {
            if (currentState.equals(State.OPEN)) {
                currentState = State.CLOSED;
            } else {
                currentState = State.OPEN;
            }
            return children.toggle(id);
        } else {
            currentState = parent.currentState;
            return children.toggle(id) + 1;
        }
    }

    public int delete(final Task task) {
        isDeleted("restore");
        restoreState = currentState;
        currentState = State.DELETED;
        if (this == task) {
            if (parent != null) {
                parent.removeChild(this);
            }
            return children.delete(task);
        } else {
            return children.delete(task) + 1;
        }
    }

    public int restore(final int id) {
        if (currentState != State.DELETED) {
            throw new TaskException("You cannot restore an active task");
        }
        currentState = restoreState;
        if (this.id == id) {
            if (parent != null && parent.currentState != State.DELETED) {
                parent.assign(this);
            } else {
                parent = null;
            }
            return children.restore(id);
        } else {
            return children.restore(id) + 1;
        }
    }

    public String show(final int whitespaceCount) {
        isDeleted("show");
        StringBuilder result = new StringBuilder();
        result.append(toString(whitespaceCount));
        result.append(children.show(whitespaceCount));

        return result.toString();
    }

    private String toString(final int whitespaceCount) {
        StringBuilder result = new StringBuilder();
        result.append("  ".repeat(Math.max(0, whitespaceCount)));
        result.append("-").append(currentState.abbreviation).append(name).append(priority.abbreviation);
        if (!tags.isEmpty()) {
            result.append(": (");
            for (int tagCount = 0; tagCount < tags.size() - 1; tagCount++) {
                result.append(tags.get(tagCount)).append(", ");
            }
            result.append(tags.get(tags.size() - 1)).append(")");
        }
        if (date != null) {
            if (tags.isEmpty()) {
                result.append(":");
            }
            result.append(" --> ");
            result.append(date.getYear()).append("-");
            if (date.getMonthValue() < 10) {
                result.append(0);
            }
            result.append(date.getMonthValue()).append("-").append(date.getDayOfMonth());
        }
        return result.toString();
    }

    private void isDeleted(String errorMessage) {
        if (currentState == State.DELETED) {
            throw new TaskException("You cannot " + errorMessage + " a deleted task");
        }
    }

    public boolean hasOpenedChildren() {
        return currentState == State.OPEN || children.hasOpenedChildren();
    }

    public StringBuilder todo(final int whitespaceCount) {
        StringBuilder result = new StringBuilder();
        result.append(toString(whitespaceCount));
        result.append(children.todo(whitespaceCount));
        return result;
    }

    public boolean isInList(Task task) {
        return task.equals(this) || children.isInList(task);
    }

    public boolean isAParent(Task task) {
        if (parent == null) {
            return false;
        } else if (task.equals(parent)) {
            return true;
        }
        return parent.isAParent(task);
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if (parent != null) {
            parent.removeChild(this);
            this.priority = priority;
            parent.assign(this);
        } else {
            this.priority = priority;
        }
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void assignList(int listIndex) {
        assignedLists.add(listIndex);
    }
    public List<Integer> getAssignedLists() {
        return assignedLists;
    }
    public List<Task> find(String name) {
        List<Task> list = new ArrayList<>();
        if (this.name.contains(name)) {
            list.add(this);
        } else {
            list.addAll(children.childrenWithName(name));
        }
        return list;
    }

    public List<Task> taggedWith(String tag) {
        List<Task> list = new ArrayList<>();
        for (String taskTag : tags) {
            if (taskTag.equals(tag)) {
                list.add(this);
            }
        }
        if (list.isEmpty()) {
            list.addAll(children.childrenWithTag(tag));
        }
        return list;
    }

    public List<Task> dateBetween(LocalDate startDate, LocalDate finishDate) {
        List<Task> list = new ArrayList<>();
        if (date.isBefore(finishDate) && date.isAfter(startDate)) {
            list.add(this);
        } else {
            list.addAll(children.childrenDateBetween(startDate, finishDate));
        }
        return list;
    }

    public List<Task> dateBefore(LocalDate lastDate) {
        List<Task> list = new ArrayList<>();
        if (date.isBefore(lastDate)) {
            list.add(this);
        } else {
            list.addAll(children.childrenDateBefore(lastDate));
        }
        return list;
    }

    public boolean isDuplicate(Task task) {
        return ((date == null || task.date == null) || date.equals(task.date)) && name.equals(task.getName());
    }

    public Task getParent() {
        return parent;
    }
}
