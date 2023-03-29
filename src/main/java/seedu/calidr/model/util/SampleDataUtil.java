package seedu.calidr.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.task.params.Tag;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {


    public static Task[] getSampleTasks() {
        return new Task[]{
                new ToDo(
                        new Title("CS2101 Quiz 2"),
                        new TodoDateTime(
                                LocalDateTime.of(2023, 3, 10, 10, 10)
                        )
                ),
                new Event(
                        new Title("CS2103T Lecture 1"),
                        new EventDateTimes(
                                LocalDateTime.of(2023, 3, 10, 10, 10),
                                LocalDateTime.of(2023, 3, 11, 10, 10)
                        )
                ),
                ((Supplier<ToDo>) () -> {
                    ToDo temp = new ToDo(
                            new Title("CS2101 Quiz 3"),
                            new TodoDateTime(
                                    LocalDateTime.of(2023, 3, 11, 10, 10)
                            )
                    );
                    temp.setDone(true);
                    temp.setPriority(Priority.HIGH);
                    return temp;
                }).get(),
                ((Supplier<Event>) () -> {
                    Event temp = new Event(
                            new Title("CS2103T Lecture 2"),
                            new EventDateTimes(
                                    LocalDateTime.of(2023, 3, 11, 10, 10),
                                    LocalDateTime.of(2023, 3, 12, 10, 10)
                            )
                    );
                    temp.setPriority(Priority.LOW);
                    temp.setDescription(new Description("This is a description"));
                    return temp;
                }).get()
        };
    }

    public static ReadOnlyTaskList getSampleTaskList() {
        TaskList sampleTaskList = new TaskList();
        sampleTaskList.setTasks(Arrays.asList(getSampleTasks()));
        return sampleTaskList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
