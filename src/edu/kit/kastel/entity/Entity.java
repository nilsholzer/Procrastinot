package edu.kit.kastel.entity;

import edu.kit.kastel.TaskException;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private final String name;
    private final List<String> tags;

    protected Entity(String name) {
        this.name = name;
        tags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void tag(String tag) {
        if (tags.contains(tag)) {
            throw new TaskException(name + " already contains given tag");
        }
        tags.add(tag);
    }

    protected List<String> getTags() {
        return tags;
    }

    abstract void assign(Task task);
    abstract int delete(Task task);

    //everything from TaskInterface apart from assign as normal method
    //rest is abstract
    //Also make Lists abstract
    //Try to make Task.java less methods anyhow!!
}
