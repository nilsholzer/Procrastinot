package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that contains methods & variables a Task & TaskList needs.
 * @author uhquw
 * @version 1.0.0
 */
public abstract class Entity {
    private final String name;
    private final List<String> tags;

    /**
     * Constructs a Entity.
     * @param name The name of the Entity
     */
    protected Entity(String name) {
        this.name = name;
        tags = new ArrayList<>();
    }

    /**
     * Gets the name of the entity.
     * @return A String representing the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a new String to tags, if tags does not contain the tag yet.
     * @param tag The String to add to tags
     * @throws TaskException if tags contains already the given parameter tag
     */
    public void tag(String tag) {
        if (tags.contains(tag)) {
            throw new TaskException(name + " already contains given tag");
        }
        tags.add(tag);
    }

    /**
     * Gets the tags of the entity.
     * @return A list of Strings representing the tags of the entity
     */
    protected List<String> getTags() {
        return tags;
    }

    /**
     * Assigns the task to the entity.
     * @param task The Task to be assigned to the entity
     */
    abstract void assign(Task task);
}
