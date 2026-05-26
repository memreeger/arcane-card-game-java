package dto.baseDto;

import enums.DeckType;
import enums.DifficultyType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BaseGameSessionDto {
    private DeckType deckType;
    private DifficultyType difficulty;
    private int currentRoundNumber;
    private int totalScore;
    private int totalTargetScore;
    private int totalDiscardCount;
    private boolean gameOver;
    private boolean playerWon;
}
