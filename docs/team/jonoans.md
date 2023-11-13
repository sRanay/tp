---
title: Jonathan's Project Portfolio Page
---

# Jonathan's Project Portfolio Page

### Project: FinText

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their saving/spending,** and view a summary of their daily/weekly/monthly transactions.

Given below are my contributions to the project.

* **New Feature**: Added command to add expenses
    * What it does: Allows a user to add an expense entry to the program
    * Justification: This allows the user to track his expenses as part of the set of product features set forth in the user stories.
    * Highlights: Made a StateManager object which enforces singleton design pattern. This StateManager object is used to track program state and can be used by other developers.
                  Ensures only a single source of truth exists in the program.
* **New Feature**: Added table printing utilities in Ui class
    * What it does: Allows other developers to use the same common functions to print output in a tabular format
    * Justification: A lot of our output (transactions details etc.) are best displayed in a tabular format. Thus, a common function for use to print outputs in tabular format would be ideal.
    * Highlights: The table printer is made to have adjustable parameters. These parameters include the column widths. The function automatically truncates values if the column content exceeds column width.
* **New Feature**: Implemented the underlying code for recurring transactions
    * What it does: Allows a user to add a recurring transactions. New recurring entries will be created automatically when required.
    * Justification: This allows the users to add recurring transactions and have the program automatically create future entries, saving the user the need to manually create entries.
    * Highlights: Tried out using streams to filter transaction entries to find candidate transactions that need to have their recurring entries added.

* **Code contributed**: [RepoSense link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=jonoans&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=Jonoans&tabRepo=AY2324S1-CS2113-W12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
    * Fixed test cases for list command ([\#38](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/38))
    * Fixed failing tests due to inter-test dependencies ([\#59](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/59))
        * Tests were indirectly dependent on one another because state program state was not properly cleared after tests ran for different components.
    * Fixed edge case for edit command ([\#177](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/177))

* **Documentation**:
    * User Guide:
        * Updated documentation for the features `in` and `out` to include information about `recurrence` option ([\#70](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/70), [\#131](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/131))
    * Developer Guide:
        * Added information about `UI` class of program ([\#46](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/46), [\#79](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/79), [\#92](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/92)).
        * Added section for `StateManager` and `in` and `out` commands ([\#154](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/154), [\#164](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/164))
        * Added non-functional requirements ([\#154](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/154))
        * Added sequence diagrams for DG ([\#173](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/173))

* **Contribution to team-based tasks**:
    * Participate in tutorial activities, working with teammates to complete tutorial tasks.
    * Participated in team discussions about product and gave feedback about proposed features and implementation.

* **Community**:
    * PR reviewed (with non-trivial review comments): [\#78](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/78), [\#85](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/85), [\#143](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/143), [\#166](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/166)
    * All PRs reviewed: [PRs Reviewed](https://github.com/AY2324S1-CS2113-W12-3/tp/pulls?q=is%3Apr+reviewed-by%3AJonoans)
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/86), [2](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/92), [3](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/116), [4](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/122), [5](https://github.com/AY2324S1-CS2113-T18-4/tp/issues/97))