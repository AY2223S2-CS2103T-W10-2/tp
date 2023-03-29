package seedu.calidr.model.task.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.calidr.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code Title} contains any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Task> {

    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        String titleValue = task.getTitle().value.toLowerCase();
        return keywords.stream()
                .anyMatch(keyword -> titleValue.contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }


}
