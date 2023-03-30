package seedu.calidr.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.calidr.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.calidr.commons.core.index.Index;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.logic.parser.ParserUtil;
import seedu.calidr.model.Model;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Location;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.TodoDateTime;
import seedu.calidr.model.task.predicates.TitleContainsKeywordsPredicate;
import seedu.calidr.model.tasklist.TaskList;
import seedu.calidr.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TITLE_LEC = "Lecture CS2103";
    public static final String VALID_TITLE_ASS = "Assignment CS2103";

    public static final String VALID_DESC_LEC = "Lecture CS2103 Online Compulsory";

    public static final String VALID_DESC_ASS = "Assignment 2 CS2103 Compulsory";

    public static final String VALID_TAG_2103 = "CS2103";

    public static final String VALID_TAG_WORK = "Work";

    public static final Priority VALID_PRIORITY_HIGH = Priority.HIGH;

    public static final Priority VALID_PRIORITY_LOW = Priority.LOW;

    public static final Location VALID_LOCATION_LEC = new Location("SOC 2 LT 12");

    public static final Location VALID_LOCATION_ASS = new Location("Online");

    public static final TodoDateTime VALID_DATE_TODO =
            new TodoDateTime(LocalDateTime.of(2023, 3, 23, 12, 36));

    public static final EventDateTimes VALID_DATE_EVENT = new EventDateTimes(
            LocalDateTime.of(2023, 3, 23, 14, 0),
            LocalDateTime.of(2023, 3, 23, 16, 0)
    );

    public static final String TITLE_DESC_LEC = " " + PREFIX_TITLE + VALID_TITLE_LEC;

    public static final String TITLE_DESC_ASS = " " + PREFIX_TITLE + VALID_TITLE_ASS;

    public static final String DESCRIPTION_DESC_LEC = " " + PREFIX_DESCRIPTION + VALID_DESC_LEC;

    public static final String DESCRIPTION_DESC_ASS = " " + PREFIX_DESCRIPTION + VALID_DESC_ASS;

    public static final String TAG_DESC_2103 = " " + PREFIX_TAG + VALID_TAG_2103;

    public static final String TAG_DESC_WORK = " " + PREFIX_TAG + VALID_TAG_WORK;

    public static final String PRIORITY_DESC_LEC = " " + PREFIX_PRIORITY + VALID_PRIORITY_HIGH;

    public static final String PRIORITY_DESC_ASS = " " + PREFIX_PRIORITY + VALID_PRIORITY_LOW;

    public static final String LOCATION_DESC_LEC = " " + PREFIX_LOCATION + VALID_LOCATION_LEC;

    public static final String LOCATION_DESC_ASS = " " + PREFIX_LOCATION + VALID_LOCATION_ASS;

    public static final String DATE_FROM_DESC_LEC =
            " " + PREFIX_FROM + VALID_DATE_EVENT.from.format(ParserUtil.DATETIME_FORMAT);

    public static final String DATE_TO_DESC_LEC =
            " " + PREFIX_TO + VALID_DATE_EVENT.to.format(ParserUtil.DATETIME_FORMAT);

    public static final String DATE_DESC_LEC = DATE_FROM_DESC_LEC + DATE_TO_DESC_LEC;

    public static final String DATE_DESC_ASS =
            " " + PREFIX_BY + VALID_DATE_TODO.value.format(ParserUtil.DATETIME_FORMAT);

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " ";

    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION;

    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "mid";

    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION;

    public static final String INVALID_DATE_FROM_DESC = " " + PREFIX_FROM + "Apr 6 2023";

    public static final String INVALID_DATE_BY_DESC = " " + PREFIX_BY;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor DESC_LEC;
    public static final EditTaskCommand.EditTaskDescriptor DESC_ASS;

    static {
        DESC_LEC = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_LEC).withDescription(VALID_DESC_LEC)
                .withLocation(VALID_LOCATION_LEC.value).withPriority(VALID_PRIORITY_HIGH)
                .withByDateTime(VALID_DATE_TODO.value)
                .withTags(VALID_TAG_2103).build();
        DESC_ASS = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_ASS).withDescription(VALID_DESC_ASS)
                .withLocation(VALID_LOCATION_ASS.value).withPriority(VALID_PRIORITY_LOW)
                .withFromDateTime(VALID_DATE_EVENT.from).withToDateTime(VALID_DATE_EVENT.to)
                .withTags(VALID_TAG_2103, VALID_TAG_WORK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the task list, filtered task list and selected task in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskList expectedTaskList = new TaskList(actualModel.getTaskList());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTaskList, actualModel.getTaskList());
        assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s task list.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitTitle = task.getTitle().value.split("\\s+");
        model.updateFilteredTaskList(new TitleContainsKeywordsPredicate(List.of(splitTitle)));

        assertEquals(1, model.getFilteredTaskList().size());
    }

}
