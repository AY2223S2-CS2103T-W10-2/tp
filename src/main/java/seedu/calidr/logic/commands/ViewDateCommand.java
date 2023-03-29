package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.calidr.model.Model;

/**
 * Changes the current focused date.
 */
public class ViewDateCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the current focused date.\n"
            + "Example: " + COMMAND_WORD + " 29-12-2023\n"
            + "Enter without arguments to view today's date";

    public static final String MESSAGE_SUCCESS = "Viewing date: %1$s";

    private final LocalDate toDate;

    /**
     * Creates a ViewDateCommand to change the current focused date.
     *
     * @param date the date to view
     */
    public ViewDateCommand(LocalDate date) {
        requireNonNull(date);
        toDate = date;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDate),
                false, false, null, toDate, null);
    }
}
