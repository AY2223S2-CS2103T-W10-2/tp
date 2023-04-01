package seedu.calidr.commons.util;

import java.time.LocalDateTime;
import java.util.Objects;

import com.calendarfx.model.Interval;

import seedu.calidr.model.TaskEntry;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;

/**
 * Task to TaskEntry converter.
 */
public final class TaskEntryUtil {

    /**
     * Converts the Calidr Task object to a CalendarFX TaskEntry object.
     *
     * @param task the task
     * @return the task entry
     */
    public static TaskEntry convert(Task task) {
        Objects.requireNonNull(task);
        TaskEntry taskEntry = new TaskEntry();

        Interval interval;
        if (task instanceof Event) {
            Event event = (Event) task;
            interval = new Interval(event.getEventDateTimes().from, event.getEventDateTimes().to);
        } else if (task instanceof ToDo) {
            ToDo toDo = (ToDo) task;
            LocalDateTime endDt = toDo.getBy().value;
            LocalDateTime startDt = endDt.withHour(0).withMinute(0).withSecond(0);
            if (startDt.isBefore(endDt.minusMinutes(30))) {
                startDt = endDt.minusMinutes(30);
            }
            interval = new Interval(startDt, endDt);
        } else {
            throw new IllegalArgumentException("Task type not supported");
        }
        taskEntry.setTitle(task.getTitle().value);

        taskEntry.setInterval(interval);
        taskEntry.setPriority(task.getPriority());
        taskEntry.setIsDone(task.isDone());
        taskEntry.setUserObject(task);
        return taskEntry;
    }
}
