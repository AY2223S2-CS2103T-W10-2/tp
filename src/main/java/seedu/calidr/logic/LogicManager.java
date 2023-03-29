package seedu.calidr.logic;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.calidr.commons.core.GuiSettings;
import seedu.calidr.commons.core.LogsCenter;
import seedu.calidr.logic.commands.Command;
import seedu.calidr.logic.commands.CommandResult;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.logic.parser.CalidrParser;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.Model;
import seedu.calidr.model.ReadOnlyAddressBook;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.person.Person;
import seedu.calidr.model.task.Task;
import seedu.calidr.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    // private final AddressBookParser addressBookParser;

    private final CalidrParser calidrParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        // addressBookParser = new AddressBookParser();
        calidrParser = new CalidrParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = calidrParser.parseCommand(commandText);
        commandResult = command.execute(model);

        // TODO Storage integration
        /*
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
         */

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    //==================For Calidr=====================================================================

    @Override
    public ReadOnlyTaskList getTaskList() {
        return model.getTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
