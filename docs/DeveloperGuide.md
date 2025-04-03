# FlashCLI Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## Implementation
This section describes some noteworthy details on how certain features are implemented. 

### Flashcard features
### Create a flashcard

This command allows the user to create a new flashcard with compulsory `/q QUESTION` and `/a ANSWER` fields.

The create flashcard mechanism is facilitated by `Deck` and `CommandCreateFlashcard`.

The feature requires a deck to be selected before usage.

**How the feature is implemented:**

Below is the sequence diagram describing the operations for creating the flashcard:

![](images/CreateFlashcardSequenceDiagram.png)

1. The `CommandCreateFlashcard#executeCommand()` method is executed which calls `Deck#createFlashcard()`.
2. A new `Flashcard` object is created when the user uses the command.
3. When `Deck#createFlashcard()` is called by the `CommandCreateFlashcard#executeCommand()` method, it immediately checks if the input arguments (without the command) is valid. 
4. This is achieved with the `Deck#checkQuestionAndAnswer` helper method.
5. The `Deck#checkQuestionAndAnswer` helper method returns the valid strings of question and answer.
6. A new flashcard is then created using the question and answer and added to the current selected deck.

**Handling of edge cases:**
* **Contains all arguments**: Arguments should have both question and answer fields
* **Correct indices**: The index of the start of the question and answer is valid.
* **Correct order**: The question comes before the answer.

If the arguments are invalid, the exception `FlashCLIArgumentException` will be thrown with a custom message which is shown to the user.

### Edit Flashcard Question and Answer Feature

#### Design

This feature enables the user to edit the question and answer to a specific flashcard by supplying its index and updated question and answer. It assumes the user has already selected a deck.

#### Sequence Diagram

![](images/EditSequenceDiagram.png)

#### Implementation

##### `Deck#editFlashcard(int index, String arguments)`

- Replaces the existing flashcard at index with updated question and answer
- Returns a confirmation of the updated flashcard that was edited

##### `CommandEdit#executeCommand()`

- Parses the index
- Validates that it's a valid number and within bounds
- Replaces and updates the existing flashcard and displays the updated flashcard

**Edge Cases Handled:**
- Invalid index format → `NumberFormatException`
- Out-of-bounds index → `ArrayIndexOutOfBoundsException`

### Delete Flashcard Feature

#### Design

The delete flashcard feature allows users to remove a specific flashcard from the currently selected deck based on a 0-based index. The system validates the index and ensures it’s within bounds.

#### Class Diagram

![](images/DeleteClassDiagram.png)

#### Sequence Diagram

![](images/DeleteSequenceDiagram.png)

#### Implementation

##### `Deck#deleteFlashcard(int index)`

- Removes the flashcard at the given index
- Returns a confirmation message with the deleted flashcard's content

##### `CommandDelete#executeCommand()`

- Parses the index from user input
- Validates it as a number and within bounds
- Invokes `deleteFlashcard(...)`
- Displays confirmation or appropriate error messages

**Edge Cases Handled:**
- Invalid input format (e.g., not an integer) → `NumberFormatException`
- Index out of bounds → `ArrayIndexOutOfBoundsException`

### View Flashcard Answer Feature

#### Design

This feature enables the user to view the answer to a specific flashcard by supplying its index. It assumes the user has already selected a deck.

#### Class Diagram

![](images/ViewAnsClassDiagram.png)

#### Sequence Diagram

![](images/ViewAnsSeqDiagram.png)

#### Implementation

##### `Deck#viewFlashcardAnswer(int index)`

- Returns the answer text of the flashcard at the given index

##### `CommandViewAnswer#executeCommand()`

- Parses the index
- Validates that it's a valid number and within bounds
- Retrieves and displays the answer

### Insert Code Snippet

#### Design

This feature enables the user to insert a code snippet to a specific flashcard by supplying its index and code snippet. It assumes the user has already selected a deck.

#### Sequence Diagram

![](images/InsertSequenceDiagram.png)

#### Implementation

##### `Deck#insertCodeSnippet(int index, String arguments)`

- Formats and adds a code snippet into an existing flashcard
- Returns a confirmation of the updated flashcard with the code snippet

##### `CommandInsertCode#executeCommand()`

- Parses the index
- Validates that it's a valid number and within bounds
- Inserts the provided code snippet into the existing flashcard and displays the updated flashcard with code snippet

**Edge Cases Handled:**
- Invalid index format → `NumberFormatException`
- Out-of-bounds index → `ArrayIndexOutOfBoundsException`

### Deck features
### Creating a New Deck

The `new` command is implemented using the `Deck` class, which represents a collection of flashcards, and the `CommandCreateDeck` class, which processes user input to create a new deck. To ensure deck names are unique, a hashmap is used to track existing deck names.

#### Implementation of `DeckManager.createDeck()`
Below shows the sequence diagram of the operations of creating a deck:
![](images/CreateDeckSequenceDiagram.png)

1. The user issues the command to create a new deck.
2. The `DeckManager.createDeck()` method checks whether the deck name already exists in the hashmap.
3. If the name is unique, a new `Deck` object is created and stored in the hashmap, with the name as the key and the `Deck` object as the value.
4. If the name already exists, an error message is shown to the user.

#### Handling Edge Cases
* **Duplicate Deck Name**: If the user attempts to create a deck with a name that already exists, an error message is displayed, and the command is not executed.
* **Empty Deck Name**: Empty deck names are considered invalid.
* **Whitespace-Only Names**: Deck names consisting solely of spaces are considered invalid.

A `FlashCLIArgumentException` will be thrown for each of these cases, with a custom message and the error is displayed to the user.

### Renaming decks

The `rename` command is implemented using the `Deck` class and the `CommandRenameDeck` class. Similar to creating decks, a hashmap is used to track existing deck names. A deck has to be selected before being able to use this command.


#### Implementation of `DeckManager.renameDeck()`
Below shows the sequence diagram for the operations of rename deck:
![](images/RenameDeckSequenceDiagram.png)

1. The user issues the command to rename an existing deck.
2. The `DeckManager.renameDeck()` method checks whether the deck name already exists in the hashmap.
3. If the new name is unique, the `name` attribute of `Deck` object will be updated to the new name. Then, the new name with the renamed `Deck` object will be added to the hashmap as a new entry. 
4. The old entry will then be removed from the hashmap.

#### Handling Edge Cases
* **Unchanged Name**: If the user renames back to the same name as previous, it will not be allowed.
* **Duplicate Deck Name**: The user will not be able to rename the selected deck to deck names that are already created.
* **Empty Deck Name / Whitespace-Only Names**: Empty deck names or names consisting solely of spaces are considered invalid.

A `FlashCLIArgumentException` will be thrown for each of these cases, with a custom message and the error is displayed to the user.

### Listing all decks

The `decks` command is implemented using the `Deck` class and the `CommandViewDecks` class. 

#### Implementation of `DeckManager.viewDecks()`
* Using the `StringBuilder` class from `java.lang`, the method prints the name of each deck in the hashmap, along with a counter index that goes from 1 to n.

#### Handling Edge Cases
* **No Decks**: If there are no decks available, the user will not be able to list them.

A `FlashCLIArgumentException` will be thrown for each of these cases, with a custom message and the error is displayed to the user.

### Selecting a deck

The `select` command is implemented using the `Deck` class and the `CommandSelectDeck` class.

#### Implementation of `DeckManager.selectDeck()`
* Updates `currentDeck` to the selected `Deck` object if deck exists.

#### Handling Edge Cases
* **No Decks**: If there are no decks available, the user will not be able to select any decks.
* * **Deck not found**: If the deck name is not found in the keys of the hashmap, the user will not be able to select the deck.

A `FlashCLIArgumentException` will be thrown for each of these cases, with a custom message and the error is displayed to the user.

### Deleting a deck

The `remove` command is implemented using the `Deck` class and the `CommandDeleteDeck` class.

#### Implementation of `DeckManager.deleteDeck()`
* Removes the selected deck from the hashmap via its key if the deck exists.
* Also deselects the deck if the currentDeck is the deck being deleted. 
* A confirmation message is raised to the user before deletion. This can be found in `Parser`.

#### Handling Edge Cases
* **No Decks**: There are no decks to delete.
* **Deck not found**: The deck does not exists as it is not in the hashmap. 
* **Empty Deck Name / Whitespace-Only Names**: The deck name is empty or consists of only whitespace, which is not a valid deck name.

A `FlashCLIArgumentException` will be thrown for each of these cases, with a custom message and the error is displayed to the user.

### Search Feature

#### Design

The flashcard application supports searching for flashcards either within the currently selected deck or globally across all decks.

The search feature is designed with the following constraints:

- Users may search by question (`/q`) and/or answer (`/a`)
- If no deck is selected, the search is performed globally across all decks
- If a deck is selected, only that deck is searched
- The system is case-insensitive and supports partial matches

##### Class Diagram

The following PlantUML diagram shows the key classes involved in the search operation:

![](images/SearchClassDiagram.png)

##### Sequence Diagram

Below is a simplified sequence of how a search request is handled:

![](images/SearchSequenceDiagram.png)

#### Implementation

##### `Deck#searchFlashcardQuestion(String arguments)`

This method parses the search arguments for `/q` and `/a` prefixes and returns flashcards that match either (or both) the question or answer. It supports edge cases such as:

- Only `/q` or only `/a` provided
- Input in any order (`/a first /qsecond` works too)
- Case insensitivity

The method throws a `FlashCLIArgumentException` if neither `/q` nor `/a` is present.

```java
String queryQuestion = ...; // parsed from arguments
String queryAnswer = ...;   // parsed from arguments
for (Flashcard f : flashcards) {
  boolean matches = ...;
  if (matches) matched.add(f);
}
return matched;
```

##### `DeckManager#globalSearch(String arguments)`

This method iterates through all decks in `DeckManager.decks`, calls `deck.searchFlashcardQuestion()`, and returns a formatted string of results including the deck name, question, and answer.

**Edge cases handled:**
- No decks exist → `EmptyListException`
- Malformed arguments → `FlashCLIArgumentException`
- No matches → returns `"No matching flashcards found in any deck."`

##### `CommandSearchFlashcard`

This command bridges user input with search logic:

- If `DeckManager.currentDeck == null`, invokes `DeckManager.globalSearch()`
- Otherwise, calls `Deck.searchFlashcardQuestion()`
- Sends output to `Ui.showToUser(...)`

#### Considerations & Limitations

- Currently does not support regex or fuzzy matching
- Could be extended to highlight matched terms or paginate long results
- Error handling is gracefully propagated to the UI layer

### Save/Load Functionality

#### Design

The Save/Load functionality ensures persistence of flashcard data between sessions. When the program exits, it serializes all in-memory `Deck` objects into individual `.txt` files in the `./data/decks` directory. Upon startup, it reconstructs the decks by reading and parsing these `.txt` files.

This design allows easy access, portability, and simple debugging via text files, avoiding binary or JSON formats for simplicity.

#### Class Diagram

![](images/SaveClassDiagram.png)

#### Sequence Diagram

![](images/SaveSequenceDiagram.png)

#### Implementation

##### `Saving.saveAllDecks(Map<String, Deck>)`

- Creates `./data/decks/` directory if not present  
- Removes any `.txt` files that no longer correspond to in-memory decks  
- Iterates over each deck and writes its flashcards in:

```
Q: question text
A: answer text
```

Each flashcard is separated by a blank line for clarity.

**Edge cases handled:**
- Missing directory → automatically created
- Deleted decks → corresponding `.txt` files removed

##### `Loading.loadAllDecks()`

- Reads all `.txt` files in `./data/decks`
- Infers deck name from filename (e.g. `bio.txt` → deck name = `bio`)
- Parses `Q:` and `A:` pairs into `Flashcard` objects
- Adds each loaded `Deck` to a `LinkedHashMap`

**Edge cases handled:**
- Non-existent or empty folder → returns empty deck map
- Files with missing/partial questions/answers → skipped or handled gracefully

##### `FlashCLI.main()` Integration

```java
DeckManager.decks = Loading.loadAllDecks();
...
Saving.saveAllDecks(DeckManager.decks);
```

- Loads decks at startup  
- Saves them at exit, after user types the `exit` command  

#### Considerations

- File format is human-readable and editable
- Current implementation assumes well-formed files
- Future improvements: introduce backup/restore, encryption, or support for import/export formats like JSON/CSV

## Product scope
### Target user profile

The user should:
- be a CS2113 student
- prefer desktop apps over other types
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

### Value proposition

This app provides a no-frills solution to helping CS2113 students study and 
practice using terminal commands while memorising key information required for the course.

## User Stories

| Version | As a ... | I want to ...                                                    | So that I can ...                                      |
|---------|----------|------------------------------------------------------------------|--------------------------------------------------------|
| v1.0    | student  | create flashcards with CS2113 information on them                | memorise information in digestible quantities          |
| v1.0    | student  | view all my flashcards created                                   | see how many of them are created                       |
| v1.0    | student  | view the questions without answers                               | test my understanding of a specific question           |
| v1.0    | student  | delete flashcards                                                | remove outdated information                            |
| v1.0    | student  | edit my flashcards                                               | make updates to flashcards when necessary              |
| v1.0    | student  | show the answer after answering the questions                    | check my answers                                       |
| v2.0    | student  | mark each flashcard according to how well I remember the content | review concepts I get wrong often                      |
| v2.0    | student  | search for specific flashcards by keywords                       | revise certain questions I have trouble with           |
| v2.0    | student  | see what functions the flashcard app has                         | know how to use the commands effectively               |
| v2.0    | student  | test all cards in a deck                                         | revise the concepts related to the deck's topic        |
| v2.0    | student  | shuffle the deck                                                 | prevent memorizing answers based on order              |
| v2.0    | student  | view flashcards that I got wrong after testing                   | identify my mistakes and improve my understanding      |
| v2.0    | student  | add code snippets into flashcards                                | properly format code in questions to aid understanding |
| v2.0    | student  | organise flashcards into different decks                         | study them by topic                                    |
| v2.0    | student  | rename decks                                                     | make updates to the deck's topic                       |
| v2.0    | student  | view all decks                                                   | easily navigate and manage my flashcards               |
| v2.0    | student  | search for specific flashcards                                   | find the flashcard I want quickly                      |
| v2.0    | student  | access my flashcards and decks across sessions                   | continue my revision without losing progress           |
| v2.0    | student  | see a nice UI                                                    | have a comfortable viewing experience                  |

## Non-Functional Requirements
1. Should be compatible on any mainstream OS as long as it has Java 17 or above installed. 
2. The system should respond to user input within 5 seconds for most commands under typical usage.
3. Should be intuitive for most users familiar with a command line user interface .
4. End-users should be able to set up and run the flashcard quizzes within 3 steps (create deck, add flashcard, quiz).
5. The system has automated logging after the end of every session, and be able to store up to a casual amount of usage.

## Glossary

* *Mainstream OS* - Windows, Linux, Unix, macOS
* *Flashcard* - An Object with parameters *index*, *question*, *answer*, *codeSnippet*, *isLearned*
* *Deck* - An Object that holds array of Flashcards, as well as other parameters.

* *CLI* - Command Line Interface

* *EmptyListException* - thrown if there is an empty list present
* *FlashCLIArgumentException* - thrown if an invalid input is inputted
* *QuizCancelledException* - thrown if the quiz is cancelled halfway

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
