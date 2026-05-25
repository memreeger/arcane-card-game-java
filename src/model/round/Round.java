package model.round;

import enums.DifficultyType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Round {

    private int roundNumber;
    private int targetScore;
    private int playerScore;
    private DifficultyType difficulty;
    private boolean completed;
}