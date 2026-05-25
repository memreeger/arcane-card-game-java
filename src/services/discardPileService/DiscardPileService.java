package services.discardPileService;

import abst.IDiscardPileService;
import model.card.Card;
import model.deck.DiscardPile;

import java.util.List;


public class DiscardPileService implements IDiscardPileService {
    private static DiscardPileService instance;

    private final DiscardPile discardPile = new DiscardPile();


    private DiscardPileService() {
    }

    public static DiscardPileService getInstance() {

        if (instance == null) {
            instance = new DiscardPileService();
        }

        return instance;
    }
    //IHandService handService = HandService.getInstance();


    /*
    @Override
    public void addCard(Hand hand, int cardIndex) {
        //List<Card> discardDeck = discardPile.getDiscardedCards();
        //discardDeck.add()
        //discardPile.getDiscardedCards().add(hand.getCards().remove(cardIndex));

        //discardPile.getDiscardedCards().add(card);
        ;
    }


     */
    public void addCardByCard(Card card) {
        discardPile.getDiscardedCards().add(card);
    }


    @Override
    public int getDiscardedCardCount() {
        return discardPile.getDiscardedCards().size();
    }


    @Override
    public void showDiscardedCards() {
        int discardListSize = discardPile.getDiscardedCards().size();
        for (int i = 0; i < discardListSize; i++) {
            System.out.println((i + 1) + ") " + discardPile.getDiscardedCards().get(i));
        }
    }

    @Override
    public List<Card> getDiscardedCards() {
        return discardPile.getDiscardedCards();
    }

    @Override
    public void clearDiscardPile() {
        discardPile.getDiscardedCards().clear();
    }
}
