package com.narekusei.todolist;
import java.io.*; // For FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream, IOException
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // For reading user input

/**
 * Manages the list of tasks, handles user interaction,
 * and performs file saving/loading.
 */
public class TodoListManager {

    // The list to hold all Task objects
    private final List<Task> tasks;
    // Scanner for reading input from the console
    private final Scanner scanner;
    // Constant for the filename where tasks are stored
    private static final String DATA_FILE_NAME = "tasks.dat";

    /**
     * Constructor: Initializes the task list, scanner, and loads tasks from file.
     */
    public TodoListManager() {
        // Use try-with-resources for the scanner to ensure it's closed
        this.scanner = new Scanner(System.in);
        // Load existing tasks from the data file
        this.tasks = loadTasksFromFile();
    }

    /**
     * The main loop that runs the application menu and processes user choices.
     */
    public void run() {
        int choice = -1; // Initialize choice to enter the loop

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine(); // Read the whole line

            try {
                choice = Integer.parseInt(input); // Try to convert input to an integer

                switch (choice) {
                    case 1 -> viewTasks();
                    case 2 -> addTask();
                    case 3 -> markTaskComplete();
                    case 4 -> removeTask();
                    case 0 -> {
                        saveTasksToFile(); // Save before exiting
                        System.out.println("Exiting application. Tasks saved.");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                // Handle cases where input is not a valid number
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
            } catch (Exception e) {
                // Catch any other unexpected errors during operation
                System.err.println("An unexpected error occurred: " + e.getMessage());
                // Consider logging the stack trace for debugging: e.printStackTrace();
            }

            // Add a small pause or prompt before showing the menu again (optional)
            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine(); // Consume the Enter press
            }

        } while (choice != 0);

        // Scanner is closed automatically if initialized outside constructor with try-with-resources,
        // but since it's needed for the lifetime of run(), close it here.
        scanner.close();
    }

    // --- Private Helper Methods ---

    private void displayMenu() {
        System.out.println("\n--- To-Do List Menu ---");
        System.out.println("1. View Tasks");
        System.out.println("2. Add Task");
        System.out.println("3. Mark Task as Complete");
        System.out.println("4. Remove Task");
        System.out.println("0. Save and Exit");
        System.out.println("-----------------------");
    }

    private void viewTasks() {
        System.out.println("\n--- Your Tasks ---");
        if (tasks.isEmpty()) {
            System.out.println("Your to-do list is empty!");
        } else {
            // Display tasks with a 1-based index for user convenience
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i)); // Uses Task's toString() method
            }
        }
        System.out.println("------------------");
    }

    private void addTask() {
        System.out.print("Enter the description for the new task: ");
        String description = scanner.nextLine();
        try {
            Task newTask = new Task(description); // Validation happens in Task constructor
            tasks.add(newTask);
            System.out.println("Task added successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding task: " + e.getMessage());
        }
    }

    private void markTaskComplete() {
        viewTasks(); // Show tasks so the user can choose by number
        if (tasks.isEmpty()) return; // Nothing to mark

        System.out.print("Enter the number of the task to mark as complete: ");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());
            // Validate the input number against the list size
            if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                Task task = tasks.get(taskNumber - 1); // Adjust to 0-based index
                if (!task.isDone()) {
                    task.setDone(true);
                    System.out.println("Task \"" + task.getDescription() + "\" marked as complete.");
                } else {
                     System.out.println("Task is already complete.");
                }
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IndexOutOfBoundsException e) {
            // This shouldn't happen with the check above, but good practice
            System.out.println("Error: Task number out of bounds.");
        }
    }

    private void removeTask() {
        viewTasks(); // Show tasks so the user can choose by number
        if (tasks.isEmpty()) return; // Nothing to remove

        System.out.print("Enter the number of the task to remove: ");
         try {
            int taskNumber = Integer.parseInt(scanner.nextLine());
             // Validate the input number
            if (taskNumber >= 1 && taskNumber <= tasks.size()) {
                // Remove task using 0-based index and store the removed task to show its description
                Task removedTask = tasks.remove(taskNumber - 1);
                System.out.println("Task \"" + removedTask.getDescription() + "\" removed successfully.");
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IndexOutOfBoundsException e) {
             System.out.println("Error: Task number out of bounds.");
        }
    }

    // --- File I/O Methods ---

    /**
     * Saves the current list of tasks to the data file using Object Serialization.
     */
    private void saveTasksToFile() {
        // Use try-with-resources to ensure the output stream is closed automatically
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))) {
            oos.writeObject(tasks); // Write the entire list object
            // System.out.println("Tasks saved to " + DATA_FILE_NAME); // Optional: confirmation message
        } catch (IOException e) {
            // Handle errors during file writing
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Loads the list of tasks from the data file.
     * If the file doesn't exist or an error occurs, returns an empty list.
     * @return The loaded list of tasks, or a new empty list if loading fails.
     */
    @SuppressWarnings("unchecked") // Suppress warning for the unavoidable cast
    private List<Task> loadTasksFromFile() {
        List<Task> loadedTasks = new ArrayList<>(); // Start with an empty list
        File dataFile = new File(DATA_FILE_NAME);

        if (dataFile.exists()) { // Only try to load if the file exists
            // Use try-with-resources for the input stream
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                Object readObject = ois.readObject();
                // Check if the read object is actually a List
                if (readObject instanceof List) {
                   loadedTasks = (List<Task>) readObject; // Unchecked cast
                   System.out.println("Tasks loaded successfully from " + DATA_FILE_NAME);
                } else {
                    System.err.println("Warning: Data file does not contain a valid task list. Starting fresh.");
                }
            } catch (FileNotFoundException e) {
                // This case is handled by dataFile.exists(), but good to catch defensively
                 System.out.println("Data file not found. Starting with an empty list.");
            } catch (IOException e) {
                System.err.println("Error reading tasks from file: " + e.getMessage());
                // Data might be corrupted, start fresh
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Task class definition not found or has changed. Starting fresh.");
                // Could happen if Task.java changed incompatibly since last save
            }
        } else {
             System.out.println("No existing task file found ("+DATA_FILE_NAME+"). Starting with an empty list.");
        }
        return loadedTasks; // Return the loaded list or the newly created empty list
    }
}