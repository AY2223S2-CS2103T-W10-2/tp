package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Task;

/**
 * Shows a task's details in a popup dialog.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display a task's details in a popup dialog.\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Provide an index from the task list on the right.";

    public static final String MESSAGE_SUCCESS = "Showing task: %1$s";

    private final Index index;

    /**
     * Creates a ShowCommand to show the details of the specified {@code Task}.
     *
     * @param index Index of the task in the filtered task list to show.
     */
    public ShowCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> readOnlyTaskList = model.getFilteredTaskList();

        if (index.getZeroBased() >= readOnlyTaskList.size() || index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToShow = readOnlyTaskList.get(index.getZeroBased());

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToShow.getTitle()),
                false, false, null, null, taskToShow);
    }
}
