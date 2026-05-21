package services.deckService.create;

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
public class AlchemySetDeckCreator extends AbstractDeckCreator implements IDeckCreator {

    @Override
    protected DeckType getDeckType() {
        return DeckType.ALCHEMY_SET;
    }

    @Override
    protected EnumSet<CardType> getCardType() {
        return CardType.ALCHEMY_CARD_TYPES;
    }

    @Override
    protected EnumSet<SpecialCardType> getSpecialCardType() {
        return SpecialCardType.ALCHEMY_SPECIAL_SET;
    }


/*
        for (byte value = 1; value <= 9; value++) {

            cards.add(new NumberCard(
                    value,
                    CardType.FIRE,
                    DeckType.ALCHEMY_SET
            ));

            cards.add(new NumberCard(
                    value,
                    CardType.WATER,
                    DeckType.ALCHEMY_SET
            ));

            cards.add(new NumberCard(
                    value,
                    CardType.EARTH,
                    DeckType.ALCHEMY_SET
            ));

            cards.add(new NumberCard(
                    value,
                    CardType.AIR,
                    DeckType.ALCHEMY_SET
            ));
        }


 */

    //cards.add(new SpecialCard(DeckType.ALCHEMY_SET, SpecialCardType.PHILISOPHERS_STONE));
    //cards.add(new SpecialCard(DeckType.ALCHEMY_SET, SpecialCardType.TRANSMUTATION));
    //cards.add(new SpecialCard(DeckType.ALCHEMY_SET, SpecialCardType.ELEMENTAL_FUSION));
    //cards.add(new SpecialCard(DeckType.ALCHEMY_SET, SpecialCardType.CATALYST));


}