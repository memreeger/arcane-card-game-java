package services.deck.create;

import abst.IDeckCreator;
import enums.CardType;
import enums.DeckType;
import enums.SpecialCardType;
import model.card.Card;
import model.card.NumberCard;
import model.card.SpecialCard;
import model.deck.Deck;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class AbstractDeckCreator implements IDeckCreator {
    protected abstract DeckType getDeckType();

    protected abstract EnumSet<CardType> getCardType();

    protected abstract EnumSet<SpecialCardType> getSpecialCardType();

    @Override
    public Deck deckCreate() {

        List<Card> cards = new ArrayList<>();

        for (CardType type : getCardType()) {

            for (byte value = 1; value <= 9; value++) {

                cards.add(new NumberCard(
                        value,
                        type,
                        getDeckType()
                ));
            }
        }

        for (SpecialCardType specialCardType : getSpecialCardType()) {

            cards.add(new SpecialCard(
                    getDeckType(),
                    specialCardType
            ));
        }

        return new Deck(getDeckType(), cards);
    }
}
