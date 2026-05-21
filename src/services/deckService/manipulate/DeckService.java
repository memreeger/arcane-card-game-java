package services.deckService.manipulate;

import abst.IDeckDraw;
import abst.IDeckShuffle;
import model.card.Card;
import model.deck.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DeckService implements IDeckDraw, IDeckShuffle {
    private static DeckService instance;

    private DeckService() {
    }

    public static DeckService getInstance() {

        if (instance == null) {
            instance = new DeckService();
        }

        return instance;
    }


    @Override
    public void shuffleDeck(Deck deck) {
        Collections.shuffle(deck.getCards());
    }

    public Card drawCard(Deck deck) {

        if (deck.getCards().isEmpty()) {
            throw new IllegalStateException("Deck is empty.");
        }

        return deck.getCards().remove(0);
    }

    @Override
    public List<Card> drawCards(Deck deck, int count) {
        if (count < 0 || count > 5) {
            throw new IllegalArgumentException("Enter a number between 0-5");
        }

        List<Card> drawnCards = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            drawnCards.add(drawCard(deck));
        }

        return drawnCards;

    }


}
