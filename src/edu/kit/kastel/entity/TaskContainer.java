package edu.kit.kastel.entity;

import java.util.ArrayList;
import java.util.List;

public class TaskContainer {
    private final List<List<Task>> tasks;
    private final List<Task> high;
    private final List<Task> mid;
    private final List<Task> low;
    private final List<Task> noPriority;

    public TaskContainer() {
        tasks = new ArrayList<>();
        high = new ArrayList<>();
        tasks.add(high);
        mid = new ArrayList<>();
        tasks.add(mid);
        low = new ArrayList<>();
        tasks.add(low);
        noPriority = new ArrayList<>();
        tasks.add(noPriority);
    }

    public boolean isElement(Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            return high.contains(task);
        } else if (priority == Priority.MD) {
            return mid.contains(task);
        } else if (priority == Priority.LO) {
            return low.contains(task);
        } else {
            return noPriority.contains(task);
        }
    }

    public void assign(Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            high.add(task);
        } else if (priority == Priority.MD) {
            mid.add(task);
        } else if (priority == Priority.LO) {
            low.add(task);
        } else {
            noPriority.add(task);
        }
    }

    public void remove(Task task) {
        Priority priority = task.getPriority();
        if (priority == Priority.HI) {
            high.remove(task);
        } else if (priority == Priority.MD) {
            mid.remove(task);
        } else if (priority == Priority.LO) {
            low.remove(task);
        } else {
            noPriority.remove(task);
        }
    }
}
