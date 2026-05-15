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
public class QuantumSetDeckCreator extends AbstractDeckCreator implements IDeckCreator {

    @Override
    protected DeckType getDeckType() {
        return DeckType.QUANTUM_SET;
    }

    @Override
    protected EnumSet<CardType> getCardType() {
        return CardType.QUANTUM_CARD_TYPES;
    }

    @Override
    protected EnumSet<SpecialCardType> getSpecialCardType() {
        return SpecialCardType.QUANTUM_SPECIAL_SET;
    }
}
