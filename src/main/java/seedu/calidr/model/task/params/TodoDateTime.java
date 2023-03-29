package seedu.calidr.model.task.params;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the deadline date-time for a ToDo.
 */
public class TodoDateTime {

    public static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");

    public final LocalDateTime value;

    /**
     * Constructs a {@code TodoDateTime}.
     *
     * @param dateTime A valid deadline date-time.
     */
    public TodoDateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);

        value = dateTime;

    }

    /**
     * Returns the String representation of the TodoDateTime object.
     */
    public String toString() {
        return "by: " + value.format(PRINT_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoDateTime // instanceof handles nulls
                && value.equals(((TodoDateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
