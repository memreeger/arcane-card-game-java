package abst;

import model.deck.Deck;
import model.hand.Hand;

public interface ISpecialCardService {
    int applyEffects(Hand hand, int currentScore);

    void useSpecialCard(
            Hand hand,
            Deck deck,
            int specialCardIndex
    );
}
