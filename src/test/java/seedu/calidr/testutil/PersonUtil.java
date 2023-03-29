package seedu.calidr.testutil;

import static seedu.calidr.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.calidr.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;

import seedu.calidr.logic.commands.AddEventCommand;
import seedu.calidr.logic.commands.AddTodoCommand;
import seedu.calidr.model.tag.Tag;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;

/**
 * A utility class for Tasks.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(Task task) {
        if (task instanceof ToDo) {
            return AddTodoCommand.COMMAND_WORD + " " + getTodoDetails((ToDo) task);
        } else {
            return AddEventCommand.COMMAND_WORD + " " + getEventDetails((Event) task);
        }
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTodoDetails(ToDo todo) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE).append(todo.getTitle().value).append(" ");
        sb.append(PREFIX_BY).append(todo.getBy().value).append(" ");
        sb.append(PREFIX_PRIORITY).append(todo.getPriority().toString()).append(" ");
        if (todo.getDescription().isPresent()) {
            sb.append(PREFIX_DESCRIPTION).append(todo.getDescription().get().value).append(" ");
        }
        todo.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    // TODO: Add getEventDetails method

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK).append(remark.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
