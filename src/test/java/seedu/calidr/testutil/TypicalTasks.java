package seedu.calidr.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;
import seedu.calidr.model.tasklist.TaskList;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {


    public static final LocalDateTime DATE_TIME1 =
            LocalDateTime.of(2023, 3,
                    10, 10, 10);
    public static final LocalDateTime DATE_TIME2 =
            LocalDateTime.of(2023, 3,
                    11, 10, 10);

    public static final LocalDateTime DATE_TIME3 =
            LocalDateTime.of(2023, 3,
                    12, 10, 10);
    public static final ToDo TODO1 = new ToDo(
            new Title("CS2101 Quiz 2"),
            new TodoDateTime(DATE_TIME1)
    );

    public static final ToDo TODO2 = (
            (Supplier<ToDo>) () -> {
                ToDo temp = new ToDo(
                        new Title("CS2101 Quiz 3"),
                        new TodoDateTime(DATE_TIME2)
                );
                temp.setPriority(Priority.LOW);
                temp.setDone(true);
                temp.setDescription(new Description("This is a todo description"));
                return temp;
            }
    ).get();

    public static final Event EVENT1 = new Event(
            new Title("CS2103T Lecture 1"),
            new EventDateTimes(DATE_TIME1,
                    DATE_TIME2)
    );

    public static final Event EVENT2 = (
            (Supplier<Event>) () -> {
                Event temp = new Event(
                        new Title("CS2103T Lecture 3"),
                        new EventDateTimes(DATE_TIME1,
                                DATE_TIME3)
                );
                temp.setPriority(Priority.HIGH);
                return temp;
            }
    ).get();

    public static final Event EVENT3 = (
            (Supplier<Event>) () -> {
                Event temp = new Event(
                        new Title("CS2103T Lecture 3"),
                        new EventDateTimes(DATE_TIME1,
                                DATE_TIME3)
                );
                temp.setDescription(new Description("This is an event description"));
                return temp;
            }
    ).get();

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code TaskList} with all the typical persons.
     */
    public static TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        ab.setTasks(getTypicalTasks());
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(TODO1, TODO2, EVENT1, EVENT2, EVENT3));
    }

}
