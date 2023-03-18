package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.calidr.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.commons.util.CollectionUtil;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * Edits the details of an existing task in the task list.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_BY + "BY] (only for todos)"
            + "[" + PREFIX_FROM + "FROM] (only for events)"
            + "[" + PREFIX_TO + "TO] (only for events)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "Science Homework "
            + PREFIX_BY + "03-11-2024 2359";

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
            EditTodoDescriptor editTodoTaskDescriptor = new EditTodoDescriptor(editTaskDescriptor);

            Title updatedTitle = editTodoTaskDescriptor.getTitle().orElse(todoToEdit.getTitle());
            TodoDateTime updatedBy = editTodoTaskDescriptor.getBy().orElse(todoToEdit.getBy());

            return new ToDo(updatedTitle, updatedBy);

        } else if (taskToEdit instanceof Event) {
            Event eventToEdit = (Event) taskToEdit;
            EditEventDescriptor editEventTaskDescriptor = new EditEventDescriptor(editTaskDescriptor);

            Title updatedTitle = editEventTaskDescriptor.getTitle().orElse(eventToEdit.getTitle());
            LocalDateTime updatedFromDateTime = editEventTaskDescriptor.getFromDateTime()
                    .orElse(eventToEdit.getEventDateTimes().from);
            LocalDateTime updatedToDateTime = editEventTaskDescriptor.getToDateTime()
                    .orElse(eventToEdit.getEventDateTimes().to);

            if (!EventDateTimes.isValidEventDateTimes(updatedFromDateTime, updatedToDateTime)) {
                throw new CommandException(EventDateTimes.MESSAGE_CONSTRAINTS);
            }

            EventDateTimes updatedEventTimes = new EventDateTimes(updatedFromDateTime, updatedToDateTime);

            return new Event(updatedTitle, updatedEventTimes);

        } else {
            // error
            return null;
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Title title;
        private TodoDateTime byDateTime;
        private LocalDateTime fromDateTime;
        private LocalDateTime toDateTime;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setBy(toCopy.byDateTime);
            setFrom(toCopy.fromDateTime);
            setTo(toCopy.toDateTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, byDateTime, fromDateTime, toDateTime);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public void setBy(TodoDateTime todoDateTime) {
            this.byDateTime = todoDateTime;
        }

        public void setFrom(LocalDateTime localDateTime) {
            this.fromDateTime = localDateTime;
        }

        public void setTo(LocalDateTime localDateTime) {
            this.toDateTime = localDateTime;
        }
    }


    /**
     * Stores the details to edit the todo task with. Each non-empty field value will replace the
     * corresponding field value of the todo Task.
     */
    public static class EditTodoDescriptor {
        private Title title;
        private TodoDateTime byDateTime;

        /**
         * Creates a new {@code EditTodoDescriptor} object with the
         * same title and byDateTime as the template given.
         *
         * @param toCopy The {@code EditTaskDescriptor} to copy from
         */
        public EditTodoDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setBy(toCopy.byDateTime);
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTodoDescriptor(EditTodoDescriptor toCopy) {
            setTitle(toCopy.title);
            setBy(toCopy.byDateTime);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, byDateTime);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setBy(TodoDateTime todoDateTime) {
            this.byDateTime = todoDateTime;
        }

        public Optional<TodoDateTime> getBy() {
            return Optional.ofNullable(byDateTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTodoDescriptor)) {
                return false;
            }

            // state check
            EditTodoDescriptor e = (EditTodoDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getBy().equals(e.getBy());
        }
    }


    /**
     * Stores the details to edit the event task with. Each non-empty field value will replace the
     * corresponding field value of the event Task.
     */
    public static class EditEventDescriptor {
        private Title title;
        private LocalDateTime fromDateTime;
        private LocalDateTime toDateTime;

        /**
         * Creates a new {@code EditEventDescriptor} object with the
         * same title, fromDateTime, toDateTime as the template given.
         *
         * @param toCopy The {@code EditTaskDescriptor} to copy from
         */
        public EditEventDescriptor(EditTaskDescriptor toCopy) {
            setTitle(toCopy.title);
            setFromDateTime(toCopy.fromDateTime);
            setToDateTime(toCopy.toDateTime);
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setTitle(toCopy.title);
            setFromDateTime(toCopy.fromDateTime);
            setToDateTime(toCopy.toDateTime);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, fromDateTime, toDateTime);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setFromDateTime(LocalDateTime fromDateTime) {
            this.fromDateTime = fromDateTime;
        }

        public Optional<LocalDateTime> getFromDateTime() {
            return Optional.ofNullable(fromDateTime);
        }

        public void setToDateTime(LocalDateTime toDateTime) {
            this.toDateTime = toDateTime;
        }

        public Optional<LocalDateTime> getToDateTime() {
            return Optional.ofNullable(toDateTime);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getFromDateTime().equals(e.getFromDateTime())
                    && getToDateTime().equals(e.getToDateTime());
        }
    }

}
