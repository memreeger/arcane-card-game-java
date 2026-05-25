package abst;

import enums.DeckType;

import enums.DifficultyType;
import model.card.Card;
import model.hand.Hand;

import java.util.List;

public interface IGameService {

    void startGame(DeckType deckType, DifficultyType difficulty);

    void showCurrentHand();

    void discardCardAndDrawNewCard(int cardIndex);

    void showDiscardedCards();

    void submitHand();

    boolean isGameOver();

    boolean isPlayerWon();

    List<Card> getDiscardedCards();

    Hand getCurrentHand();

    int getDiscardedCardCount();

    int getCurrentRoundNumber();

    int getCurrentTargetScore();

    int getTotalScore();

    int getRemainingDeckCardCount();
}