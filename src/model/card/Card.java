package model.card;

import enums.CardType;
import enums.DeckType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class Card {
    private int id;
    private DeckType deckType;
    private CardType cardType;
    private static int idCounter = 1;

    public Card(DeckType deckType, CardType cardType) {
        this.id = idCounter++;
        this.deckType = deckType;
        this.cardType = cardType;
    }

    public Card(DeckType deckType) {
        this.id = idCounter++;
        this.deckType = deckType;
    }


}
