package edu.kit.kastel.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PriorityList {
    private final List<List<Task>> children;
    private final List<Task> high;
    private final List<Task> mid;
    private final List<Task> low;
    private final List<Task> noPriority;

    public PriorityList() {
        children = new ArrayList<>();
        high = new ArrayList<>();
        children.add(high);
        mid = new ArrayList<>();
        children.add(mid);
        low = new ArrayList<>();
        children.add(low);
        noPriority = new ArrayList<>();
        children.add(noPriority);
    }

    public boolean isElement(Task task) {
        Priority priority = task.getPriority();
        if (priority.equals(Priority.HI)) {
            return high.contains(task);
        } else if (priority.equals(Priority.MD)) {
            return mid.contains(task);
        } else if (priority.equals(Priority.LO)) {
            return low.contains(task);
        } else {
            return noPriority.contains(task);
        }
    }

    public void assign(Task task) {
        Priority priority = task.getPriority();
        if (priority.equals(Priority.HI)) {
            high.add(task);
        } else if (priority.equals(Priority.MD)) {
            mid.add(task);
        } else if (priority.equals(Priority.LO)) {
            low.add(task);
        } else {
            noPriority.add(task);
        }
    }

    public void remove(Task task) {
        Priority priority = task.getPriority();
        if (priority.equals(Priority.HI)) {
            high.remove(task);
        } else if (priority.equals(Priority.MD)) {
            mid.remove(task);
        } else if (priority.equals(Priority.LO)) {
            low.remove(task);
        } else {
            noPriority.remove(task);
        }
    }

    public int toggle(final int parentId) {
        int subtaskCount = 0;
        for (List<Task> list : children) {
            for (Task task : list) {
                subtaskCount += task.toggle(parentId);
            }
        }
        return subtaskCount;
    }

    public int delete(final Task deletedTask) {
        int subtaskCount = 0;
        for (List<Task> list : children) {
            for (Task task : list) {
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
    public StringBuilder show(int whitespaceCount) {
        int updatedWhitespaceCount = whitespaceCount + 1;
        StringBuilder result = new StringBuilder();
        for (List<Task> list : children) {
            for (Task task : list) {
                result.append(System.lineSeparator()).append(task.show(updatedWhitespaceCount));
            }
        }
        return result;
    }

    public StringBuilder todo(int whitespaceCount) {
        int updatedWhitespaceCount = whitespaceCount + 1;
        StringBuilder result = new StringBuilder();
        for (List<Task> list : children) {
            for (Task task : list) {
                if (task.hasOpenedChildren()) {
                    result.append(System.lineSeparator()).append(task.todo(updatedWhitespaceCount));
                }
            }
        }
        return result;
    }

    public boolean hasOpenedChildren() {
        for (List<Task> list : children) {
            for (Task task : list) {
                if (task.hasOpenedChildren()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInList(Task searchedTask) {
        for (List<Task> list : children) {
            for (Task task : list) {
                if (task.isInList(searchedTask)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Task childIsInList(Task searchedTask) {
        for (List<Task> list : children) {
            for (Task task : list) {
                if (task.isAParent(searchedTask)) {
                    return task;
                }
            }
        }
        return null;
    }

    public String find(String name) {
        StringBuilder result = new StringBuilder();
        for (List<Task> list : children) {
            for (Task task : list) {
                List<Task> taskList = task.find(name);
                for (Task foundTask : taskList) {
                    result.append(System.lineSeparator()).append(foundTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 2).toString();
        }
        return "nein";
    }

    public List<Task> childrenWithName(String name) {
        List<Task> list = new ArrayList<>();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                list.addAll(task.find(name));
            }
        }
        return list;
    }

    public String taggedWith(String tag) {
        StringBuilder result = new StringBuilder();
        for (List<Task> list : children) {
            for (Task task : list) {
                List<Task> taskList = task.taggedWith(tag);
                for (Task foundTask : taskList) {
                    result.append(System.lineSeparator()).append(foundTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return "nein";
    }

    public List<Task> childrenWithTag(String tag) {
        List<Task> list = new ArrayList<>();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                list.addAll(task.taggedWith(tag));
            }
        }
        return list;
    }

    public String dateBetween(LocalDate startDate, LocalDate finishDate) {
        StringBuilder result = new StringBuilder();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                List<Task> list = task.dateBetween(startDate, finishDate);
                for (Task betweenTask : list) {
                    result.append(System.lineSeparator()).append(betweenTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 1).toString();
        }
        return "Nein!";
    }

    public List<Task> childrenDateBetween(LocalDate startDate, LocalDate finishDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                list.addAll(task.dateBetween(startDate, finishDate));
            }
        }
        return list;
    }

    public String dateBefore(LocalDate lastDate) {
        StringBuilder result = new StringBuilder();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                List<Task> list = task.dateBefore(lastDate);
                for (Task beforeTask : list) {
                    result.append(System.lineSeparator()).append(beforeTask.show(0));
                }
            }
        }
        if (!result.isEmpty()) {
            return result.delete(0, 2).toString();
        }
        return "Nein!";
    }

    public List<Task> childrenDateBefore(LocalDate lastDate) {
        List<Task> list = new ArrayList<>();
        for (List<Task> taskList : children) {
            for (Task task : taskList) {
                list.addAll(task.dateBefore(lastDate));
            }
        }
        return list;
    }
}
