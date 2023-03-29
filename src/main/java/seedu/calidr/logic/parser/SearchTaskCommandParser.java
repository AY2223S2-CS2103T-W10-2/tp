package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.calidr.logic.commands.SearchTaskCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.task.predicates.TitleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchTaskCommand object
 */
public class SearchTaskCommandParser implements Parser<SearchTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchTaskCommand
     * and returns a SearchTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchTaskCommand(new TitleContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
