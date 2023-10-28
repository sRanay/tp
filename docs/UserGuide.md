# FinText User Guide

## Introduction

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their spending,** and generate daily/weekly/monthly reports to break down how they spend.

* [Quick Start](#quick-start)
* [Features](#features)
    * [Viewing Help: `help`](#viewing-help-help)
    * [Adding an income entry: `in`](#adding-an-income-entry-in)
    * [Adding an expense entry: `out`](#adding-an-expense-entry-out)
    * [Delete Transaction: `delete`](#delete-transaction-delete)
    * [List Transactions: `list`](#list-transactions-list)
    * [End Program: `bye`](#end-program-bye)
* [Command Summary](#command-summary)


## Quick Start

1. Ensure that you have Java 11 installed on your computer.
2. Download the latest version of `FinText` from [here](https://github.com/AY2324S1-CS2113-W12-3/tp/releases).

## Features

> * `UPPER_CASE` denotes user-supplied parameters, and arguments with square brackets<br> e.g. `[/date DATE]` denote
    optional arguments, while arguments not in square brackets are mandatory.
> * Any text e.g. `DESCRIPTION` has to come before arguments.<br>
    `in Salary /amount 500 /goal  Savings` is a valid command, while `in /amount 500 /goal Savings Salary` is not a valid
    command.
> * Arguments can be in any order. <br>
    e.g. if a command has the arguments `/amount AMOUNT /goal GOAL`, `/goal GOAL /amount AMOUNT` is acceptable as well.


### Viewing Help: `help`
Shows a list of all the commands available to the user.

### Adding an income entry: `in`
Adds an income towards a goal.

Format: `in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`

* `DESCRIPTION` is case-sensitive, while the arguments are not.
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must be less than 1 period away.
    * i.e. If `RECURRENCE` is weekly, date specified must be less than 7 days away from current date.
* `RECURRENCE` is a string that indicates whether of the income added is recurring.<br>
  Possible values are `none`, `daily`, `weekly` and `monthly`. If this option is not specified, recurrence defaults to `none`.

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
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must be less than 1 period away.
    * i.e. If `RECURRENCE` is weekly, date specified must be less than 7 days away from current date.
* `RECURRENCE` is a string that indicates whether of the expense added is recurring.<br>
  Possible values are `none`, `daily`, `weekly` and `monthly`. If this option is not specified, recurrence defaults to `none`.

**Usage Example:**

`out dinner /amount 10.50 /category food` <br>
Adds an expense entry for 'dinner' with an amount of 10.50 towards the 'food' category.

`out pokemon card pack /amount 10.50 /category food /date 18092023`<br>
Adds an expense entry that happened on 18 Sept 2023 for 'pokemon card pack' for an amount of 10.50 towards
the 'game' category.

`out spotify /amount 9 /category entertainment /recurring monthly`<br>
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
Shows a list of all added transactions based on type, with filters for goals and categories.

Format: `list /type (in | out) [/goal GOAL] [/category CATEGORY]`
* User must specify /type option to list either transactions added under income or expense
* Deletion has to be based on the ID of the transaction without any filters.

**Usage Example:**

`list /type in` - List all income transactions

`list /type out` - List all expense transactions

`list /type in /goal Travel` - List all income transactions with the 'Travel' goal

`list /type out /category Food` - List all expense transactions with the 'Food' category

**Sample Output:**
```
==================== IN TRANSACTIONS =====================
ID   Description   Date          Amount      Goal
1    Random        12 SEP 2023   $10         TRAVEL
2    Hongbao       13 SEP 2023   $10         KOREA STUDIES
==================== IN TRANSACTIONS =====================
```

### Export Transactions: `export`
Exports all transaction data into a CSV file called `Transactions.csv`

Format: `export [/type (in \| out)]`
* If `/type` is not specified, by default it will extract **ALL** transactions.

**Usage Example:**

`export /type in` - Export all in transactions

`export /type out` - Export all out transactions

### End Program: `bye`
Safely ends the program.

## Command Summary

| Action                  | Format                                                                       | Example                                   |
|-------------------------|------------------------------------------------------------------------------|-------------------------------------------|
| Help                    | `help`                                                                       |                                           |
| Adding an income entry  | `in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY]`          | `in part-time job /amount 500 /goal car`  |
| Adding an expense entry | `out DESCRIPTION /amount AMOUNT /category CATEGORY [/date DATE in DDMMYYYY]` | `out dinner /amount 10.50 /category food` |
| Delete Transaction      | `delete INDEX /type (in \| out)`                                             | `delete 1 /type in`                       |
| List Transactions       | `list /type (in \| out) [/goal GOAL] [/category CATEGORY]`                   | `list /type in`                           |
| Export Transactions     | `export [/type (in \| out)]`                                                 | `export /type in`                         |
| End program             | `bye`                                                                        |                                           |



