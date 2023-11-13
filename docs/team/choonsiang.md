# Chan Choon Siang's Project Portfolio Page

## Project: FinText

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their saving/spending,** and view a summary of their daily/weekly/monthly transactions.

Given below are my contributions to the project.

- New Feature: Added a Parser to parse user command and arguments.
    - What it does: The parser will first parse the user input to separate the command, description and arguments. 
Based on those, it will create and return the relevant Command object.
    - Justification: This will allow for the separation of user input into the information needed for the Command to run.
    - Highlights: The arguments input by the user does not need to be in a fixed order due to the use of HashMap to store 
  the argument and value associated with it. Duplicate arguments provided by the user will also be detected and not allowed.
- New Feature: Added a Remove transaction command.
    - What it does: It will allow for the removal of the income/expense transaction.
    - Justification: This will allow user to remove transactions that they no longer want to track or transactions with 
  errors in them.
    - Highlights: It will ensure that only valid income/transaction index can be removed safely.
- New Feature: Added Summary command.
    - What it does: This command will allow the user to view their total income/expense.
    - Justification: This allows the user to have an overview of their current saving/spending habits.
    - Highlights: The user can choose to view the total sum of the income/expense or can choose to filter by day/week/month 
  to monitor their transactions in a more detailed manner.
- Code contributed: [RepoSense Link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=choonsiang&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=ChoonSiang&tabRepo=AY2324S1-CS2113-W12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- Enhancements to existing features:
    - Add feature to allow user to list transaction by week and month. (Pull request [#80](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/80))
    - Fixed storage location for files during unit tests overwriting the actual storage files. (Pull request [#91](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/91))
    - Modify EditTransactionCommand to edit multiple values at once. (Pull request [#167](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/167))
- Contributions to the UG:
    - Updated `list` command to show filtering by week and month. [#80](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/80)
    - Added examples for `help` command. [#90](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/90)
    - Updated on the program behavior when duplicate arguments are specified. [#134](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/134)
    - Updated description for Edit Transaction feature to allow multiple arguments. [#168](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/168)
- Contributions to the DG:
    - Added implementation details of `Parser`. [#54](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/54)
    - Added details of `Income` and `Expense` classes, and their class diagram. [#82](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/82)
    - Added Architecture diagram and sequence diagram for `Parser`. [#82](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/82)
    - Added Manual tests instruction. [#158](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/158)
    - Added implementation details for Goal, Category, Delete Transaction and Summary features. [#162](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/162)
    - Added implementation details of Edit Transaction feature. [#168](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/168)
- Community:
    - PR reviewed (with non-trivial review comments): [#11](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/11), [#32](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/32), [#81](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/81), [#137](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/137)
    - Reported bugs and suggestions for other teams in the class(examples: [1](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/141), [2](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/156), [3](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/165), [4](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/161), [5](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/148), [6](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/143), [7](https://github.com/AY2324S1-CS2113-T18-3/tp/issues/140))
