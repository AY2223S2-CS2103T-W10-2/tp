package seedu.calidr.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;

    @FXML
    private Label index;

    @FXML
    private Label priority;
    @FXML
    private Label status;
    @FXML
    private Label fromDate;
    @FXML
    private Label toDate;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        title.setText(task.getTitle().value);
        index.setText(displayedIndex + ". ");
        priority.setText(task.getPriority().toString().toUpperCase());
        if (task.isDone()) {
            status.setText("DONE");
        } else {
            status.setVisible(false);
            status.setManaged(false);
        }
        if (task instanceof Event) {
            EventDateTimes eventDateTimes = ((Event) task).getEventDateTimes();
            fromDate.setText("from " + eventDateTimes.from.format(EventDateTimes.PRINT_FORMAT));
            toDate.setText("to " + eventDateTimes.to.format(EventDateTimes.PRINT_FORMAT));
        } else {
            TodoDateTime todoDateTime = ((ToDo) task).getBy();
            fromDate.setVisible(false);
            fromDate.setManaged(false);
            toDate.setText("by " + todoDateTime.value.format(TodoDateTime.PRINT_FORMAT));
        }
        /*
        task.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        */
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return index.getText().equals(card.index.getText()) && task.equals(card.task);
    }
}
