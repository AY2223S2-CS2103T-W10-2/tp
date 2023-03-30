package seedu.calidr.model;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.calidr.commons.core.GuiSettings;
import seedu.calidr.commons.core.LogsCenter;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.tasklist.TaskList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskList and userPrefs.
     */
    public ModelManager(ReadOnlyTaskList taskList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(taskList, userPrefs);

        logger.fine("Initializing with taskList: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
    }

    public ModelManager() {
        this(new TaskList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== TaskList ================================================================================

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public void setTaskList(ReadOnlyTaskList taskList) {
        this.taskList.resetData(taskList);
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return taskList.hasTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskList.deleteTask(task);
    }

    @Override
    public void addTask(Task task) {
        taskList.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        taskList.setTask(target, editedTask);
    }

    @Override
    public void markTask(Task task) {
        taskList.markTask(task);
    }

    @Override
    public void unmarkTask(Task task) {
        taskList.unmarkTask(task);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTaskList}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

}
