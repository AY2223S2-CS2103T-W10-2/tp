package seedu.calidr.model.task.params;

import static seedu.calidr.commons.util.AppUtil.checkArgument;
import static seedu.calidr.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the two date-times associated with an Event.
 */
public class EventDateTimes {

    public static final String MESSAGE_CONSTRAINTS =
            "The END date-time should be after the START date-time";

    public static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
    public final LocalDateTime from;
    public final LocalDateTime to;


    /**
     * Constructs an {@code EventDateTimes}
     *
     * @param from A valid START date-time.
     * @param to A valid END date-time.
     */
    public EventDateTimes(LocalDateTime from, LocalDateTime to) {
        requireAllNonNull(from, to);
        checkArgument(isValidEventDateTimes(from, to), MESSAGE_CONSTRAINTS);

        this.from = from;
        this.to = to;

    }

    /**
     * Returns true if a particular set of START and END date-times
     * is valid.
     */
    public static boolean isValidEventDateTimes(LocalDateTime start, LocalDateTime end) {
        return end.isAfter(start);
    }

    /**
     * Returns the string representation of the EventDateTimes object.
     */
    public String toString() {
        return "from: " + from.format(PRINT_FORMAT)
                + " ; to: " + to.format(PRINT_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDateTimes // instanceof handles nulls
                && from.equals(((EventDateTimes) other).from)
                && to.equals(((EventDateTimes) other).to)); // state check
    }

}
