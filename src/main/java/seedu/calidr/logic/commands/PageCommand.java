package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.calidr.model.Model;
import seedu.calidr.model.PageType;

/**
 * Changes the calendar page / layout.
 */
public class PageCommand extends Command {

    public static final String COMMAND_WORD = "page";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the calendar layout;\n"
            + "One of 'day', 'week' or 'month'.\n"
            + "Example: " + COMMAND_WORD + " week\n";

    public static final String MESSAGE_SUCCESS = "Switched to %1$s view.";

    private final PageType pageType;

    /**
     * Creates a PageCommand to change the calendar page / layout.
     */
    public PageCommand(PageType pt) {
        requireNonNull(pt);
        pageType = pt;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, pageType),
                false, false, pageType, null, null);
    }
}
