package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.calidr.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.calidr.logic.commands.AddEventCommand;
import seedu.calidr.logic.commands.AddTodoCommand;
import seedu.calidr.logic.commands.Command;
import seedu.calidr.logic.commands.DeleteTaskCommand;
import seedu.calidr.logic.commands.EditTaskCommand;
import seedu.calidr.logic.commands.ExitCommand;
import seedu.calidr.logic.commands.HelpCommand;
import seedu.calidr.logic.commands.MarkTaskCommand;
import seedu.calidr.logic.commands.PageCommand;
import seedu.calidr.logic.commands.SearchTaskCommand;
import seedu.calidr.logic.commands.ShowCommand;
import seedu.calidr.logic.commands.UnmarkTaskCommand;
import seedu.calidr.logic.commands.ViewDateCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CalidrParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddTodoCommand.COMMAND_WORD:
            return new AddTodoCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case MarkTaskCommand.COMMAND_WORD:
            return new MarkTaskCommandParser().parse(arguments);

        case UnmarkTaskCommand.COMMAND_WORD:
            return new UnmarkTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case ViewDateCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case PageCommand.COMMAND_WORD:
            return new PageCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case SearchTaskCommand.COMMAND_WORD:
            return new SearchTaskCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();


        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
