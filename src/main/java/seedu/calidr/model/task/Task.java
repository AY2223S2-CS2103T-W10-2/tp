package seedu.calidr.model.task;

import java.util.Optional;

import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Title;

/**
 * Represents a task.
 */
public abstract class Task {

    private final Title title;
    private Description description;
    private boolean isDone;
    private Priority priority;

    /**
     * Creates a Task object with the given title and MEDIUM priority.
     *
     * @param title The title of the Task.
     */
    public Task(Title title) {
        assert title != null;

        this.title = title;

        this.isDone = false;
        this.priority = Priority.MEDIUM;
    }


    public Title getTitle() {
        return this.title;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public void setPriority(Priority p) {
        assert p != null;
        this.priority = p;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns true if both Tasks have the same details.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return equals(otherTask);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String mark = this.isDone ? "X" : " ";
        sb.append("{")
                .append(this.priority.toString())
                .append("}[").append(mark)
                .append("] ").append(this.title);

        if (getDescription().isPresent()) {
            sb.append(": ").append(getDescription().get());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && title.equals(((Task) other).title));
    }

}
