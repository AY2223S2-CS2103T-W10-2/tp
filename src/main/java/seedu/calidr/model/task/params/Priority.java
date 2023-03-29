package seedu.calidr.model.task.params;

/**
 * Represents the priority associated with each task.
 */
public enum Priority {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    public static final String MESSAGE_CONSTRAINTS = "Priority should be low/medium/high";

    private final String string;

    Priority(String string) {
        this.string = string;
    }

    /**
     * Returns the string representation of the Priority.
     */
    @Override
    public String toString() {
        return string;
    }
}
