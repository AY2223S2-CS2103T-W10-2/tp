package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import seedu.calidr.logic.commands.ViewDateCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewDateCommand parse(String args) throws ParseException {
        args = args.trim();
        if (args.isEmpty()) {
            return new ViewDateCommand(LocalDate.now());
        }
        LocalDate date;
        try {
            date = ParserUtil.parseDate(args);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewDateCommand.MESSAGE_USAGE));
        }
        return new ViewDateCommand(date);
    }
}
