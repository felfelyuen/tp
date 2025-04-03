# User Guide

## Introduction

FlashCLI 2.0 is a command-line flashcard application designed specifically for CS2113 Software Engineering students. It helps students create, manage, and review flashcards efficiently, making it easier to retain important concepts in Java, software design, and coding best practices.

--------------------------------------------------------------------------------------------------------------------
## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2113-F11-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for FlashCLI.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar FlashCLI.jar` command to run the application.<br>
   You should be greeted with a welcome message.

5. Type the command in the command box and press Enter to execute it. e.g. typing **`user_guide`** and pressing Enter will list the available commands.<br>
   Some example commands you can try:

    * `new testDeck` : Creates a new deck named "testDeck".

    * `select testDeck` : Selects the deck "testDeck".

    * `add /q What is an assertion? /a Assertions are used to...` : Creates a new flashcard with the question and answer provided.

    * `list`: Shows the list of flashcards in the selected deck.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------
## Features

### Viewing user guide: `user_guide`
Displays possible instructions that can be inputted.

Format: `user_guide`
Example: 
```
[INPUT]: user_guide
[OUTPUT]:
Quick Start:
Create a deck of flashcards with "new", select it with "select", and begin adding flashcards with "add"!

List of commands:
"add": creates a flashcard
"list": shows list of flashcards
"qn": views the flashcard's question
"view_ans": views the flashcard's answer
"delete": deletes the flashcard
"edit": edits the flashcard question/answer
"insert_code": inserts code snippet for a flashcard
"mark_learned": marks flashcard as learned
"mark_unlearned": marks flashcard as unlearned
"quiz": quizzes the unlearned flashcards
"view_results": views results from quiz after quiz is completed
"new": creates a new deck of flashcards
"rename": renames flashcard deck
"decks": shows list of decks
"select": selects deck of flashcards
"exit": exits the program

Go to the flashCli User Guide website for more details
```
### Create flashcards: `add`
Creates a flashcard which consists of the fields questions and answers and adds it to the deck. All fields are required.

Format: `add q/QUESTION a/ANSWER`
Examples:
* `add q/What language is used in CS2113? a/Java` Adds a new flashcard with the question `What language is used in CS2113?` and answer `Java` to the current selected deck. 

If no deck is selected, this command cannot be used.

### Summary view of the flashcard deck: `list`
Shows a list of all the flashcards in your deck, including information about the index and question of each flashcard. Limit to only 50 flashcards per page.

Format: `list`
Examples:
```
[INPUT]: list
[OUTPUT]: 
1. q/What colour is an apple? 
2. q/What language is used in CS2113?
```

### View question only: `qn`
Show the question of a flashcard via its index.

Format: `qn INDEX`
INDEX must be a positive integer, ie: 1, 2, 3
Examples:
```
[INPUT]: qn 3
[OUTPUT]: What is a java interface?

interface Interface{
void method();
}
```

### Quiz mode with timer: `quiz`
Enters quiz mode. The unlearned flashcards in the deck would be shuffled and the user would have to enter the correct answer for each flashcard. If the flashcard is answered correctly, it would be marked as learned, and the user would not see the flashcard again if they enter quiz mode afterwards. They can re-add the question by marking the flashcard as unlearned. After the quiz, they can type `view_results` to view their results and re-quiz the unlearned flashcards.

The user can type `exit_quiz` to exit the test mode, but the progress would not be saved.

Format: `quiz`
Examples: 
```
[INPUT]: quiz
[OUTPUT]: Entering quiz mode... get ready!
[OUTPUT]: You have 2 questions left:
[OUTPUT]: What language is used in CS2113?
[INPUT]: Java.
[OUTPUT]: Correct!
[OUTPUT]: You have 1 question left:
[OUTPUT]: What colour is an apple?
[INPUT]: Blue.
[OUTPUT]: Incorrect.
[OUTPUT]: You finished the test! You took: 22 seconds!
Type view_results to check your test result
```

### Mark as learned: `mark_learned`
Marks the question as learned. Flashcards that are correctly answered in quiz mode would be automatically marked as learned. Learned flashcards are not tested again in quiz mode.

Format: `mark_learned INDEX`
INDEX must be a positive integer, i.e: 1, 2, 3

Examples:
```
[INPUT]:mark_learned 2
[OUTPUT]: Changed flashcard number 2 into learned
```

### Unmark learned: `mark_unlearned`
Marks the flashcard as unlearned. Unlearned flashcards are tested in quiz mode.

Format: `mark_unlearned INDEX`
INDEX must be a positive integer, i.e: 1, 2, 3

Examples:
```
[INPUT]:mark_unlearned 2
[OUTPUT]: Changed flashcard number 2 into unlearned
```


--------------------------------------------------------------------------------------------------------------------
## FAQ

**Q**: I have created many flashcards, will my data be saved?

**A**: Yes, your data will be saved to a txt file under `./data/decks/`.

**Q**: How do I transfer my data to another computer? 

**A**: It's easy, simply install FlashCLI using the instructions given in [Quick Start](#Quick-Start). Then, overwrite the txt file located in `./data/decks/` on the new computer.

--------------------------------------------------------------------------------------------------------------------

## Known Issues

--------------------------------------------------------------------------------------------------------------------
## Command Summary

| Action               | Format, Examples                                                                                |
|----------------------|-------------------------------------------------------------------------------------------------|
| **View User Guide**  | `user_guide`                                                                                    |
| **Create Flashcard** | `add q/QUESTION a/ANSWER` e.g. `add q/What language is used in CS2113? a/Java`                  |
| **List Flashcards**  | `list`                                                                                          |
| **View Question**    | `qn INDEX` e.g. `qn 3`                                                                          |
| **View Answer**      | `ans INDEX` e.g. `ans 3`                                                                        |
| **Delete Flashcard** | `delete INDEX` e.g. `delete 3`                                                                  |
| **Edit Flashcard**   | `edit INDEX [q/QUESTION] [a/ANSWER]` e.g.  `edit 2 q/What is substitutability? a/A method`      |
| **Search Flashcard** | `search SEARCHTERM` e.g.  `search apple`                                                        |
| **New Deck**         | `new DECKNAME` e.g.  `new testDeck`                                                             |
| **Rename Deck**      | `rename NEWNAME` e.g. `rename testDeck2`                                                        |
| **Select Deck**      | `select DECKNAME` e.g. `select testDeck`                                                        |
| **Quiz Mode**        | `quiz`                                                                                          |
| **View Results**     | `view_results` , `redo`                                                                         |
| **Code Snippet**     | `insert_code INDEX c/CODE_SNIPPET` e.g. `insert_code 3 c/interface Interface{ void method(); }` |
| **Mark Learned**     | `mark_learned INDEX` e.g. `mark_learned 2`                                                      |
| **Mark Unlearned**   | `mark_unlearned INDEX` e.g. `mark_unlearned 2`                                                  |
| **Exit**             | `exit`                                                                                          |
|

