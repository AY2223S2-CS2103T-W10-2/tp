package seedu.calidr.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyTaskList;

/**
 * Represents a storage for {@link seedu.calidr.model.tasklist.TaskList}.
 */
public interface CalendarStorage {
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;
}
