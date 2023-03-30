package seedu.calidr.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.calidr.commons.core.GuiSettings;
import seedu.calidr.logic.commands.CommandResult;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //========================For Calidr=========================================================
    ReadOnlyTaskList getTaskList();

    ObservableList<Task> getFilteredTaskList();
}
