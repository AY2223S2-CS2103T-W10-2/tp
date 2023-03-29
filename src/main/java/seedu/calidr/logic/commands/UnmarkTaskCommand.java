package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Task;

/**
 * Marks a task in the task list as 'not done'.
 */
public class UnmarkTaskCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Marks a task in your task list as 'not done'\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Task marked as not done: %1$s";

    private final Index index;

    /**
     * Creates a UnmarkTaskCommand to mark the specified {@code Task}
     *
     * @param index of the task in the filtered task list to mark
     */
    public UnmarkTaskCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> readOnlyTaskList = model.getFilteredTaskList();

        if (index.getZeroBased() >= readOnlyTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUnmark = readOnlyTaskList.get(index.getZeroBased());

        model.unmarkTask(taskToUnmark);
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }

}
