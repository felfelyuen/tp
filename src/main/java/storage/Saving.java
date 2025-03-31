package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import deck.Deck;
import deck.Flashcard;

public class Saving {
    /**
     * Saves the list to path ./data/Jerry.txt.
     * Creates this path if it does not exist
     * @param decks decks to be saved
     * @throws IOException if there is issues saving the file
     */
    public static void saveAllDecks(LinkedHashMap<String, Deck> decks) throws IOException {
        File dir = new File("./data/decks");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (String deckName : decks.keySet()) {
            Deck deck = decks.get(deckName);
            File file = new File(dir, deckName.toLowerCase() + ".txt");
            FileWriter fw = new FileWriter(file);

            for (Flashcard flashcard : deck.getFlashcards()) {
                fw.write("Q: " + flashcard.getQuestion() + "\n");
                fw.write("A: " + flashcard.getAnswer() + "\n");
                fw.write("\n"); // Separate flashcards with a blank line
            }

            fw.close();
        }
    }
}
