package edu.kit.kastel.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A TaskContainer that only contains children of one Task.
 * @author uhquw
 * @version 1.0.0
 */
public class Children extends TaskContainer implements TaskInterface {
    private final List<List<Task>> children;

    /**
     * Constructs a TaskContainer that only contains children of one task.
     */
    public Children() {
        children = getTasks();
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
    public int delete(final Task deletedTask) {
        int subtaskCount = 0;
        for (List<Task> priority : children) {
            for (Task task : priority) {
                subtaskCount += task.delete(deletedTask);
            }
        }
        return subtaskCount;
    }

    @Override
    public int restore(final int restoreId) {
        int subtaskCount = 0;
        for (List<Task> list : children) {
            for (Task task : list) {
                subtaskCount += task.restore(restoreId);
            }
        }
        return subtaskCount;
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
    public List<Task> find(String name) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.find(name));
            }
        }
        return list;
    }

    @Override
    public List<Task> taggedWith(String tag) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.taggedWith(tag));
            }
        }
        return list;
    }

    @Override
    public List<Task> dateBetween(LocalDate startDate, LocalDate finishDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBetween(startDate, finishDate));
            }
        }
        return list;
    }

    @Override
    public List<Task> dateBefore(LocalDate lastDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBefore(lastDate));
            }
        }
        return list;
    }
}
