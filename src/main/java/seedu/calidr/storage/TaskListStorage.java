package seedu.calidr.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyTaskList;

/**
 * Represents a storage for {@link seedu.calidr.model.tasklist.TaskList}.
 */
public interface TaskListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTaskListFilePath();

    /**
     * Returns TaskList data as a {@link seedu.calidr.model.ReadOnlyTaskList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException;

    /**
     * @see #getTaskListFilePath()
     */
    Optional<ReadOnlyTaskList> readTaskList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskList} to the storage.
     * @param taskList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskList(ReadOnlyTaskList taskList) throws IOException;

    /**
     * @see #saveTaskList(ReadOnlyTaskList)
     */
    void saveTaskList(ReadOnlyTaskList taskList, Path filePath) throws IOException;

}
