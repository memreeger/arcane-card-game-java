package abst;

import model.card.Card;
import model.deck.Deck;

import java.util.List;

public interface IDeckDraw {

    Card drawCard(Deck deck);

    List<Card> drawCards(Deck deck, int count);
}
