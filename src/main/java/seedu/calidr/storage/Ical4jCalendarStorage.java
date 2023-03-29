package seedu.calidr.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyTaskList;

// TODO: STUB

/**
 * A class to access TaskList stored in the hard disk as a ical4j file
 */
public class Ical4jCalendarStorage implements CalendarStorage {
    @Override
    public Optional<ReadOnlyTaskList> readTaskList() throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void saveTaskList(ReadOnlyTaskList taskList) throws IOException {

    }
}
