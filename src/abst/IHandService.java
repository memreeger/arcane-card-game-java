package abst;

import model.card.Card;
import model.deck.Deck;
import model.hand.Hand;

import java.util.List;

public interface IHandService {

    Hand createHand(Deck deck);

    void addCard(Hand hand, Deck deck);

    void addCards(Hand hand, Deck deck, int count);

    void removeCard(Hand hand,int cardIndex);

    void showHand(Hand hand);
}
