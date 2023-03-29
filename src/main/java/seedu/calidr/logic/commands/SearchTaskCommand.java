package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.calidr.commons.core.Messages;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.predicates.TitleContainsKeywordsPredicate;

/**
 * Finds and lists all tasks whose title contains any of the
 * keywords given. Keyword matching is case-insensitive.
 */
public class SearchTaskCommand extends Command {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose titles contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " assignment homework";

    private final TitleContainsKeywordsPredicate predicate;

    public SearchTaskCommand(TitleContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                        model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchTaskCommand // instanceof handles nulls
                && predicate.equals(((SearchTaskCommand) other).predicate)); // state check
    }
}
