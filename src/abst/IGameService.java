package abst;

import enums.DeckType;
import enums.Difficulty;
import model.card.Card;
import model.hand.Hand;

import java.util.List;

public interface IGameService {

    void startGame(DeckType deckType, Difficulty difficulty);

    Hand getCurrentHand();

    void showCurrentHand();

    void discardCardAndDrawNewCard(int cardIndex);

    int getRemainingDeckCardCount();

    int getDiscardedCardCount();

    List<Card> getDiscardedCards();

    void showDiscardedCards();

    void submitHand();

    int getCurrentRoundNumber();

    int getCurrentTargetScore();

    int getTotalScore();

    boolean isGameOver();

    boolean isPlayerWon();
}