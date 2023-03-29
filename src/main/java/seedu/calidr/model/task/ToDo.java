package seedu.calidr.model.task;

import java.util.Set;

import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * Represents a deadline - a task that should be completed within
 * a particular date and time.
 */
public class ToDo extends Task {

    private final TodoDateTime byDateTime;

    /**
     * Creates a ToDo with the given details.
     *
     * @param title The title of the ToDo.
     * @param by The deadline date-time of the ToDo.
     */
    public ToDo(Title title, TodoDateTime by) {
        super(title);

        assert by != null;
        this.byDateTime = by;
    }

    public TodoDateTime getBy() {
        return this.byDateTime;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[T]");
        String mark = isDone() ? "X" : " ";
        sb.append("{")
                .append(getPriority().toString().toUpperCase())
                .append("}[").append(mark).append("] ")
                .append(getTitle())
                .append(" (")
                .append(byDateTime.toString())
                .append(")");

        if (getDescription().isPresent()) {
            sb.append(": \n").append(getDescription().get());
        }

        if (getLocation().isPresent()) {
            sb.append("\n@ ").append(getLocation().get());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            sb.append("\nTags: ");
            tags.forEach(sb::append);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other)
                && byDateTime.equals(((ToDo) other).byDateTime);
    }
}
