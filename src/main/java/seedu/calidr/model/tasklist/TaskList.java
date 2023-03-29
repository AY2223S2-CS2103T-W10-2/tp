package seedu.calidr.model.tasklist;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.params.Priority;

/**
 * Represents a task list manager that aids in storing and manipulating the
 * list of Tasks.
 */
public class TaskList implements ReadOnlyTaskList {
    private final UniqueTaskList tasks = new UniqueTaskList();

    public TaskList() {
    }

    /**
     * Creates a TaskList using the Tasks in the {@code toBeCopied}
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Resets the existing data of this {@code TaskList} with {@code newData}.
     *
     * @param newData The new data to update the task list.
     */
    public void resetData(ReadOnlyTaskList newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
    }

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     *
     * @param task The task to check.
     * @return true if task already exists in the task list and false otherwise.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a given Task to the list of Tasks.
     *
     * @param task The Task to be added to the list of Tasks.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
    }

    /**
     * Deletes a Task from the list of Tasks.
     *
     * @param key The number to indicate which Task is to be deleted.
     */
    public void deleteTask(Task key) {
        tasks.remove(key);
    }

    public void setTaskPriority(Task target, Priority priority) {
        tasks.setTaskPriority(target, priority);
    }

    /**
     * Marks a particular Task in the list of Tasks, as done.
     *
     * @param task The number to indicate which Task is to be marked as done.
     */
    public void markTask(Task task) {
        tasks.mark(task);
    }

    /**
     * Marks a particular Task in the list of Tasks, as undone.
     *
     * @param task The number to indicate which Task is to be marked as undone.
     */
    public void unmarkTask(Task task) {
        tasks.unmark(task);
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskList
                && tasks.equals(((TaskList) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }

}
