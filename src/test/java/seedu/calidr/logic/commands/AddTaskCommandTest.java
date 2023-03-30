package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.calidr.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.calidr.commons.core.GuiSettings;
import seedu.calidr.logic.commands.exceptions.CommandException;
import seedu.calidr.model.Model;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.ReadOnlyUserPrefs;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.tasklist.TaskList;
import seedu.calidr.testutil.TaskBuilder;

public class AddTaskCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTodoCommand(null));
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        ToDo validToDo = new TaskBuilder().buildToDo();

        CommandResult commandResult = new AddTodoCommand(validToDo).execute(modelStub);

        assertEquals(String.format(AddTodoCommand.MESSAGE_SUCCESS, validToDo), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validToDo), modelStub.tasksAdded);

        Event validEvent = new TaskBuilder().buildEvent();

        commandResult = new AddEventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validEvent), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        ToDo validToDo = new TaskBuilder().buildToDo();
        AddTodoCommand addTodoCommand = new AddTodoCommand(validToDo);

        Event validEvent = new TaskBuilder().buildEvent();
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);

        ModelStub modelStubToDo = new ModelStubWithTask(validToDo);
        assertThrows(CommandException.class, AddTodoCommand.MESSAGE_DUPLICATE_TODO,
                () -> addTodoCommand.execute(modelStubToDo));

        ModelStub modelStubEvent = new ModelStubWithTask(validEvent);
        assertThrows(CommandException.class, AddEventCommand.MESSAGE_DUPLICATE_EVENT,
                () -> addEventCommand.execute(modelStubEvent));
    }

    @Test
    public void equals() {

        ToDo toDo1 = new TaskBuilder().withTitle("CS2103T").buildToDo();
        ToDo toDo2 = new TaskBuilder().withTitle("CS2101").buildToDo();
        Event event1 = new TaskBuilder().withTitle("CS2103T").buildEvent();
        Event event2 = new TaskBuilder().withTitle("CS2101").buildEvent();

        AddTodoCommand addTodoCommand1 = new AddTodoCommand(toDo1);
        AddTodoCommand addTodoCommand2 = new AddTodoCommand(toDo2);

        AddEventCommand addEventCommand1 = new AddEventCommand(event1);
        AddEventCommand addEventCommand2 = new AddEventCommand(event2);

        // same object -> returns true
        assertEquals(addTodoCommand1, addTodoCommand1);
        assertEquals(addEventCommand1, addEventCommand1);

        // same values -> returns true
        AddTodoCommand addTodoCommand1Copy = new AddTodoCommand(toDo1);
        assertEquals(addTodoCommand1, addTodoCommand1Copy);
        AddEventCommand addEventCommand1Copy = new AddEventCommand(event1);
        assertEquals(addEventCommand1, addEventCommand1Copy);

        // null -> returns false
        assertNotEquals(null, addTodoCommand1);
        assertNotEquals(null, addEventCommand1);

        // different task -> returns false
        assertNotEquals(addTodoCommand1, addTodoCommand2);
        assertNotEquals(addEventCommand1, addEventCommand2);
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCalendarFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCalendarFilePath(Path calendarFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskList getTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskList(ReadOnlyTaskList taskList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private static class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private static class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyTaskList getTaskList() {
            return new TaskList();
        }
    }

}
