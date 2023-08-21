package edu.kit.kastel.logic;

import edu.kit.kastel.entity.Priority;
import edu.kit.kastel.entity.Task;
import edu.kit.kastel.entity.TaskList;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface that contains all the method for the data structure.
 * @author uhquw
 * @version 1.0.0
 */
public interface DataStructureCommands {
    /**
     * Adds a task to the tasks and superiorTasks list.
     * @param task The Task that is being added
     */
    void add(Task task);

    /**
     * Adds a list to the taskLists list.
     * @param taskList The TaskList that is being added
     */
    void addList(TaskList taskList);

    /**
     * Tags the task with the given index with the given tag.
     * @param index The index of the task that is being tagged
     * @param tag The String the task is being tagged with
     * @return The name of the task being tagged
     */
    String tag(int index, String tag);

    /**
     * Tags the list with the given index with the given tag.
     * @param listIndex The index of the list that is being tagged
     * @param tag The String the list is being tagged with
     */
    void tagList(int listIndex, String tag);

    /**
     * Assigns the task with the childIndex to the task with the parentIndex.
     * @param childIndex The index of the task that is being assigned to the other task
     * @param parentIndex The index of the task that is getting the other task assigned to
     * @return The names of both the tasks
     */
    String[] assign(int childIndex, int parentIndex);

    /**
     * Assigns the task with the given index to the list with the given index.
     * @param index The index of the task that is being assigned
     * @param listIndex The index of the list that is getting the task assigned to
     * @return The name of the task that is being assigned to
     */
    String assignList(int index, int listIndex);

    /**
     * Toggles the task with the given index and all its subtasks.
     * @param index The index of the task being toggled
     * @return The name of the task and the number of its subtasks
     */
    String[] toggle(int index);

    /**
     * Changes the deadline of the task with the given index.
     * @param index The index of the task
     * @param date The date the deadline is being changed to
     * @return The name of the task
     */
    String changeDate(int index, LocalDate date);

    /**
     * Changes the priority of the task with the given index.
     * @param index The index of the task
     * @param priority The priority the tasks priority is being changed to
     * @return The name of the task
     */
    String changePriority(int index, Priority priority);

    /**
     * Deletes the task with the given index and all its subtasks.
     * @param index The index of the task to be deleted
     * @return The name of the task and the number of its subtasks
     */
    String[] delete(int index);

    /**
     * Restores a deleted task with the given index and all its subtasks.
     * @param index The index of the task to be restored
     * @return The name of the task and the number of its subtasks
     */
    String[] restore(int index);

    /**
     * Lists the task with the given index and all its subtasks.
     * @param index The index of the task to be shown
     * @return A visual representation of the task and all its subtasks
     */
    String show(int index);

    /**
     * Lists all tasks that are opened or have subtasks that are opened.
     * @return A visual representation of all the tasks and its opened subtasks
     */
    String todo();

    /**
     * Lists all the tasks that are assigned to the list with the given index.
     * @param listIndex The index of the list
     * @return A visual representation of all the tasks that are assigned to the list and their subtasks
     */
    String list(int listIndex);

    /**
     * Lists all the tasks that are tagged with the given tag.
     * @param tag The tag the listed tasks are tagged with
     * @return A visual representation of all the tasks that are tagged with the given tag and their subtasks
     */
    String taggedWith(String tag);

    /**
     * Lists all the tasks, whose name contains the given String.
     * @param name The String that is contained in the tasks that are listed
     * @return A visual representation of all the tasks, whose name contains the given String, and their subtasks
     */
    String find(String name);

    /**
     * Lists all the tasks, whose deadline is in between the 2 dates.
     * @param startDate The earliest date, where the tasks deadline can be
     * @param finishDate The latest date, where the tasks deadline can be
     * @return A visual representation of all the tasks, whose deadline is in between the 2 dates, and their subtasks
     */
    String between(LocalDate startDate, LocalDate finishDate);

    /**
     * Lists all the tasks, whose deadline is on or before the given date.
     * @param lastDate The latest date, where the tasks deadline can be
     * @return A visual representation of all the tasks, whose deadline is on or before the given date, and their subtasks
     */
    String before(LocalDate lastDate);

    /**
     * Finds for every task a task with the same name and the same deadline.
     * @return The amount of duplicated tasks and their ids
     */
    List<Integer> duplicates();

}
