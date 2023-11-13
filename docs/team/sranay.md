# Jason Song's Project Portfolio Page

## Project: FinText

FinText is a **Command Line Interface (CLI)-based personal finance tracker to make it easy for users to track and manage
their saving/spending,** and view a summary of their daily/weekly/monthly transactions. Application Data will be stored into a folder called `data`.

Given below are my contributions to the project.

- New Feature: Added a storage function so that the user can load back their current state of the application after reopening it.
    - What it does: Allows the user to be able to save the current state of the application and load back after reopening the application.
    - Justification: This allows the user to not key in all the transactions details everytime they reopen the application.
    - Highlights: Every new data added in any of the object will need to update the storage's logic flow.
    - Credits: Uses [OpenCSV](https://mvnrepository.com/artifact/com.opencsv/opencsv) to store the storage files as CSV.
- New Feature: Added a help command that allows the user to see what are the available commands that can be used.
    - What it does: Allows the user to see what are the commands available that can be used.
    - Justification: This allows the user to know what are the available commands there are.
    - Highlights: Every new command or flags added will require it to be updated.
- New Feature: Added a export command that allows the user to export all the transactions stored into a CSV File.
    - What it does: Allows the user to export all the transaction data into a CSV File.
    - Justification: This allows the user to see the summary of all the transactions that is being recorded.
    - Highlights: Every new data added in Transaction object will need to update the command's logic flow.
    - Credits: Uses [OpenCSV](https://mvnrepository.com/artifact/com.opencsv/opencsv) to store the storage files as CSV.
- Code contributed: [RepoSense Link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=sRanay&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=sRanay&tabRepo=AY2324S1-CS2113-W12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=other~docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- Enhancements to existing features:
    - Help command for specific commands (Pull request [#11](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/11))
    - Export command to export only "in" or "out" transactions (Pull request [#63](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/63))
- Contributions to the UG:
    - Updated documentation for features `export`: [#69](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/69)
- Contributions to the DG:
    - Added implmentation details of Storage component and its Class Diagram [#78](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/78)
- Contributions to team-based tasks:
    - Updated the user stories for DG: [#41](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/41)
- Review/mentoring contributions:
    - PRs reviewed: [Pull Request](https://github.com/AY2324S1-CS2113-W12-3/tp/pulls?q=is%3Apr+reviewed-by%3AsRanay)
    - PRs reviewed with comments to fix potential issue with commit: [#85](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/85), [#82](https://github.com/AY2324S1-CS2113-W12-3/tp/pull/82) 
- Contributions beyond the project teams:
    - DG Review: CS2113-T18-3 ([Pull request](https://github.com/nus-cs2113-AY2324S1/tp/pull/22))