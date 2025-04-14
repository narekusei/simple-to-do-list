package com.narekusei.todolist;

/**
 * The main entry point for the To-Do List application.
 */
public class Main {

    /**
     * Creates a TodoListManager and starts the application loop.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Simple Console To-Do List!");
        System.out.println("=======================================");

        // Create the manager instance (which loads tasks in its constructor)
        TodoListManager manager = new TodoListManager();

        // Start the main application loop
        manager.run();

        // The program exits when manager.run() finishes (user selects 0)
        System.out.println("Application finished.");
    }
}