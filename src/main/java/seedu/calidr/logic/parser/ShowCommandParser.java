package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.ShowCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns a ShowCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE), pe);
        }

        return new ShowCommand(index);
    }
}
