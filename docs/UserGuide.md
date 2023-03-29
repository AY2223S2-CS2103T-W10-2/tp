---
layout: page
title: User Guide
---

Calidr is a **time-management and scheduling calendar application** for university students, optimized for use via a
Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). Calidr helps you
manage your time and tasks more effectively.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `calidr.jar`, available [here](https://github.com/AY2223S2-CS2103T-W10-2/tp/releases).

1. Copy the file to the folder you want to use as the _home directory_ for your Calidr.

1. Double click the `calidr.jar` file.<br>
   A GUI similar to the below should appear in a few seconds.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box at the top and press `Enter` to execute it.

   E.g. typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#paperclip-features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## :mag: Definitions

### Command definitions

<div markdown="block" class="alert alert-info">
🕮 This user guide uses a modifided version of the <a href="http://docopt.org/">docopt</a> command description language.
</div>

1. We will use the word "**string**" to describe any general sequence of characters.
1. We will use the word "**word**" to describe a sequence of characters terminated by a whitespace.
1. Words ending with a backslash `/` are interpreted as _compulsory_, _position-independent_ **options**.

   `t/`,`by/`,`from/`,`to/`
1. Uppercase words denote the position of **arguments**.
1. All other words that do not follow the above conventions are interpreted as **commands** and **subcommands**.

   `help`
3. Options can have arguments specified after a whitespace. Together they are refered to as a **field**.

   `todo t/ <title> by/ <date-time>`
1. An option's arguments, when bracketed by "<" and ">", can be **whitespace-containing** strings. They are terminated
   by a newline or a different option, matching *leftmost-first*

   `event t/ <title> from/ <start-date-time> to/ <end-date-time>`
1. Else, if the arguments are in uppercase, they must be **strictly words** terminated by whitespace

   `delete TASK_INDEX`
1. Some commands may accept a single *positional* argument string, known as a **main argument**, beginning after a
   whitespace following the command itself and terminating at a newline or the first option, matching *leftmost-first*.

   `edit TASK_INDEX t/ <title>`
1. Options (and arguments) bracketed with "\[" and  "\]" are **optional**.

   `[d/ <description>] [p/ <priority>]`
1. Ellipsis "..." specify that the argument (or field) to the left could be **repeated** multiple times:

   `[tag/ <tag>]...`
1. *All elements are required by default*, if not included in brackets "\[ \]". However, sometimes it is necessary to
   mark elements as **required** explicitly with parentheses "( )". For example, when you need to group
   mutually-inclusive elements (if *one* element is present, then *another* one is required):

   `edit TASK_INDEX (OPTION <argument>)...`

### Data terminology

1. Elements on a calendar are refered to as **Tasks**.
1. Tasks must have a **title**.
1. A task can have an optional **description**, **location**, and **comment**.
1. Tasks have an associated **priority**.
    1. Priority is one of `HIGH`, `MEDIUM` or `LOW`.
    3. Tasks have a default priority of `MEDIUM`.
1. Tasks must either be **ToDos** or **Events**.
    1. ToDos have a single associated **date-time** (e.g. a due date).
        1. ToDos also have an associated **status**.
            1. Status is one of `DONE` or `NOT DONE`.
            1. ToDos have a default status of `NOT DONE`.
    1. Events have *two* associated date-times (e.g a start and end-date).
1. Dates are specified in the format `DD-MM-YYYY`. Date-times are specified in the format `DD-MM-YYYY HHMM`. Calidr also
   accepts and automatically parser the following keywords as date-times:

   `today`, `tomorrow`, `next week`, `next month`, `next year`
1. Tasks are uniquely identified by their **task index**. This is given in the list panel, and is also used to refer to
   tasks in commands.

### UI terminology

![Ui](images/Ui_annotated.png)

a. **Calendar panel**: The calendar panel is where you can view your tasks graphically. Tasks are displayed as coloured
blocks, with green being **ToDos** and blue being **Events**. The calendar panel also displays the _focused_ date and
time, which [may not be the current time](#viewing-a-different-date-view).

b. **Command box**: The command box is where you can type in commands to Calidr. Pressing `Enter` will execute the
command.

c. **List panel**: The list panel is where Calidr will display the list of tasks that match your command. This usually
just shows all the tasks in the calendar, but can
be [filtered by title, date, or other criteria](#searching-and-filtering-for-tasks-search).

d. **Result panel**: The result panel is where Calidr will display the result of your command, including any errors.

## :paperclip: Features

#### View help: `help`

Shows a message explaning how to access the help page.
The help page provides information about how to use the application and its features.

#### Adding tasks

Please refer to the terminology [specified above](#data-terminology) if you are unfamiliar with the terms used.

<!-- end of the list -->

- #### Adding a ToDo: `todo`
  Adds a new ToDo with the given title, date-time, description, location and tags to the list of tasks.

  Format: `todo t/ <title> by/ <date-time> [d/ <description>] [l/ <location>] [p/ <priority>] [tag/ <tag>]...`

  Examples:
    - `todo t/ CS2103T tP by/ 31-03-2023 2359`
    - `todo t/ CS2103T tP by/ today d/ Complete the tP l/ Online p/ HIGH tag/ CS2103T tag/ tP`

- #### Adding an Event: `event`
  Adds a new event with the given title, starting date-time, ending date-time, description, location and tags to the
  list of tasks.

  Format: `event t/ <title> from/ <start-date-time> to/ <end-date-time> [d/ <description>] [l/ <location>] [p/ <priority>] [tag/ <tag>]...`

  Examples:
    - `event t/ CS2103T Lecture from/ 31-03-2023 1400 to/ 31-03-2023 1600`
    - `event t/ CS2103T Lecture from/ today to/ 31-03-2023 1600 d/ Lecture on UML l/ Online p/ HIGH tag/ CS2103T tag/ Lecture`

#### Searching and filtering for tasks: `search`

Searches for tasks with matching keywords in their fields. Filtered tasks are displayed in a list to the side.

<!--- TODO -->

#### Deleting a task: `delete`

Deletes one or more tasks from the calendar.

Format: `delete TASK_INDEX`

Example: `delete 2`

#### Editing task details: `edit`

Edits information pertaining to a particular task given by the index.

Format: `edit TASK_INDEX (OPTION <argument>)...`

Example: for a task which is an event of index 4,
`edit 4 from/ 05-03-2023 2000 to/ 06-03-2023 2000`

#### Switching to a different layout: `page`

By default, the calendar panel is displayed in a monthly-layout. A weekly or daily-layout is supported if you wish to
view more or less tasks.

Format: `page LAYOUT`, where `LAYOUT` is one of `month`, `week`, or `day`.

Example: `page week`

![A comparison of the three layouts](images/Ui_pages.png)

#### Viewing a different date: `view`

By default, the calendar panel is focused on the current date. When you wish to view a different month, week or day, you
can use the `view` command.

Format: `view <date-time>`.

Example:

- `view 01-03-2023`
- `view next week`

#### Showing all the details of a task: `show`

The GUI displays a condensed representation of tasks in both the calendar and list panels. This commmand shows all the
details of a particular task, including its description, location and tags, in a pop-up dialog.

Format: `show TASK_INDEX`

Example: `show 2`

![A pop-up dialog showing the details of a task](images/Ui_popup.png)

## Command summary

| Action          | Format, Examples                                                                                                                                                                                                                                                     |
|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Todo**    | `todo t/ <title> by/ <date-time> [d/ <description>] [l/ <location>] [p/ <priority>] [tag/ <tag>]...` <br> e.g., `event t/ CS2103T Lecture from/ today to/ 31-03-2023 1600 d/ Lecture on UML l/ Online p/ HIGH tag/ CS2103T tag/ Lecture`                             |
| **Add Event**   | `event t/ <title> from/ <start-date-time> to/ <end-date-time> [d/ <description>] [l/ <location>] [p/ <priority>] [tag/ <tag>]...` <br> e.g. `event t/ CS2103T Lecture from/ today to/ 31-03-2023 1600 d/ Lecture on UML l/ Online p/ HIGH tag/ CS2103T tag/ Lecture` |
| **Delete**      | `delete TASK_INDEX`<br> e.g., `delete 2`                                                                                                                                                                                                                             |
| **Edit**        | `edit TASK_INDEX (OPTION <argument>) ...`<br> e.g.,`edit 4 from/ 05-03-2023 2000 to/ 06-03-2023 2000`                                                                                                                                                                |
| **Help**        | `help`                                                                                                                                                                                                                                                               |
| **Switch Page** | `page LAYOUT`<br> e.g., `page day`                                                                                                                                                                                                                                   |
| **View Date**   | `view <date-time>`<br> e.g., `view next week`                                                                                                                                                                                                                        |
| **Show Task**   | `show TASK_INDEX`<br> e.g., `show 1`                                                                                                                                                                                                                                 |

### Other

#### Saving your data

Calidr calendar information is saved in the hard disk automatically after any modification command. The data is stored
as an [ics file](https://www.ical4j.org/).

#### Editing the data file

The `cal.ics` save file is located in the directory the program resides in (i.e. `[root_directory]/cal.ics`). Advanced
users are welcome to manipulate data directly by editing the save file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
    If your changes to the save file makes its format invalid, Calidr will discard all data and start with an empty data file the next time it's run.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous Calidr home directory.

--------------------------------------------------------------------------------------------------------------------

