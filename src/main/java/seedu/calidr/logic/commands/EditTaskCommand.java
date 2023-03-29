package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.calidr.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.commons.util.CollectionUtil;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
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
 * Edits the details of an existing task in the task list.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. \n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_FROM + "FROM] (only for events) "
            + "[" + PREFIX_TO + "TO] (only for events)\n"
            + "[" + PREFIX_BY + "BY] (only for todos) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + " Science Homework "
            + PREFIX_BY + " 03-11-2024 2359 "
            + PREFIX_DESCRIPTION + " Finish Lab Report "
            + PREFIX_LOCATION + " S17 "
            + PREFIX_TAG + " lab";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the read-only task-list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> readOnlyTaskList = model.getFilteredTaskList();

        if (index.getZeroBased() >= readOnlyTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = readOnlyTaskList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.equals(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor)
            throws CommandException {

        assert taskToEdit != null;

        if (taskToEdit instanceof ToDo) {
            ToDo todoToEdit = (ToDo) taskToEdit;

            Title updatedTitle = editTaskDescriptor.getTitle().orElse(todoToEdit.getTitle());

            Description oldDescription = todoToEdit.getDescription().orElse(null);
            Description updatedDescription = editTaskDescriptor.getDescription().orElse(oldDescription);
            Location oldLocation = todoToEdit.getLocation().orElse(null);
            Location updatedLocation = editTaskDescriptor.getLocation().orElse(oldLocation);

            Priority updatedPriority = editTaskDescriptor.getPriority().orElse(todoToEdit.getPriority());
            TodoDateTime updatedBy = editTaskDescriptor.getByDateTime().orElse(todoToEdit.getBy());
            Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(todoToEdit.getTags());;

            ToDo updatedTodo = new ToDo(updatedTitle, updatedBy);
            updatedTodo.setDescription(updatedDescription);
            updatedTodo.setLocation(updatedLocation);
            updatedTodo.setPriority(updatedPriority);
            updatedTodo.setTags(updatedTags);

            return updatedTodo;

        } else if (taskToEdit instanceof Event) {
            Event eventToEdit = (Event) taskToEdit;

            Title updatedTitle = editTaskDescriptor.getTitle().orElse(eventToEdit.getTitle());
            Description oldDescription = eventToEdit.getDescription().orElse(null);
            Description updatedDescription = editTaskDescriptor.getDescription().orElse(oldDescription);
            Location oldLocation = eventToEdit.getLocation().orElse(null);
            Location updatedLocation = editTaskDescriptor.getLocation().orElse(oldLocation);
            Priority updatedPriority = editTaskDescriptor.getPriority().orElse(eventToEdit.getPriority());
            Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(eventToEdit.getTags());

            LocalDateTime updatedFromDateTime = editTaskDescriptor.getFromDateTime()
                    .orElse(eventToEdit.getEventDateTimes().from);
            LocalDateTime updatedToDateTime = editTaskDescriptor.getToDateTime()
                    .orElse(eventToEdit.getEventDateTimes().to);

            if (!EventDateTimes.isValidEventDateTimes(updatedFromDateTime, updatedToDateTime)) {
                throw new CommandException(EventDateTimes.MESSAGE_CONSTRAINTS);
            }
            EventDateTimes updatedEventTimes = new EventDateTimes(updatedFromDateTime, updatedToDateTime);

            Event updatedEvent = new Event(updatedTitle, updatedEventTimes);
            updatedEvent.setDescription(updatedDescription);
            updatedEvent.setLocation(updatedLocation);
            updatedEvent.setPriority(updatedPriority);
            updatedEvent.setTags(updatedTags);

            return updatedEvent;

        } else {
            // error
            // todo store messages as constant in messages
            throw new CommandException("Invalid task type!");
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Title title;
        private Description description;
        private Location location;
        private TodoDateTime byDateTime;
        private LocalDateTime fromDateTime;
        private LocalDateTime toDateTime;
        private Priority priority;
        private Set<Tag> tags;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setLocation(toCopy.location);
            setByDateTime(toCopy.byDateTime);
            setFromDateTime(toCopy.fromDateTime);
            setToDateTime(toCopy.toDateTime);
            setPriority(toCopy.priority);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, location, byDateTime,
                    fromDateTime, toDateTime, priority, tags);
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }
        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }
        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }
        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<TodoDateTime> getByDateTime() {
            return Optional.ofNullable(byDateTime);
        }
        public void setByDateTime(TodoDateTime todoDateTime) {
            this.byDateTime = todoDateTime;
        }

        public Optional<LocalDateTime> getFromDateTime() {
            return Optional.ofNullable(fromDateTime);
        }
        public void setFromDateTime(LocalDateTime localDateTime) {
            this.fromDateTime = localDateTime;
        }

        public Optional<LocalDateTime> getToDateTime() {
            return Optional.ofNullable(toDateTime);
        }
        public void setToDateTime(LocalDateTime localDateTime) {
            this.toDateTime = localDateTime;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }
        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;
            return getTitle().equals(e.getTitle())
                    && getDescription().equals(e.getDescription())
                    && getLocation().equals(e.getLocation())
                    && getByDateTime().equals(e.getByDateTime())
                    && getFromDateTime().equals(e.getFromDateTime())
                    && getToDateTime().equals(e.getToDateTime())
                    && getPriority().equals(e.getPriority())
                    && getTags().equals(e.getTags());
        }
    }
}
