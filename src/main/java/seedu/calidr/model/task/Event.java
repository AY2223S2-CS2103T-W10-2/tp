package seedu.calidr.model.task;

import java.util.Set;

import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;

/**
 * Represents an event - a task with specific start and end
 * dates and times.
 */
public class Event extends Task {

    private final EventDateTimes eventDateTimes;

    /**
     * Creates an Event with the given details.
     *
     * @param title The title of the Event.
     * @param eventDateTimes The date-times associated with the Event.
     */
    public Event(Title title, EventDateTimes eventDateTimes) {
        super(title);

        assert eventDateTimes != null;
        this.eventDateTimes = eventDateTimes;
    }

    public EventDateTimes getEventDateTimes() {
        return eventDateTimes;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[E]");
        String mark = isDone() ? "X" : " ";
        sb.append("{")
                .append(getPriority().toString().toUpperCase())
                .append("}[").append(mark).append("] ")
                .append(getTitle())
                .append("(")
                .append(eventDateTimes.toString())
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
                && eventDateTimes.equals(((Event) other).eventDateTimes);
    }
}
