# FinText User Guide

## Introduction

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their saving/spending,** and view a summary of their daily/weekly/monthly transactions. Application Data will be stored into a folder called `data`.

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
    * [Edit Transactions: `edit`](#edit-transactions-edit)
    * [Transaction Summary: `summary`](#transaction-summary-summary)
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
> * Argument names are case-sensitive, while argument values are case-insensitive. <br>
    e.g. `/type` and `/Type` are different argument. `/type in` and `/type IN` will indicate the `in` transaction type.
> * Additional supplied arguments or unnecessary description will be simply ignored.
> * User is intentionally not restricted to input future or past date to the `/date DATE` argument to allow for flexibility in managing their transactions.
> * Duplicate arguments are not accepted by the program. A message will be shown in such cases.
> * On MacOS, `Ctrl-c` and `Ctrl-d` will end the program safely and print the bye message.
> * On Windows, `Ctrl-c` will end the program safely and print the bye message.

### Viewing Help: `help`
Shows a list of all the commands available to the user.

User can also view more details of a command.

Format: `help COMMAND`

**Usage Example:**

`help in` - Shows details on how to use the `in` command.

`help delete` - Shows details on how to use the `delete` command.


### Adding an income entry: `in`
Adds an income towards a goal.

Format: `in DESCRIPTION /amount AMOUNT [/goal GOAL] [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`

* `DESCRIPTION` is case-sensitive, while the arguments are not.
* `AMOUNT` must be more than or equal to 0 and less than 10 million, it can contain at most 2 decimal points.
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must not be earlier than or equal to 1 period in the past (can be in the future).
    * i.e. If `RECURRENCE` is weekly, date specified must not be more than 6 days in the past.
* `RECURRENCE` is a string that indicates whether the income added is recurring.<br>
  Possible values are `none`, `daily`, `weekly` and `monthly`. If this option is not specified, recurrence defaults to `none`.
* If `GOAL` is specified, it must either be `Uncategorised` or the goal must already exist beforehand.

**Usage Example:**

`in part-time job /amount 500` <br>
Adds an income entry for 'part-time job' with an amount of 500. As goal is not specified, a default goal `Uncategorised` would be assigned to it.

`in red packet money /amount 50 /goal PS5 /date 18092023`<br>
Adds an income entry that happened on 18 Sept 2023 for 'red packet money' for an amount of 50 towards
a goal called 'PS5'.

`in pocket money saved /amount 25 /goal savings /recurrence weekly`<br>
Adds an income entry for 'pocket money saved' for an amount of 25 towards
a goal called 'savings' which recurs weekly.

**Sample Output**
```
Nice! The following income has been tracked:
Description                      Date          Amount        Goal                   Recurrence
Salary                           2023-11-14    300.00        Holiday                none
```

### Adding an expense entry: `out`
Adds an expense for a category.

Format: `out DESCRIPTION /amount AMOUNT [/category CATEGORY] [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`

* `DESCRIPTION` is case-sensitive, while the arguments are not.
* `AMOUNT` must be more than or equal to 0 and less than 10 million, it can contain at most 2 decimal points.
* `DATE` must be in format `DDMMYYYY`
  * If `RECURRENCE` is specified, date must not be earlier than or equal to 1 period in the past (can be in the future).
    * i.e. If `RECURRENCE` is weekly, date specified must not be more than 6 days in the past.
* `RECURRENCE` is a string that indicates whether the expense added is recurring.<br>
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

**Sample Output**
```
Nice! The following expense has been tracked:
Description                      Date          Amount        Category               Recurrence
11/11 Purchase                   2023-11-14    500.00        Uncategorised          none
```

### Delete Transaction: `delete`
Delete a specific transaction based on the index in the list.

Format: `delete INDEX /type (in | out)`
* `/type` only accepts `in` or `out`.
*  `INDEX` is based on the ID from the `list` command.


**Usage Example:**

`delete 1 /type in` - Deletes the first income entry.

`delete 2 /type out` - Deletes the second expense entry.


### List Transactions: `list`
Shows a sorted list of all added transactions based on type, with filters for goals, categories and recurrence, or shows a list of current goals and categories.

Formats: 

`list (goal | category)`

`list /type (in | out) [/goal GOAL] [/category CATEGORY] [/week] [/month]`

* Deletion has to be based on the ID of the transaction without any filters (e.g. only `list /type in` or `list /type out`).
* User must only specify either `/week` or `/month`. If both are specified, then `/week` will take priority.
* The list that would be printed will be sorted in descending order by date.
* If `list goal` or `list category` is used, there must not be any other arguments that come after that.
* If arguments are specified, such as `list /type in`, there should not be anything before the argument. (`list goal /type in` would be considered an invalid command)
* The maximum supported goal progress percentage is `99999999.99%`, if exceeded, the goal progress percentage will be truncated 
* For 'Uncategorised' goal, there would not be any progress shown as there is no target amount allowed.

**Usage Example:**

`list /type in` - List all income transactions

`list /type out` - List all expense transactions

`list /type in /goal Travel` - List all income transactions with the 'Travel' goal

`list /type out /category Food` - List all expense transactions with the 'Food' category

`list /type in /week` - List all income transactions in the current week

`list /type in /month` - List all income transactions in the current month

`list goal` - Lists all current goals and the progress towards each goal

`list category` - Lists all current categories and the spending for each category

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
```
==================================== Goals Status ====================================
Name                   Amount                 Progress
Savings                300.00/500.00          [============        ] 60.00%
Uncategorised          300.00

Unused Goals:
Goal                   Target Amount
Tuition                300.00
==================================== Goals Status ====================================
```
### Add/Remove a goal: `goal`
Creates or deletes a user's goal (used for income)

Format: `goal [/add GOAL /amount AMOUNT] [/remove GOAL]`
* Only either `/add` or `/remove` can be provided. They should not be provided together.
* `GOAL` is case-insensitive
* `/add GOAL` has to be accompanied with `/amount AMOUNT`
* `AMOUNT` has to be a value of at least 0.01 and less than 10 million, and it can contain at most 2 decimal points.

### Add/Remove a category: `category`
Creates or deletes a user's category (used for expenses)

Format: `category [/add CATEGORY] [/remove CATEGORY]`
* Only either `/add` or `/remove` can be provided. They should not be provided together.
* `CATEGORY` is case-insensitive.


### Export Transactions: `export`
Exports all transaction data into a CSV file called `Transactions.csv`

Format: `export [/type (in | out)]`
* If `/type` is not specified, by default it will extract **ALL** transactions.
* In any scenario where any error is encountered when exporting the transactions, the message displayed will be `Cannot create file`.

**Usage Example:**

`export /type in` - Export all in transactions

`export /type out` - Export all out transactions

### Edit Transactions: `edit`
Edits an existing transaction.

Format: `edit INDEX /type (in | out) [/description DESCRIPTION] [/amount AMOUNT] [/goal GOAL] [/category CATEGORY]`

- User must specify /type option to edit either a transaction under income or expense.
- User must specify a valid income/expense transaction index.
- User must only specify at least either `/description`, `/amount`, `/goal` (if editing an income transaction) or `/category` (if editing an expense transaction).
- User cannot edit the date field.
- User cannot edit the recurrence field.
- In case of editing a goal, it must exist beforehand.
- The same constraints that apply when adding income/expenses also apply here.

**Usage Example:**

`edit 1 /type in /description allowance` - Edits the description of the first income transaction to be "allowance".

`edit 2 /type in /goal ps5` - Edits the goal of the second income transaction to be ps5.

`edit 2 /type out /amount 10` - Edits the amount of the second expense transaction to be 10.

`edit 2 /type out /description grab /amount 10 /category transport` - Edits the second expense transaction description to `grab`, amount to `10`, category to `transport`.

**Sample Output**
```
> User: list /type in
Alright! Displaying 1 transaction.
=========================================== IN TRANSACTIONS ===========================================
ID    Description                      Date         Amount       Goal                   Recurrence
1     Salary                           2023-11-14   300.00       Holiday                none
=========================================== IN TRANSACTIONS ===========================================
> User: edit 1 /type in /goal Uncategorised
Successfully edited income no.1 Salary
> User: list /type in
Alright! Displaying 1 transaction.
=========================================== IN TRANSACTIONS ===========================================
ID    Description                      Date         Amount       Goal                   Recurrence
1     Salary                           2023-11-14   300.00       Uncategorised          none
=========================================== IN TRANSACTIONS ===========================================
```

### Transaction Summary: `summary`
Shows the summarised total of transactions.

Format: `summary /type (in | out) [/day] [/week] [/month]`
* User must specify /type option to list either transactions added under income or expense
* If neither `/day`, `/week` or `/month` are shown, then all transactions under income or expense will be summarised according to the `/type` specified. 
* User must only specify either /day or /week or /month. If multiple are specified, then they will take priority in the order of `day`, `week`, `month`. 
  * If both `/day` and `/week` are specified, then `/day` result will be shown.
  * If `/week` and `/month` are specified, then `/week` result will be shown.
  * If `/day`, `/week` and `/month` are all specified, then `/day` result will be shown.
* `/day` will filter the transactions to those of the current day.
* `/week` will filter the transactions to those in the current week.
* `/month` will filter the transactions to those in the current month.

**Usage Example:**

`summary /type in` - Shows the summarised total for income.

`summary /type out` - Shows the summarised total for expense.

`summary /type in /day` - Shows the summarised total for income of the current day.

`summary /type out /week` - Shows the summarised total for expense in the current week.

`summary /type out /month` - Shows the summarised total for expense in the current month.

```
> User: summary /type in
Good job! Total income so far: $300.00
> User: summary /type out
Wise spending! Total expense so far: $500.00
```


### End Program: `bye`
Safely ends the program.

## Command Summary

| Action                  | Format                                                                                        | Example                                                                          |
|-------------------------|-----------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| Help                    | `help`                                                                                        |                                                                                  |
| Adding an income entry  | `in DESCRIPTION /amount AMOUNT /goal GOAL [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]`  | `in part-time job /amount 500 /goal car`                                         |
| Adding an expense entry | `out DESCRIPTION /amount AMOUNT /category CATEGORY [/date DATE in DDMMYYYY] [/recurrence RECURRENCE]` | `out dinner /amount 10.50 /category food`                                        |
| Delete Transaction      | `delete INDEX /type (in | out)`                                                                            | `delete 1 /type in`                       |
| List Transactions       | `list /type (in | out) [/goal GOAL] [/category CATEGORY] [/week] [/month]`                         | `list /type in`                           |
| Add/Remove a Goal       | `goal [/add GOAL /amount AMOUNT] [/remove GOAL]`                                              | `goal /add PS5 /amount 600`                                                      |
| Add/Remove a Category   | `category [/add CATEGORY] [/remove CATEGORY]`                                                 | `category /add Bills`                                                            |
| Export Transactions     | `export [/type (in | out)]`                                                                           | `export /type in`                         |
| Edit Transaction        | `edit INDEX /type (in | out) (/description DESCRIPTION | /amount AMOUNT | /goal GOAL | /category CATEGORY)` | `edit 2 /type in /goal ps5`         |
| Transaction Summary     | `summary /type (in | out) [/day] [/week] [/month]`                                                    | `summary /type in /day`                   |
| End program             | `bye`                                                                                         |                                                                                  |
