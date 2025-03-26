package deck;

import java.util.ArrayList;

import ui.Ui;

public class DeckManager {
    public static Deck currentDeck = new Deck("test");
    public static ArrayList<Deck> decks = new ArrayList<>();

    public static int getDeckSize() {
        return decks.size();
    }

    public static String createDeck(String arguments) {
        String deckName = parseArguments(arguments);
        decks.add(new Deck(deckName));
        return String.format("Deck \"%s\" created, no of decks %d", deckName, getDeckSize());
    }

    private static String parseArguments(String arguments) {
        return arguments;
    }
}
