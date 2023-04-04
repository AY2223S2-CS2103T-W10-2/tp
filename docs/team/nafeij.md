---
layout: page
title: Nafeij's Project Portfolio Page
---

# Project: Calidr

Calidr is a **time-management and scheduling calendar application**, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

Given below are my contributions to the project.

## Contributions

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2223s2.github.io/tp-dashboard/?search=nafeij&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2023-02-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=Nafeij&tabRepo=AY2223S2-CS2103T-W10-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements**:
  * **GUI enhancements**:
    * Adapted `PersonCard` to display `Task` data type.
    * Added `TaskListPanel`, adapted from `PersonListPanel`, to display tasks in a list.
    * Adapted `MainWindow` to support `CalendarView` and `TaskListPanel`.
  * **New Commands**:
    * `show` - Show the current task and full details in a focused pop-up.
    * `page` - Switch between calendar layouts.
    * `view` - Focus the calendar on a specific date.
  * **Other**:
    * Brainstormed name and designed logo for the project.

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Documentation**:
  * User Guide:
    * Added **Definitions** section to explain the meaning of terms used in the User Guide.
    * Added documentation for features `show`, `page`, `view`.
    * Did various cosmetic tweaks to existing documentation.
  * Developer Guide:
    * Added implementation details of the `Calendar Panel` GUI component.

* **Community**:
  * PRs authored (with non-trivial comments): [\#59](https://github.com/AY2223S2-CS2103T-W10-2/tp/pull/116), [\#116](https://github.com/AY2223S2-CS2103T-W10-2/tp/pull/59)
  * Contributions to forum discussions: [\#74](https://github.com/nus-cs2103-AY2223S2/forum/issues/74#issuecomment-1406057347), [\#76](https://github.com/nus-cs2103-AY2223S2/forum/issues/76#issuecomment-1406532217), [\#187](https://github.com/nus-cs2103-AY2223S2/forum/issues/187)

* **Tools**:
  * **CalendarFX integration**:
    * Extended `Event` class to support native `Task` data type, including conversion utils.
    * Redirect user-interactive navigation handlers to take key-input events.
    * Implemented [Observer pattern](https://nus-cs2103-ay2223s2.github.io/website/se-book-adapted/chapters/designPatterns.html#observer-pattern) to dynamically update calendar on changes to internal data.
    * Implemented support for multiple layouts: `DayPage`, `WeekPage`, `MonthPage`.

## Credits

* The calendar UI component was adapted from the [CalendarFX](https://dlsc.com/products/calendarfx/) library by [DLSC](https://dlsc.com/).
