package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.InfoCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;

public class InfoCommandParser implements Parser<InfoCommand> {
    public InfoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new InfoCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InfoCommand.MESSAGE_USAGE), pe);
        }
    }
}
