package seedu.address.task;

import java.time.LocalDateTime;

/**
 * Represents an event - a task with specific start and end
 * dates and times.
 */
public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: " + Task.getDateTimeString(this.from)
                + " ; to: " + Task.getDateTimeString(this.to) + ")";
    }
}