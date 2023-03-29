package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.calidr.logic.commands.AddEventCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Location;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_LOCATION,
                        PREFIX_FROM, PREFIX_TO, PREFIX_PRIORITY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_FROM, PREFIX_TO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        String fromDateTimeString = argMultimap.getValue(PREFIX_FROM).get();
        String toDateTimeString = argMultimap.getValue(PREFIX_TO).get();
        EventDateTimes eventDateTimes = ParserUtil.parseEventDateTimes(fromDateTimeString, toDateTimeString);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(title, eventDateTimes);

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            event.setDescription(description);
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
            event.setLocation(location);
        }

        Priority priority = Priority.MEDIUM;
        Optional<String> priorityValue = argMultimap.getValue(PREFIX_PRIORITY);
        if (priorityValue.isPresent()) {
            priority = ParserUtil.parsePriority(priorityValue.get());
        }
        event.setPriority(priority);

        event.setTags(tagList);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
