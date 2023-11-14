---
title: Jun Hong's Project Portfolio Page
---

### Project: FinText
FinText is a Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage their saving/spending, and view a summary of their daily/weekly/monthly transactions.

Given below are my contributions to the project.

- **New Feature:** Added command to list transactions
    * What it does: Allow the user to list goal/category status, e.g. view progress towards a certain goal, or how much they have spent on a certain category. It also allows the user to view all incoming/outgoing transactions
    * Justification: This feature is essential as it allows a user to review what they have added for their income and spending.
    * Highlights: The list command takes in information from commands developed by other developers and formats it into presents it in an easily-readable format to the user.
* **New Feature:** Added goal and category command
    * What it does: Allows a user to create new goals/categories to track their saving/spending habits by grouping it into a group. It also allows them to delete previously created goals/categories. 
    * Justification: This feature would allow a user to set a goal amount for them to work towards. It would also let a user group their spending into various categories and view it using the list command.
    * Highlights: When viewed using 'list goal', the program would show a user their progress towards all their goals.


*  **Code Contributed:** [RepoSense Link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=hooami&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22)

* **Enhancements to existing features:**
  * Allowed a user to not specify a goal or category when adding income or expenses, instead assigning 'Uncategorised'
    to it. [\#132](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/132)
  * Improved input validation behaviour for ListCommand and Goal/CategoryCommand [\#136](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/136), [\#143](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/143)
  * Abstracted some parts of the input validation for GoalCommand and CategoryCommand into a separate abstract class ClassificationCommand
  to reduce code reuse [\#143](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/143)

* **Documentation:**
  * User Guide:
    * Created initial UG v1.0 file in Markdown from previously created UG in docx format [\#32](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/32)
    * Updated documentation for features 'list', 'goal' and 'category' [\#84](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/84)
    * Added sample output for features [\#184](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/184)
  * Developer Guide:
    * Added information about 'Command' component of DG [\#55](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/55/)
    * Added implementation details about ListCommand and sequence diagram for ListCommand [\#166](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/166)
    * Updated class diagram for UI [\#166](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/166)

* **Contribution to team-based tasks:**
  * Actively participated in tutorial activities and gave feedback through project development
  * Prepared the release of the JAR files for v1.0 and v2.0
  * Added Javadocs to AddIncomeCommand, AddExpenseCommand, AddTransactionCommand, CategoryCommand, ClassificationCommand, GoalCommand and ListCommand [\#171](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/171)


* **Review Contributions:**
  * PRs Reviewed: [List](https://github.com/AY2324S1-CS2113-W12-3/tp/pulls?q=is%3Apr+reviewed-by%3Ahooami) 
* **Community:**
  * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/141), [2](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/149), [3](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/171), [4](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/157), [5](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/205), [6](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/199), [7](https://github.com/AY2324S1-CS2113-T17-3/tp/issues/164))
