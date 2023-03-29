package seedu.calidr.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.ReadOnlyUserPrefs;
import seedu.calidr.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CalendarStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    @Override
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

}
