package services.deck.create;

import abst.IDeckCreator;
import enums.CardType;
import enums.DeckType;
import enums.SpecialCardType;
import lombok.*;

import java.util.EnumSet;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ElementSetDeckCreator extends AbstractDeckCreator implements IDeckCreator {

    @Override
    protected DeckType getDeckType() {
        return DeckType.ELEMENT_SET;
    }

    @Override
    protected EnumSet<CardType> getCardType() {
        return CardType.ELEMENT_CARD_TYPES;
    }

    @Override
    protected EnumSet<SpecialCardType> getSpecialCardType() {
        return SpecialCardType.ELEMENT_SPECIAL_SET;
    }
}
