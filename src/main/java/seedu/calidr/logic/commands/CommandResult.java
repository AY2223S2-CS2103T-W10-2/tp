package seedu.calidr.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import seedu.calidr.model.PageType;
import seedu.calidr.model.task.Task;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The view type of the calendar panel.
     */
    private final Optional<PageType> pageType;

    /**
     * The date of the calendar panel.
     */
    private final Optional<LocalDate> date;

    /**
     * The task to be shown in the popup.
     */
    private final Optional<Task> popupTask;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp,
                         boolean exit,
                         PageType pageType,
                         LocalDate date,
                         Task popupTask
    ) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.pageType = Optional.ofNullable(pageType);
        this.date = Optional.ofNullable(date);
        this.popupTask = Optional.ofNullable(popupTask);
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, null, null);
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Optional<PageType> getPageType() {
        return pageType;
    }

    public Optional<LocalDate> getDate() {
        return date;
    }

    public Optional<Task> getPopupTask() {
        return popupTask;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && pageType.equals(otherCommandResult.pageType)
                && date.equals(otherCommandResult.date)
                && popupTask.equals(otherCommandResult.popupTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, pageType, date, popupTask);
    }

}
