package model.card;

import enums.CardType;
import enums.DeckType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)

public class NumberCard extends Card {
    private byte value;


    public NumberCard(byte value, CardType cardType, DeckType deckType) {

        super(deckType, cardType);
        this.value = value;
    }


}
