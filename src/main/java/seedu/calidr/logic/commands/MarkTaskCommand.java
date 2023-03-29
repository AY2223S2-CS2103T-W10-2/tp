package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Task;

/**
 * Marks a task in the task list as 'done'.
 */
public class MarkTaskCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Marks a task in your task list as 'done'\n"
                    + "Parameters: INDEX (must be a positive integer) "
                    + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Task marked as done: %1$s";

    private final Index index;

    /**
     * Creates a MarkTaskCommand to mark the specified {@code Task}
     *
     * @param index of the task in the filtered task list to mark
     */
    public MarkTaskCommand(Index index) {
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

        Task taskToMark = readOnlyTaskList.get(index.getZeroBased());

        model.markTask(taskToMark);
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

}
