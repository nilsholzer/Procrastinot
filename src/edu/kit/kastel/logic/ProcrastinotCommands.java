package edu.kit.kastel.logic;

/**
 * Interface which contains all command methods for the Procrastinot class.
 * @author uhquw
 * @version 1.0.0
 */
public interface ProcrastinotCommands extends Executable {
    /**
     * Adds a new superior task to the data structure.
     * @param name The name of the task
     * @param secondArg Either a String representing the priority or the deadline of a task
     * @param date A String representing the deadline of a task, only if secondArg represents the priority
     * @return A String showing the user that the task could be executed and the id of the new added task
     */
    String add(String name, String secondArg, String date);

    /**
     * Adds a new list to the data structure.
     * @param name The name of the list
     * @return A String showing the user that the task could be executed
     */
    String addList(String name);

    /**
     * Tags a task with a tag.
     * @param id id of the task to be tagged
     * @param tag The tag the task is being tagged with
     * @return A String showing the user that the task could be executed
     */
    String tag(int id, String tag);

    /**
     * Tags a list with a tag.
     * @param name Name of the list to be tagged
     * @param tag The tag the task being tagged with
     * @return A String showing the user that the task could be executed
     */
    String tagList(String name, String tag);

    /**
     * Assigns one task to another task.
     * @param childId The id of the task, which gets assigned to another task
     * @param parentId The id of the task, which the other task is getting assigned to
     * @return A String showing the user that the task could be executed
     */
    String assign(int childId, int parentId);

    /**
     * Assigns a task to a specific list.
     * @param id The id of the task to be assigned to the list
     * @param name The name of the list, the task is being assigned to
     * @return A String showing the user that the task could be executed
     */
    String assignList(int id, String name);

    /**
     * Toggles a task and all its subtasks.
     * @param id The id of the task to be toggled
     * @return A String showing the user that the task could be executed and the amount of the subtasks of the task
     */
    String toggle(int id);

    /**
     * Changes the deadline of a specific task.
     * @param id The id of the task, which deadline is being changed
     * @param date A String representing the new deadline of the task
     * @return A String showing the user that the task could be executed
     */
    String changeDate(int id, String date);

    /**
     * Changes the priority of a specific task.
     * @param id The id of the task, which priority is being changed
     * @param priority A String representing the new priority of the task
     * @return A String showing the user that the task could be executed
     */
    String changePriority(int id, String priority);

    /**
     * Deletes a task and all its subtasks.
     * @param id The id of the task to be deleted
     * @return A String showing the user that the task could be executed and the amount of the deleted subtasks
     */
    String delete(int id);

    /**
     * Restores a deleted task.
     * @param id The id of the task being restored
     * @return A String showing the user that the task could be executed and the amount of the restored subtasks
     */
    String restore(int id);

    /**
     * Shows a task and all its subtasks.
     * @param id The id of the task to be showed
     * @return A list of all the tasks that are being showed in certain order
     */
    String show(int id);

    /**
     * Shows all tasks that have to be done.
     * @return A list of all the tasks that have to be done and their subtasks
     */
    String todo();

    /**
     * Lists all tasks that are part of a specific list.
     * @param name The name of the list
     * @return A list of all the tasks assigned to the specific list and their subtasks
     */
    String list(String name);

    /**
     * Lists all tasks that are tagged with a specific tag.
     * @param tag The specific tag, that the listed tasks are tagged with
     * @return A list of all the tasks with the specific tag and their subtasks
     */
    String taggedWith(String tag);

    /**
     * Lists all tasks that contains the given String in their name.
     * @param name The String the tasks name needs to contain
     * @return A list of all the tasks that contains the String and their subtasks
     */
    String find(String name);

    /**
     * Lists all tasks, whose deadline is within the next 7 days of the given date.
     * @param date A String representing the date where the deadline starts
     * @return A list of all the tasks, whose deadline is within the next 7 days of the given date, and all its subtasks
     */
    String upcoming(String date);

    /**
     * Lists all tasks, whose deadline is before or on the specific date.
     * @param date A String representing the date where the deadline ends
     * @return A list of all the tasks, whose deadline is before or on the specific date, and all its subtasks
     */
    String before(String date);

    /**
     * Lists all tasks, whose deadline is in between the 2 given dates.
     * @param startDate A String representing the start of the deadline
     * @param finishDate A String representing the end of the deadline
     * @return A list of all the tasks, whose deadline is in between the given dates, and all its subtasks
     */
    String between(String startDate, String finishDate);

    /**
     * Finds all tasks that have duplicates.
     * @return A String showing the amount of duplicated tasks and their id
     */
    String duplicates();
}
