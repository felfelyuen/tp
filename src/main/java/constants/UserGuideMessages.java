package constants;

import static constants.CommandConstants.CREATE;
import static constants.CommandConstants.DELETE;
import static constants.CommandConstants.EDIT;
import static constants.CommandConstants.EXIT;
import static constants.CommandConstants.INSERT_CODE;
import static constants.CommandConstants.LIST;
import static constants.CommandConstants.MARK_LEARNED;
import static constants.CommandConstants.MARK_UNLEARNED;
import static constants.CommandConstants.NEW_DECK;
import static constants.CommandConstants.QUIZ;
import static constants.CommandConstants.RENAME_DECK;
import static constants.CommandConstants.SWITCH_DECK;
import static constants.CommandConstants.VIEW_ANS;
import static constants.CommandConstants.VIEW_DECKS;
import static constants.CommandConstants.VIEW_QN;
import static constants.CommandConstants.VIEW_RESULTS;

/**
 * class to store the user guide information
 */
public class UserGuideMessages {
    public static final String USER_GUIDE_INFORMATION =
            "\"" + CREATE + "\": creates a flashcard\n" +
                    "\"" + LIST + "\": shows list of flashcards\n" +
                    "\"" + VIEW_QN + "\": views the flashcard's question\n" +
                    "\"" + VIEW_ANS + "\": views the flashcard's answer\n" +
                    "\"" + DELETE + "\": deletes the flashcard\n" +
                    "\"" + EDIT + "\": edits the flashcard question/answer\n" +
                    "\"" + INSERT_CODE + "\": inserts code snippet for a flashcard\n" +
                    "\"" + MARK_LEARNED + "\": marks flashcard as learned\n" +
                    "\"" + MARK_UNLEARNED + "\": marks flashcard as unlearned\n" +
                    "\"" + QUIZ + "\": quizzes the unlearned flashcards\n" +
                    "\"" + VIEW_RESULTS + "\": views results from quiz after quiz is completed\n" +
                    "\"" + NEW_DECK + "\": creates a new deck of flashcards\n" +
                    "\"" + RENAME_DECK + "\": renames flashcard deck\n" +
                    "\"" + VIEW_DECKS + "\": shows list of decks\n" +
                    "\"" + SWITCH_DECK + "\": selects deck of flashcards\n" +
                    "\"" + EXIT + "\": exits the program\n" +
                    "Go to the flashCli User Guide website for more details";

}
