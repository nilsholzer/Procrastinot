package edu.kit.kastel.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Children extends TaskContainer {
    private final List<List<Task>> children;

    public Children() {
        children = getTasks();
    }

    public int toggle(final int parentId) {
        int subtaskCount = 0;
        for (List<Task> priority : children) {
            for (Task task : priority) {
                subtaskCount += task.toggle(parentId);
            }
        }
        return subtaskCount;
    }

    public int delete(final Task deletedTask) {
        int subtaskCount = 0;
        for (List<Task> priority : children) {
            for (Task task : priority) {
                subtaskCount += task.delete(deletedTask);
            }
        }
        return subtaskCount;
    }

    public int restore(final int parentId) {
        int subtaskCount = 0;
        for (List<Task> list : children) {
            for (Task task : list) {
                subtaskCount += task.restore(parentId);
            }
        }
        return subtaskCount;
    }

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

    public List<Task> childrenWithName(String name) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.find(name));
            }
        }
        return list;
    }

    public List<Task> childrenWithTag(String tag) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.taggedWith(tag));
            }
        }
        return list;
    }

    public List<Task> childrenDateBetween(LocalDate startDate, LocalDate finishDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBetween(startDate, finishDate));
            }
        }
        return list;
    }

    public List<Task> childrenDateBefore(LocalDate lastDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> priority : children) {
            for (Task task : priority) {
                list.addAll(task.dateBefore(lastDate));
            }
        }
        return list;
    }
}
