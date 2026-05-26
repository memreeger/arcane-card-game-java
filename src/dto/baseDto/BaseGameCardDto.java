package dto.baseDto;

import enums.CardLocation;
import enums.CardType;
import enums.DeckType;
import enums.SpecialCardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BaseGameCardDto {
    private int gameSessionId;
    private int cardId;
    private DeckType deckType;
    private CardType cardType;
    private SpecialCardType specialCardType;
    private Integer cardValue;
    private CardLocation location;
    private int positionIndex;
    private boolean used;
}
