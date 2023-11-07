# FinText User Guide

## Introduction

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their spending,** and generate daily/weekly/monthly reports to break down how they spend. Application Data will be stored into a folder called `data`.

* [Quick Start](#quick-start)
* [Features](#features)
    * [Viewing Help: `help`](#viewing-help-help)
    * [Adding an income entry: `in`](#adding-an-income-entry-in)
    * [Adding an expense entry: `out`](#adding-an-expense-entry-out)
    * [Delete Transaction: `delete`](#delete-transaction-delete)
    * [List Transactions: `list`](#list-transactions-list)
    * [Add/Remove Goal: `goal`](#addremove-a-goal-goal)
    * [Add/Remove Category: `category`](#addremove-a-category-category)
    * [Export Transactions: `export`](#export-transactions-export)
    * [Edit Transactions: `[Coming Soon]`](#edit-transactions-coming-soon)
    * [End Program: `bye`](#end-program-bye)
* [Command Summary](#command-summary)


## Quick Start

1. Ensure that you have Java 11 installed on your computer.
2. Download the latest version of `FinText` from [here](https://github.com/AY2324S1-CS2113-W12-3/tp/releases).
3. Run the program by `java -jar FinText.jar`

## Features

> * `UPPER_CASE` denotes user-supplied parameters, and arguments with square brackets<br> e.g. `[/date DATE]` denote
    optional arguments, while arguments not in square brackets are mandatory.
> * Any text e.g. `DESCRIPTION` has to come before arguments.<br>
    `in Salary /amount 500 /goal  Savings` is a valid command, while `in /amount 500 /goal Savings Salary` is not a valid
    command.
> * Arguments can be in any order. <br>
    e.g. if a command has the arguments `/amount AMOUNT /goal GOAL`, `/goal GOAL /amount AMOUNT` is acceptable as well.
> * Additional supplied arguments will be simply ignored.
> * User is intentionally not restricted to input future or past date to the `/date DATE` argument to allow for flexibility in managing their transactions.
> * Duplicate arguments are not accepted by the program. A message will be shown in such cases.

### Viewing Help: `help`
Shows a list of all the commands available to the user.

User can also view more details of a command.

Format: `help COMMAND`

**Usage Example:**

`help in` - Shows details on how to use the `in` command.

`help delete` - Shows details on how to use the `delete` command.


### Adding an income entry: `in`
Adds an income towards a goal.

Format: `in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`

* `DESCRIPTION` is case-sensitive, while the arguments are not.
* `AMOUNT` must be more than or equal to 0.
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must not be earlier than or equal to 1 period in the past (can be in the future).
    * i.e. If `RECURRENCE` is weekly, date specified must not be more than 6 days in the past.
* `RECURRENCE` is a string that indicates whether of the income added is recurring.<br>
  Possible values are `none`, `daily`, `weekly` and `monthly`. If this option is not specified, recurrence defaults to `none`.
* `GOAL` must already exist beforehand, if not the user would be prompted to create the goal first.

**Usage Example:**

`in part-time job /amount 500 /goal car` <br>
Adds an income entry for 'part-time job' with an amount of 500 towards a goal called 'car'.

`in red packet money /amount 50 /goal PS5 /date 18092023`<br>
Adds an income entry that happened on 18 Sept 2023 for 'red packet money' for an amount of 50 towards
a goal called 'PS5'.

`in pocket money saved /amount 25 /goal savings /recurrence weekly`<br>
Adds an income entry for 'pocket money saved' for an amount of 25 towards
a goal called 'savings' which recurs weekly.

### Adding an expense entry: `out`
Adds an expense for a category.

Format: `out DESCRIPTION /amount AMOUNT /category CATEGORY [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`

* `DESCRIPTION` is case-sensitive, while the arguments are not.
* `AMOUNT` must be more than or equal to 0.
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must not be earlier than or equal to 1 period in the past (can be in the future).
    * i.e. If `RECURRENCE` is weekly, date specified must not be more than 6 days in the past.
* `RECURRENCE` is a string that indicates whether of the expense added is recurring.<br>
  Possible values are `none`, `daily`, `weekly` and `monthly`. If this option is not specified, recurrence defaults to `none`.
* If `CATEGORY` was not created previously, a category would automatically be created for it.

**Usage Example:**

`out dinner /amount 10.50 /category food` <br>
Adds an expense entry for 'dinner' with an amount of 10.50 towards the 'food' category.

`out pokemon card pack /amount 10.50 /category food /date 18092023`<br>
Adds an expense entry that happened on 18 Sept 2023 for 'pokemon card pack' for an amount of 10.50 towards
the 'game' category.

`out spotify /amount 9 /category entertainment /recurrence monthly`<br>
Adds an expense entry for 'pokemon card pack' for an amount of 9 towards
the 'entertainment' category which recurs monthly.

### Delete Transaction: `delete`
Delete a specific transaction based on the index in the list.

Format: `delete INDEX /type (in | out)`
* `/type` only accepts `in` or `out`.
*  `INDEX` is based on the ID from the `list` command.


**Usage Example:**

`delete 1 /type in` - Deletes the first income entry.

`delete 2 /type out` - Deletes the second expense entry.


### List Transactions: `list`
Shows a sorted list of all added transactions based on type, with filters for goals and categories.

Format: `list /type (in | out) [/goal GOAL] [/category CATEGORY] [/week] [/month]`
* User must specify /type option to list either transactions added under income or expense
* Deletion has to be based on the ID of the transaction without any filters.
* User must only specify either `/week` or `/month`. If both are specified, then `/week` will take priority.
* The list that would be printed will be sorted descending by date, then transaction description.

**Usage Example:**

`list /type in` - List all income transactions

`list /type out` - List all expense transactions

`list /type in /goal Travel` - List all income transactions with the 'Travel' goal

`list /type out /category Food` - List all expense transactions with the 'Food' category

`list /type in /week` - List all income transactions in the current week

`list /type in /month` - List all income transactions in the current month

**Sample Output:**
```
Alright! Displaying 3 transactions.
====================================== IN TRANSACTIONS ======================================
ID    Description                      Date         Amount       Goal         Recurrence
1     part-time job                    2023-10-31   500.00       car          none
2     pocket money saved               2023-10-31   25.00        PS5          weekly
3     red packet money                 2023-09-18   50.00        PS5          none
====================================== IN TRANSACTIONS ======================================
```
### Add/Remove a goal: `goal`
Creates or deletes a user's goal (used for income)

Format: `goal [/add GOAL /amount AMOUNT] [/remove GOAL]`
* Only either `/add` or `/remove` can be provided. They should not be provided together.
* `GOAL` is case-insensitive.
* `/add GOAL` has to be accompanied with `/amount AMOUNT`.

### Add/Remove a category: `category`
Creates or deletes a user's category (used for expenses)

Format: `category [/add CATEGORY] [/remove CATEGORY]`
* Only either `/add` or `/remove` can be provided. They should not be provided together.
* `CATEGORY` is case-insensitive.


### Export Transactions: `export`
Exports all transaction data into a CSV file called `Transactions.csv`

Format: `export [/type (in | out)]`
* If `/type` is not specified, by default it will extract **ALL** transactions.

**Usage Example:**

`export /type in` - Export all in transactions

`export /type out` - Export all out transactions

### Edit Transactions `[Coming Soon]`
Details coming soon...

### End Program: `bye`
Safely ends the program.

## Command Summary

| Action                  | Format                                                                                                | Example                                   |
|-------------------------|-------------------------------------------------------------------------------------------------------|-------------------------------------------|
| Help                    | `help`                                                                                                |                                           |
| Adding an income entry  | `in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`          | `in part-time job /amount 500 /goal car`  |
| Adding an expense entry | `out DESCRIPTION /amount AMOUNT /category CATEGORY [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]` | `out dinner /amount 10.50 /category food` |
| Delete Transaction      | `delete INDEX /type (in | out)`                                                                       | `delete 1 /type in`                       |
| List Transactions       | `list /type (in | out) [/goal GOAL] [/category CATEGORY] [/week] [/month]`                            | `list /type in`                           |
| Add/Remove a Goal       | `goal [/add GOAL /amount AMOUNT] [/remove GOAL]`                                                      | `goal /add PS5 /amount 600`               |
| Add/Remove a Category   | `category [/add CATEGORY] [/remove CATEGORY]`                                                         | `category /add Bills`                     |
| Export Transactions     | `export [/type (in | out)]`                                                                           | `export /type in`                         |
| End program             | `bye`                                                                                                 |                                           |



