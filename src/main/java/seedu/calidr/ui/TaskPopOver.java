package seedu.calidr.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.calidr.commons.util.StringUtil;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * An UI component that displays all information of a {@code Task}.
 */
public class TaskPopOver extends UiPart<Region> {

    private static final String FXML = "TaskEntryPopOver.fxml";

    @FXML
    private Label description;

    @FXML
    private Label doneTick;

    @FXML
    private Label toBy;

    @FXML
    private Label fromDate;

    @FXML
    private Label priority;

    @FXML
    private Label taskLocation;

    @FXML
    private Label title;

    @FXML
    private FlowPane tags;

    @FXML
    private Label toDate;

    @FXML
    private Button closeButton;


    /**
     * Creates a {@code TaskPopOver} with the given {@code Task} to display.
     *
     * @param task The task to display.
     */
    public TaskPopOver(Task task) {
        super(FXML);
        title.setText(task.getTitle().value);

        if (task.getDescription().isPresent()) {
            description.setText(task.getDescription().get().value);
        } else {
            description.setManaged(false);
            description.setVisible(false);
        }

        if (task.getLocation().isPresent()) {
            taskLocation.setText("@ " + task.getLocation().get().value);
        } else {
            taskLocation.setManaged(false);
            taskLocation.setVisible(false);
        }

        if (task instanceof Event) {
            EventDateTimes eventDateTimes = ((Event) task).getEventDateTimes();
            fromDate.setText(eventDateTimes.from.format(EventDateTimes.PRINT_FORMAT));
            toBy.setText("to");
            toDate.setText(eventDateTimes.to.format(EventDateTimes.PRINT_FORMAT));
        } else {
            ToDo todo = (ToDo) task;
            TodoDateTime todoDateTime = todo.getBy();
            fromDate.setVisible(false);
            fromDate.setManaged(false);
            toBy.setText("by");
            toDate.setText(todoDateTime.value.format(TodoDateTime.PRINT_FORMAT));

            if (todo.isDone()) {
                doneTick.setText("✔");
            }
        }

        priority.setText(StringUtil.capitalize(task.getPriority().toString()) + " priority");

        if (task.getTags().isEmpty()) {
            tags.setManaged(false);
            tags.setVisible(false);
        } else {
            task.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }

        closeButton.setOnAction(e -> this.getRoot().getScene().getWindow().hide());
    }
}
