package seedu.calidr.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.calidr.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.calidr.commons.core.index.Index;
import seedu.calidr.commons.util.StringUtil;
import seedu.calidr.logic.parser.exceptions.ParseException;
import seedu.calidr.model.PageType;
import seedu.calidr.model.task.params.Description;
import seedu.calidr.model.task.params.EventDateTimes;
import seedu.calidr.model.task.params.Location;
import seedu.calidr.model.task.params.Priority;
import seedu.calidr.model.task.params.Tag;
import seedu.calidr.model.task.params.Title;
import seedu.calidr.model.task.params.TodoDateTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final Map<String, LocalDateTime> KEY_DATE_MAP = Map.of(
            "today", LocalDateTime.now(),
            "tomorrow", LocalDateTime.now().plusDays(1),
            "next week", LocalDateTime.now().plusWeeks(1),
            "next month", LocalDateTime.now().plusMonths(1),
            "next year", LocalDateTime.now().plusYears(1),
            "heat death of universe", LocalDateTime.MAX
    );

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    //===============================For Calidr====================================================

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Description.isValidDescription(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String dateText} into a {@code LocalDate} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dateText The string containing the date.
     * @return A {@code LocalDate} object representing the given date .
     * @throws ParseException if the given {@code dateText} is invalid.
     */
    public static LocalDate parseDate(String dateText) throws ParseException {
        dateText = dateText.trim();
        Optional<LocalDateTime> keyDateTime = parseDateKeyword(dateText);
        if (keyDateTime.isPresent()) {
            return keyDateTime.get().toLocalDate();
        }
        try {
            return LocalDate.parse(dateText, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException("Date-times should be of the format DD-MM-YYYY");
        }
    }

    /**
     * Parses a {@code String dateTimeText} into a {@code LocalDateTime} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dateTimeText The string containing the date and time.
     * @return A {@code LocalDateTime} object representing the given date and time.
     * @throws ParseException if the given {@code dateTimeText} is invalid.
     */
    public static LocalDateTime parseDateTime(String dateTimeText) throws ParseException {
        dateTimeText = dateTimeText.trim();
        Optional<LocalDateTime> keyDateTime = parseDateKeyword(dateTimeText);
        if (keyDateTime.isPresent()) {
            return keyDateTime.get();
        }
        try {
            return LocalDateTime.parse(dateTimeText, DATETIME_FORMAT);

        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date-time."
                    + "Date-times should be of the format DD-MM-YYYY hhmm"
                    + "and correspond to existing dates.");
        }
    }

    /**
     * Parses a {@code String todoDateTime} into a {@code TodoDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code todoDateTime} is invalid.
     */
    public static TodoDateTime parseTodoDateTime(String todoDateTime) throws ParseException {
        requireNonNull(todoDateTime);

        LocalDateTime byDateTime = parseDateTime(todoDateTime);

        return new TodoDateTime(byDateTime);
    }

    /**
     * Parses a {@code String fromDateTime} and {@code String toDateTime}
     * into a {@code EventDateTimes}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code String fromDateTime} and
     *                        {@code String toDateTime} are invalid.
     */
    public static EventDateTimes parseEventDateTimes(String fromDateTime, String toDateTime)
            throws ParseException {

        requireAllNonNull(fromDateTime, toDateTime);

        LocalDateTime from = parseDateTime(fromDateTime);
        LocalDateTime to = parseDateTime(toDateTime);

        if (!EventDateTimes.isValidEventDateTimes(from, to)) {
            throw new ParseException(EventDateTimes.MESSAGE_CONSTRAINTS);
        }

        return new EventDateTimes(from, to);
    }

    /**
     * Parses a shorthand into a {@code LocalDateTime} object.
     * {@code dateTimeText} can be one of the following:
     * - "today"
     * - "tomorrow"
     * - "next week"
     * - "next month"
     * - "next year"
     *
     * @param dateTimeText The shorthand.
     * @return A {@code LocalDateTime} object representing the given date and time.
     */
    public static Optional<LocalDateTime> parseDateKeyword(String dateTimeText) {
        return Optional.of(dateTimeText).map(KEY_DATE_MAP::get);
    }

    /**
     * Parses a {@code String pageType} into a {@code PageType}.
     *
     * @param args The string containing the page type.
     * @return A {@code PageType} object representing the given page type.
     * @throws ParseException if the given {@code pageType} is invalid.
     */
    public static PageType parsePageType(String args) throws ParseException {
        requireNonNull(args);
        PageType pageType;
        try {
            pageType = PageType.valueOf(args.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid page type.");
        }
        return pageType;
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     *
     * @param priorityString The string containing the priority.
     * @return A {@code Priority} object representing the given priority.
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priorityString) throws ParseException {
        requireNonNull(priorityString);

        Priority priority;
        try {
            priority = Priority.valueOf(priorityString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        return priority;
    }

    //@@author vaidyanaath-reused
    // Reused from AB3 code.

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
