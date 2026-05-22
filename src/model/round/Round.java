package model.round;

import lombok.*;
import enums.Difficulty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Round {

    private int roundNumber;
    private int targetScore;
    private int playerScore;
    private Difficulty difficulty;
    private boolean completed;
}