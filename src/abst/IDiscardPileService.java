package abst;

import model.card.Card;


import java.util.List;


public interface IDiscardPileService {
    //void addCard(Hand hand, int cardIndex);

    void addCardByCard(Card card);

    int getDiscardedCardCount();

    void showDiscardedCards();

    List<Card> getDiscardedCards();

}
