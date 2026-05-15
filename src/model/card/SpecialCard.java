package model.card;

import enums.CardType;
import enums.DeckType;
import enums.SpecialCardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class SpecialCard extends Card {
    private String name;
    private String description;
    private boolean used;
    private SpecialCardType specialCardType;

    public SpecialCard(DeckType deckType, SpecialCardType specialCardType) {
        super(deckType);
        this.specialCardType = specialCardType;
        this.description = specialCardType.getDescription();
    }
}
