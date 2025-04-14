# Simple Java Console To-Do List

A basic command-line To-Do List application written in Java. Allows users to add, view, complete, and remove tasks, with data persisted between sessions using Java Serialization.

## Features
*   Add new tasks
*   View all tasks with completion status (`[ ]` or `[X]`)
*   Mark tasks as complete
*   Remove tasks
*   Save tasks to a `tasks.dat` file automatically on exit
*   Load tasks from `tasks.dat` on startup

## Technologies Used
*   Java (Core SDK)
*   Java Collections Framework (`ArrayList`)
*   Java I/O (Serialization - `ObjectInputStream`, `ObjectOutputStream`)

## How to Compile and Run

1.  **Prerequisites:**
    *   Java Development Kit (JDK) installed (version 8 or higher recommended).
    *   Git (for cloning the repository).

2.  **Clone the Repository:**
    ```bash
    git clone https://github.com/YOUR_USERNAME/your-repo-name.git
    cd your-repo-name
    ```

3.  **Compile:**
    Open a terminal or command prompt in the project's root directory (`your-repo-name`). Create an output directory and compile the source files:
    ```bash
    mkdir bin
    javac -d bin src/main/java/com/yourusername/todolist/*.java
    ```

4.  **Run:**
    Execute the application using the `java` command, specifying the classpath:
    ```bash
    java -cp bin com.yourusername.todolist.Main
    ```

5.  Follow the on-screen menu prompts. Tasks will be saved to `tasks.dat` in the project root directory.