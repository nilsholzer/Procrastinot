package edu.kit.kastel.entity;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface that contains methods that a Task and its children need.
 * @author uhquw
 * @version 1.0.0
 */
public interface TaskInterface {
    /**
     * Sets a task´s, and its children´s, currentState to the opposite state of the currentState of the task with the given id.
     * @param toggleId The id of the task to be toggled
     * @return An Integer giving the amount of subtasks of the task with the given id
     */
    int toggle(int toggleId);

    /**
     * Deletes an active task and its children.
     * @param deleteId The id of the task to be deleted
     * @param list A list where all the results are being saved in
     * @return A list containing the amount of children of the deleted task and all the ids of the tasks, which are in lists
     */
    List<Integer> delete(int deleteId, List<Integer> list);

    /**
     * Restores a deleted task and its children.
     * @param restoreId The id of the task to be restored
     * @param list A list where all the results are being saved in
     * @return An Integer giving the amount of subtasks of the task with the given id
     */
    List<Integer> restore(int restoreId, List<Integer> list);

    /**
     * Searches, if a Task´s or one of the subtask´s state is open.
     * @return A boolean representing, if a Task or one of the subtasks´ state is open
     */
    boolean hasOpenedChildren();

    /**
     * Lists all the tasks, whose name contains the given String.
     * @param name The String that is searched for in the tasks name
     * @return A list of tasks, whose name contains the given String
     */
    List<Task> find(String name);

    /**
     * Lists all the tasks, whose tags contains the given tag.
     * @param tag The String that is searched for in the tasks tags
     * @return A list of tasks, whose tags contains the given tag
     */
    List<Task> taggedWith(String tag);

    /**
     * Lists all the tasks, whose deadline is in between the two given dates.
     * @param startDate The date one day before the deadline should at least be
     * @param finishDate The date one day after the deadline should at latest be
     * @return A list of tasks, whose deadline is in between the two given dates
     */
    List<Task> dateBetween(LocalDate startDate, LocalDate finishDate);

    /**
     * Lists all the tasks, whose deadline is before the given date.
     * @param lastDate The date one day after the deadline should at latest be
     * @return A list of all the tasks, whose deadline is before the given date
     */
    List<Task> dateBefore(LocalDate lastDate);
}
