package seedu.calidr.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.PageBase;
import com.calendarfx.view.page.WeekPage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.InputEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import seedu.calidr.commons.core.LogsCenter;
import seedu.calidr.commons.util.TaskEntryUtil;
import seedu.calidr.model.PageType;
import seedu.calidr.model.ReadOnlyTaskList;
import seedu.calidr.model.TaskEntry;
import seedu.calidr.model.task.Event;
import seedu.calidr.model.task.Task;
import seedu.calidr.model.task.ToDo;

/**
 * Panel containing the CalendarFX DetailedWeekView.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    private final CalendarSource calendarSource = new CalendarSource("My Calendars");

    private final Calendar<TaskEntry> calendarTodos = new Calendar<>("Todos");

    private final Calendar<TaskEntry> calendarEvents = new Calendar<>("Events");

    private final Map<Class<? extends Task>, Calendar<TaskEntry>> taskEntryCalendarMap = new HashMap<>();

    @FXML
    private VBox calendarPanel;

    @FXML
    private MonthPage monthPage;

    @FXML
    private WeekPage weekPage;

    @FXML
    private DayPage dayPage;

    private final Map<PageType, PageBase> calendarViews = Map.of(
            PageType.DAY, dayPage,
            PageType.WEEK, weekPage,
            PageType.MONTH, monthPage
    );

    private PageType currentPage = PageType.MONTH;

    private Thread updateTimeThread;

    /**
     * Instantiates a new CalendarPanel.
     */
    public CalendarPanel() {
        super(FXML);
        calendarPanel.addEventFilter(InputEvent.ANY, event -> {
            if (!(event instanceof ScrollEvent)) {
                event.consume();
            }
        });

        calendarTodos.setReadOnly(true);
        calendarEvents.setReadOnly(true);
        calendarTodos.setStyle(Calendar.Style.STYLE1);
        calendarEvents.setStyle(Calendar.Style.STYLE2);

        taskEntryCalendarMap.put(ToDo.class, calendarTodos);
        taskEntryCalendarMap.put(Event.class, calendarEvents);

        calendarSource.getCalendars().addAll(calendarTodos, calendarEvents);

        dayPage.getDetailedDayView()
                .getDayView()
                .setEarlyLateHoursStrategy(DayViewBase
                        .EarlyLateHoursStrategy.SHOW);
        calendarViews.forEach((key, view) -> {
            view.setContextMenuCallback(param -> null);
            view.setShowNavigation(false);
            view.getCalendarSources().add(calendarSource);
            view.setBackground(Background.fill(Paint.valueOf("#ffffff")));
            view.setRequestedTime(LocalTime.now());
            view.managedProperty().bind(view.visibleProperty());
        });
        setPage(currentPage);
        goToToday();
    }

    public PageBase getActivePage() {
        return calendarViews.get(currentPage);
    }

    public void setAllPages(Consumer<PageBase> consumer) {
        calendarViews.forEach((key, view) -> consumer.accept(view));
    }

    public void setDate(LocalDate date) {
        setAllPages(view -> view.setDate(date));
    }

    public void goToToday() {
        setAllPages(PageBase::goToday);
    }

    public void goToNext() {
        setAllPages(PageBase::goForward);
    }

    public void goToPrevious() {
        setAllPages(PageBase::goBack);
    }

    public void nextPage() {
        setPage(currentPage.next(currentPage));
    }

    public void setPage(PageType pageType) {
        currentPage = pageType;
        calendarViews.forEach((key, view) -> view.setVisible(key == pageType));
    }

    private void spawnUpdateThread() {
        updateTimeThread = new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                    getActivePage().setToday(LocalDate.now());
                    getActivePage().setTime(LocalTime.now());
                });

                try {
                    // update every 10 seconds
                    //noinspection BusyWait
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    /**
     * Update the calendar entries with the given task list.
     *
     * @param taskList the task list
     */
    public void updateCalendar(ReadOnlyTaskList taskList) {
        taskEntryCalendarMap.values().forEach(Calendar::clear);
        taskList.getTaskList().forEach(task -> {
            Class<? extends Task> taskClass = task.getClass();
            if (taskEntryCalendarMap.containsKey(taskClass)) {
                Calendar<TaskEntry> calendar = taskEntryCalendarMap.get(taskClass);
                calendar.addEntry(TaskEntryUtil.convert(task));
            }
        });
    }
}
