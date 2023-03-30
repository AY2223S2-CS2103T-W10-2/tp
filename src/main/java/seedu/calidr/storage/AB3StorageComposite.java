package seedu.calidr.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.calidr.commons.exceptions.DataConversionException;
import seedu.calidr.model.ReadOnlyAddressBook;
import seedu.calidr.model.ReadOnlyUserPrefs;
import seedu.calidr.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface AB3StorageComposite extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
