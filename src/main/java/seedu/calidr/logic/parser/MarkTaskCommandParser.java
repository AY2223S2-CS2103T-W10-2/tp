package seedu.calidr.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.MarkTaskCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkTaskCommand object
 */
public class MarkTaskCommandParser implements Parser<MarkTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTaskCommand
     *
     * @param args the arguments to be parsed
     * @return a MarkTaskCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE), pe);
        }

        return new MarkTaskCommand(index);
    }
}
