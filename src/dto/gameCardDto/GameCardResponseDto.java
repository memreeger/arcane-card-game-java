package dto.gameCardDto;

import dto.baseDto.BaseGameCardDto;
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
public class GameCardResponseDto extends BaseGameCardDto {
    private int id;

    public GameCardResponseDto(
            int id,
            int gameSessionId,
            int cardId,
            DeckType deckType,
            CardType cardType,
            SpecialCardType specialCardType,
            Integer cardValue,
            CardLocation location,
            int positionIndex,
            boolean used
    ) {
        super(gameSessionId,
                cardId,
                deckType,
                cardType,
                specialCardType,
                cardValue,
                location,
                positionIndex,
                used
        );

        this.id = id;
    }
}