package seedu.calidr.logic.parser;

import static seedu.calidr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.calidr.logic.commands.AddTodoCommand;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.Location;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * Parses input arguments and creates a new AddTodoCommand object
 */
public class AddTodoCommandParser implements Parser<AddTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTodoCommand
     * and returns an AddTodoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTodoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_LOCATION,
                        PREFIX_BY, PREFIX_PRIORITY, PREFIX_TAG);


        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_BY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTodoCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        TodoDateTime byDateTime = ParserUtil.parseTodoDateTime(argMultimap.getValue(PREFIX_BY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ToDo todo = new ToDo(title, byDateTime);

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            todo.setDescription(description);
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
            todo.setLocation(location);
        }

        Priority priority = Priority.MEDIUM;
        Optional<String> priorityValue = argMultimap.getValue(PREFIX_PRIORITY);
        if (priorityValue.isPresent()) {
            priority = ParserUtil.parsePriority(priorityValue.get());
        }
        todo.setPriority(priority);

        todo.setTags(tagList);

        return new AddTodoCommand(todo);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
