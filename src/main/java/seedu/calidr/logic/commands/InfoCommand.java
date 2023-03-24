package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Task;

/**
 * Displays the information of the specified task.
 */
public class InfoCommand extends Command {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays task details of the task identified by the index number.\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_INFO_SUCCESS = "Info: %1$s";

    private final Index index;

    public InfoCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToShow = lastShownList.get(index.getZeroBased());
        return new CommandResult(String.format(MESSAGE_INFO_SUCCESS, taskToShow));
    }
}
