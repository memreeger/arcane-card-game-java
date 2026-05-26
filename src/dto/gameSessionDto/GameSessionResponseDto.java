package dto.gameSessionDto;

import dto.baseDto.BaseGameSessionDto;
import enums.DeckType;
import enums.DifficultyType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class GameSessionResponseDto extends BaseGameSessionDto {

    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GameSessionResponseDto(
            int id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            DeckType deckType,
            DifficultyType difficulty,
            int currentRoundNumber,
            int totalScore,
            int totalTargetScore,
            int totalDiscardCount,
            boolean gameOver,
            boolean playerWon
    ) {

        super(
                deckType,
                difficulty,
                currentRoundNumber,
                totalScore,
                totalTargetScore,
                totalDiscardCount,
                gameOver,
                playerWon
        );

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
