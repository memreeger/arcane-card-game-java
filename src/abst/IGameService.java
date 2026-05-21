package abst;

import enums.DeckType;
import model.card.Card;
import model.hand.Hand;

import java.util.List;

public interface IGameService {

    void startGame(DeckType deckType);

    Hand getCurrentHand();

    void showCurrentHand();

    void discardCardAndDrawNewCard(int cardIndex);

    int getRemainingDeckCardCount();

    int getDiscardedCardCount();

    List<Card> getDiscardedCards();

    void showDiscardedCards();
}