package com.narekusei.todolist;

import java.io.Serializable;

public class Task implements Serializable {

    // Helps ensure compatibility during deserialization if the class structure changes.
    private static final long serialVersionUID = 1L;

    private String description;
    private boolean isDone;

    /**
     * Constructs a new Task.
     * @param description The description of the task (cannot be null or empty).
     * @throws IllegalArgumentException if description is invalid.
     */
    public Task(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty.");
        }
        this.description = description.trim(); // Store trimmed description
        this.isDone = false; // New tasks are initially not done
    }

    // --- Getters ---
    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    // --- Setter ---
    /**
     * Marks the task as done or not done.
     * @param done true to mark as done, false otherwise.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns a user-friendly string representation of the task,
     * including its completion status.
     * Example: "[X] Buy groceries" or "[ ] Write report"
     * @return Formatted string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
