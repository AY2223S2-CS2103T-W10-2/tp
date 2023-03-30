package seedu.calidr.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Location;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Tasks";
    public static final String DEFAULT_DESCRIPTION = "Finish tP";
    public static final Priority DEFAULT_PRIORITY = Priority.HIGH;

    public static final String DEFAULT_LOCATION = "COM1-B103";

    public static final LocalDateTime DEFAULT_START_DATE =
            LocalDateTime.of(2020, 3, 1, 10, 0);

    public static final LocalDateTime DEFAULT_END_DATE =
            LocalDateTime.of(2020, 3, 1, 11, 0);

    public static final String DEFAULT_TAG = "CS2103";

    public static final boolean DEFAULT_IS_DONE = false;

    private Title title = new Title(DEFAULT_TITLE);

    private Description description = new Description(DEFAULT_DESCRIPTION);

    private Priority priority = DEFAULT_PRIORITY;

    private Location location = new Location(DEFAULT_LOCATION);

    private LocalDateTime startByDate = DEFAULT_START_DATE;

    private LocalDateTime endDate = DEFAULT_END_DATE;

    private boolean isDone = DEFAULT_IS_DONE;

    private Set<Tag> tags = Set.of(new Tag(DEFAULT_TAG));

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        title = taskToCopy.getTitle();
        taskToCopy.getDescription().ifPresent(description -> this.description = description);
        priority = taskToCopy.getPriority();
        taskToCopy.getLocation().ifPresent(location -> this.location = location);
        if (taskToCopy instanceof Event) {
            startByDate = ((Event) taskToCopy).getEventDateTimes().from;
            endDate = ((Event) taskToCopy).getEventDateTimes().to;
        } else if (taskToCopy instanceof ToDo) {
            startByDate = ((ToDo) taskToCopy).getBy().value;
            isDone = taskToCopy.isDone();
        }
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Task} that we are building.
     */
    public TaskBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are building.
     */
    public TaskBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Task} that we are building.
     */
    public TaskBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code fromDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withFromDate(LocalDateTime startByDate) {
        this.startByDate = startByDate;
        return this;
    }

    /**
     * Sets the {@code byDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withByDate(LocalDateTime startByDate) {
        return withFromDate(startByDate);
    }

    /**
     * Sets the {@code toDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withToDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Marks the {@code Task} that we are building as done.
     */
    public TaskBuilder mark() {
        this.isDone = true;
        return this;
    }

    /**
     * Unmarks the {@code Task} that we are building as not done.
     */
    public TaskBuilder unmark() {
        this.isDone = false;
        return this;
    }

    /**
     * Adds a {@code Tag} to the {@code Task} that we are building.
     */
    public TaskBuilder withTag(String tagName) {
        this.tags.add(new Tag(tagName));
        return this;
    }

    public ToDo buildToDo() {
        ToDo toDo = new ToDo(title, new TodoDateTime(startByDate));
        toDo.setDescription(description);
        toDo.setPriority(priority);
        toDo.setLocation(location);
        toDo.setTags(tags);
        toDo.setDone(isDone);
        return toDo;
    }

    public Event buildEvent() {
        Event event = new Event(title, new EventDateTimes(startByDate, endDate));
        event.setDescription(description);
        event.setPriority(priority);
        event.setLocation(location);
        event.setTags(tags);
        return event;
    }
}
