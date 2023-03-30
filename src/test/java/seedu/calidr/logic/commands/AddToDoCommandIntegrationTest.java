package seedu.calidr.logic.commands;

import static seedu.calidr.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.calidr.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.calidr.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.calidr.model.Model;
import seedu.calidr.model.ModelManager;
import seedu.calidr.model.UserPrefs;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddToDoCommand}.
 */
public class AddToDoCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaskList(), new UserPrefs());
    }

    @Test
    public void execute_newToDo_success() {
        ToDo validToDo = new TaskBuilder().buildToDo();

        Model expectedModel = new ModelManager(model.getTaskList(), new UserPrefs());
        expectedModel.addTask(validToDo);

        assertCommandSuccess(new AddTodoCommand(validToDo), model,
                String.format(AddTodoCommand.MESSAGE_SUCCESS, validToDo), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        ToDo taskInList = new TaskBuilder().buildToDo();
        model.addTask(taskInList);
        assertCommandFailure(new AddTodoCommand(taskInList), model, AddTodoCommand.MESSAGE_DUPLICATE_TODO);
    }

}
