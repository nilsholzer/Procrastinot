package edu.kit.kastel.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A TaskContainer that only contains children of one Task.
 * @author uhquw
 * @version 1.0.2
 */
public final class Children extends TaskContainer implements TaskInterface {
    private final List<List<Task>> children;

    /**
     * Constructs a TaskContainer that only contains children of one task.
     */
    public Children() {
        children = getTasks();
    }

    @Override
    public StringBuilder show(final int whitespaceCount) {
        int updatedWhitespaceCount = whitespaceCount + 1;
        StringBuilder result = new StringBuilder();
        for (List<Task> priorities : children) {
            for (Task task : priorities) {
                result.append(System.lineSeparator()).append(task.show(updatedWhitespaceCount));
            }
        }
        return result;
    }
    @Override
    public int toggle(final int toggleId) {
        int subtaskCount = 0;
        for (List<Task> priority : children) {
            for (Task task : priority) {
                subtaskCount += task.toggle(toggleId);
            }
        }
        return subtaskCount;
    }

    @Override
    public List<Integer> delete(final int deleteId, List<Integer> list) {
        for (List<Task> priority : children) {
            for (Task task : priority) {
                List<Integer> updatedList = task.delete(deleteId, list);
                list.clear();
                list.addAll(updatedList);
            }
        }
        return list;
    }

    @Override
    public List<Integer> restore(final int restoreId, final List<Integer> list) {
        for (List<Task> priority : children) {
            for (Task task : priority) {
                List<Integer> updatedList = task.restore(restoreId, list);
                list.clear();
                list.addAll(updatedList);
            }
        }
        return list;
    }

    @Override
    public boolean hasOpenedChildren() {
        for (List<Task> priority : children) {
            for (Task task : priority) {
                if (task.hasOpenedChildren()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Task> find(final String name) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.find(name));
            }
        }
        return list;
    }

    @Override
    public List<Task> taggedWith(final String tag) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.taggedWith(tag));
            }
        }
        return list;
    }

    @Override
    public List<Task> dateBetween(final LocalDate startDate, final LocalDate finishDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBetween(startDate, finishDate));
            }
        }
        return list;
    }

    @Override
    public List<Task> dateBefore(final LocalDate lastDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBefore(lastDate));
            }
        }
        return list;
    }
    @Override
    public void assignList(final int listIndex) {
        for (List<Task> priority : children) {
            for (Task task : priority) {
                task.assignList(listIndex);
            }
        }
    }
}
