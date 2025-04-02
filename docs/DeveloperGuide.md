# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

### Design & Implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Search Feature

#### Design

The flashcard application supports searching for flashcards either within the currently selected deck or globally across all decks.

The search feature is designed with the following constraints:

- Users may search by question (`q/`) and/or answer (`a/`)
- If no deck is selected, the search is performed globally across all decks
- If a deck is selected, only that deck is searched
- The system is case-insensitive and supports partial matches

##### Class Diagram

The following PlantUML diagram shows the key classes involved in the search operation:

```plantuml
@startuml
class CommandSearchFlashcard {
- String arguments
+ executeCommand()
  }

class DeckManager {
+ static Deck currentDeck
+ static Map<String, Deck> decks
+ static String globalSearch(String): String
  }

class Deck {
+ List<Flashcard> flashcards
+ List<Flashcard> searchFlashcardQuestion(String): List<Flashcard>
  }

class Flashcard {
+ String question
+ String answer
  }

CommandSearchFlashcard --> DeckManager
DeckManager --> Deck
Deck --> Flashcard
@enduml
```

##### Sequence Diagram

Below is a simplified sequence of how a search request is handled:

```plantuml
@startuml
actor User
User -> CommandSearchFlashcard : executeCommand()
alt Deck selected
    CommandSearchFlashcard -> Deck : searchFlashcardQuestion(arguments)
    Deck -> Deck : filter Flashcards
    Deck -> CommandSearchFlashcard : result
else No deck selected
    CommandSearchFlashcard -> DeckManager : globalSearch(arguments)
    DeckManager -> Deck : searchFlashcardQuestion(arguments) [loop over decks]
    DeckManager -> CommandSearchFlashcard : result
end
CommandSearchFlashcard -> Ui : showToUser(result)
@enduml
```

#### Implementation

##### `Deck#searchFlashcardQuestion(String arguments)`

This method parses the search arguments for `/q` and `/a` prefixes and returns flashcards that match either (or both) the question or answer. It supports edge cases such as:

- Only `/q` or only `/a` provided
- Input in any order (`a/first q/second` works too)
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

```plantuml
@startuml
class Saving {
  + saveAllDecks(Map<String, Deck>)
}

class Loading {
  + loadAllDecks(): Map<String, Deck>
}

class DeckManager {
  + static Map<String, Deck> decks
}

Saving --> DeckManager
Loading --> DeckManager
@enduml
```

#### Sequence Diagram

```plantuml
@startuml
actor User
User -> FlashCLI : main()
FlashCLI -> Loading : loadAllDecks()
Loading -> FileSystem : read .txt files
Loading -> Deck : create flashcards
FlashCLI -> User : run session
User -> FlashCLI : exit command
FlashCLI -> Saving : saveAllDecks(decks)
Saving -> FileSystem : write .txt files (overwrite + delete removed)
@enduml
```

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

### Delete Flashcard Feature

#### Design

The delete flashcard feature allows users to remove a specific flashcard from the currently selected deck based on a 0-based index. The system validates the index and ensures it’s within bounds.

#### Class Diagram

```plantuml
@startuml
class CommandDelete {
  - String arguments
  + executeCommand()
}

class Deck {
  + String deleteFlashcard(int): String
}

CommandDelete --> Deck
@enduml
```

#### Sequence Diagram

```plantuml
@startuml
actor User
User -> CommandDelete : executeCommand()
CommandDelete -> Deck : deleteFlashcard(index)
Deck -> CommandDelete : confirmation message
CommandDelete -> Ui : showToUser(message)
@enduml
```

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

## Quiz method

### Design

### Implementation

## Mark learned/ Mark unlearned method

### Design

### Implementation
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

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary 

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
