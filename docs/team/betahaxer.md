# Poh Yu Wen - Project Portfolio Page

## Project: FlashCLI
## Overview
FlashCLI is a command-line flashcard application designed specifically for CS2113 Software Engineering students. It helps students create, manage, and review flashcards efficiently, making it easier to retain important concepts in Java, software design, and coding best practices.

--- 
## Summary of Contributions 

**Code contributed:** [RepoSense link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Betahaxer&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

### Features Implemented

#### **Critical Feature**: Creating Flashcards

- **What it does:** Allow users to create flashcards with mandatory question and answer fields using `/q` and `/a` tags where the user input is also sanitised.
- **Justification:** This is the **core functionality** of the application—without it, users cannot interact with the app meaningfully.

#### **Critical Feature**: Creating Decks

- **What it does:** Allow users to create a named flashcard deck, given that there are no duplicated decks.
- **Justification:** Decks organize flashcards, making this crucial for user workflow.

#### New Feature: Renaming Decks

- **What it does:** Allow users to rename existing decks when a deck is selected, while also preserving deck order and flashcards.
- **Justification:** Allows decks to be updated as study needs evolve or when mistakes are made in naming decks

#### New Feature: Listing Decks

- **What it does:** Allow users to view all the decks they have created, along with the deck indices.
- **Justification:** Facilitates the user experience by allowing users to view the decks they have created. Also facilitates selection of decks by index.

#### New Feature: Selecting Decks

- **What it does:** Allow users select a deck by its index.
- **Justification:** Significantly improves user experience as deck names may be long and difficult to type.

#### New Feature: Deleting Decks

- **What it does:** Allow users remove a deck by its index.
- **Justification:** Improves user experience as it prevents outdated decks from accumulating

#### New Feature: Unselecting Decks

- **What it does:** Allow users to unselect the current deck selected.
- **Justification:** Helps to improve testability of other commands, especially those involving flashcards as they cannot be used without a deck.

Due to page limitations, I am unable to elaborate further on the challenges faced etc. Do refer to the [Developer's Guide](https://ay2425s2-cs2113-f11-4.github.io/tp/DeveloperGuide.html) for that.

### Other aspects
#### **Project Management / Contribution to Team-Based Tasks:**
1. Setting up of project structure, including `Command`, `Parser` etc. and test structure, which was critical in moving the team in the same direction
2. Releasing `v1.0` and assisting with releasing `v2.0` and `v2.1` by helping with file conversions of documentation to PDFs
3. Updating issue tracker by closing issues already completed based on teammates' PR
4. Triaging **all** 51 bugs transferred from the PE Dry Run by assigning members, tagging priority and bug type
5. Reviewing over 22 of the team's PRs

#### **Documentation:**
  - User Guide: 
    - Set up the User Guide for the team, including Quick Start, FAQ, Command Summary
    - Updated the sections corresponding to the features I implemented, eg creating a flashcard/deck
  - Developer's Guide:
    - Updated overall structure and heading, eg heading numbering
    - Updated Appendix B: User Stories
    - Updated the sections corresponding to the features I implemented, complete with sequence diagrams, rationale etc.

#### Mentoring/Team Contributions
-  Coordinated meetings and pushed the team to meet deadlines **throughout the project**, e.g. setting internal deadlines, checking up on team progress
- Setting up branch protection to prevent unauthorised pushing to master branch (it actually happened) and requiring Gradle tests to pass and at least 1 review to merge
- Helping teammates debug issues with their IDE and Java version, so that they can work on the project

#### Contributions Beyond the Project Team
- Reported 10 bugs in other teams’ tP.
- Gave structured and constructive feedback on other teams' DG.


