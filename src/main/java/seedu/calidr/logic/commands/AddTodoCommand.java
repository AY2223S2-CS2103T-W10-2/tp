package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.ToDo;

/**
 * Adds a to-do to the task list.
 */
public class AddTodoCommand extends Command {

    public static final String COMMAND_WORD = "todo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a to-do to the task list. "
            + "Parameters: "
            + PREFIX_TITLE + " TITLE "
            + PREFIX_BY + " BY DATE-TIME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "CS2107 CTF "
            + PREFIX_BY + " 05-04-2023 2359 "
            + PREFIX_DESCRIPTION + " Exercise 5 "
            + PREFIX_LOCATION + " NUS "
            + PREFIX_TAG + " Assignment";

    public static final String MESSAGE_SUCCESS = "New to-do added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This to-do already exists in the task list.";

    private final ToDo toAdd;

    /**
     * Creates an AddTodoCommand to add the specified {@code ToDo}
     */
    public AddTodoCommand(ToDo todo) {
        requireNonNull(todo);
        toAdd = todo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTodoCommand // instanceof handles nulls
                && toAdd.equals(((AddTodoCommand) other).toAdd));
    }
}
