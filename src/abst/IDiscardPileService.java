package abst;

import model.card.Card;
import model.deck.DiscardPile;
import model.hand.Hand;

import java.util.List;

public interface IDiscardPileService {
    void addCard(DiscardPile discardPile, Hand hand, int cardIndex);

    int getDiscardedCardCount(DiscardPile discardPile);

}
