package seedu.calidr.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.calidr.commons.core.LogsCenter;
import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.ReadOnlyUserPrefs;
import seedu.calidr.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    private final CalendarStorage calendarStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code CalendarStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(CalendarStorage calendarStorage, UserPrefsStorage userPrefsStorage) {
        this.calendarStorage = calendarStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return calendarStorage.readTaskList();
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {
        calendarStorage.saveTaskList(taskList);
    }

}
