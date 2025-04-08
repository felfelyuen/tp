package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import deck.Deck;
import deck.Flashcard;

/**
 * Class to handle the saving of all decks created / deleted inside the program.
 * Each deck will be saved to its own .txt in a specific format
 */
public class Saving {
    /**
     * Saves the list to path ./data/decks/
     * Each deck is its own .txt file
     * Creates this path if it does not exist
     * @param decks decks to be saved
     * @throws IOException if there is issues saving the file
     */
    public static void saveAllDecks(LinkedHashMap<String, Deck> decks) throws IOException {
        File dir = new File("./data/decks");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File[] existingFiles = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (existingFiles != null) {
            for (File file : existingFiles) {
                String deckName = file.getName().replace(".txt", "");
                if (!decks.containsKey(deckName)) {
                    file.delete();
                }
            }
        }

        for (String deckName : decks.keySet()) {
            Deck deck = decks.get(deckName);
            File file = new File(dir, deckName + ".txt");
            FileWriter fw = new FileWriter(file);

            for (Flashcard flashcard : deck.getFlashcards()) {
                fw.write(flashcard.getIsLearnedAsString() + "\n");
                fw.write("Q: " + flashcard.getQuestion() + "\n");
                fw.write("A: " + flashcard.getAnswer() + "\n");
                fw.write("\n"); // Separate flashcards with a blank line
            }

            fw.close();
        }
    }

    public static void saveDeck(String deckName, Deck deck) throws IOException {
        File dir = new File("./data/decks");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, deckName + ".txt");

        try (FileWriter fw = new FileWriter(file)) {
            for (Flashcard flashcard : deck.getFlashcards()) {
                fw.write(flashcard.getIsLearnedAsString() + "\n");
                fw.write("Q: " + flashcard.getQuestion() + "\n");
                fw.write("A: " + flashcard.getAnswer() + "\n");
                fw.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving deck: " + deckName);
        }
    }

    public static void deleteDeckFile(String deckName) throws IOException {
        File file = new File("./data/decks/" + deckName + ".txt");
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("Failed to delete deck file: " + deckName);
            }
        }
    }

    public static void renameDeckFile(String oldName, String newName) throws IOException {
        File oldFile = new File("./data/decks/" + oldName + ".txt");
        File newFile = new File("./data/decks/" + newName + ".txt");

        if (oldFile.exists()) {
            boolean success = oldFile.renameTo(newFile);
            if (!success) {
                System.out.println("Failed to rename deck file from " + oldName + " to " + newName);
            }
        } else {
            System.out.println("Deck file to rename not found: " + oldName);
        }
    }
}
