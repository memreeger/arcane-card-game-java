package services.handService;

import abst.IDeckDraw;
import abst.IDiscardPileService;
import abst.IHandService;
import model.card.Card;
import model.deck.Deck;
import model.hand.Hand;
import services.deckService.manipulate.DeckService;
import services.discardPileService.DiscardPileService;

import java.util.List;

public class HandService implements IHandService {
    private static HandService instance;

    private final IDeckDraw deckDraw = DeckService.getInstance();
    private final IDiscardPileService discardPileService = DiscardPileService.getInstance();


    private HandService() {
    }

    public static HandService getInstance() {

        if (instance == null) {
            instance = new HandService();
        }

        return instance;
    }


    @Override
    public Hand createHand(Deck deck) {
        List<Card> cards = deckDraw.drawCards(deck, 4);
        return new Hand(cards);
    }

    @Override
    public void addCard(Hand hand, Deck deck) {
        hand.getCards().add(deckDraw.drawCard(deck));

    }

    @Override
    public void addCards(Hand hand, Deck deck, int count) {
        List<Card> cards = deckDraw.drawCards(deck, count);
        for (Card c : cards) {
            hand.getCards().add(c);
        }

    }

    @Override
    public Card removeCard(Hand hand, int cardIndex) {
        //Elden attığını elden çıkart
        //DiscardPile'a ekleme yap
        if (cardIndex < 0 || cardIndex >= hand.getCards().size()) {
            throw new IllegalArgumentException("Invalid card index.");
        }
        //Card removedCard = hand.getCards().get(cardIndex);
        //discardPileService.addCardByCard(removedCard);

        return hand.getCards().remove(cardIndex);
    }

    @Override
    public void showHand(Hand hand) {
        int handListSize = hand.getCards().size();
        for (int i = 0; i < handListSize; i++) {
            System.out.println((i + 1) + ") " + hand.getCards().get(i));
        }
    }
}
