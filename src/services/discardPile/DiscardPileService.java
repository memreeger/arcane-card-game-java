package services.discardPile;

import abst.IDeckDraw;
import abst.IDiscardPileService;
import abst.IHandService;
import model.card.Card;
import model.deck.DiscardPile;
import model.hand.Hand;
import services.deck.manipulate.DeckService;
import services.hand.HandService;

import java.util.List;

public class DiscardPileService implements IDiscardPileService {
    IHandService handService = HandService.getInstance();


    @Override
    public void addCard(DiscardPile discardPile, Hand hand, int cardIndex) {
        discardPile.getDiscardedCards().add(hand.getCards().remove(cardIndex));
    }


    @Override
    public int getDiscardedCardCount(DiscardPile discardPile) {
        return discardPile.getDiscardedCards().size();
    }
}
