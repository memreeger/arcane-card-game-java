package abst;

import model.hand.Hand;

public interface ISpecialCardService {
    int applyEffects(Hand hand, int currentScore);
}
