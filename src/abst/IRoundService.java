package abst;

import enums.DifficultyType;
import model.round.Round;

public interface IRoundService {

    Round createRound(int roundNumber, DifficultyType difficulty);

    void setPlayerScore(Round round, int score);

    boolean isRoundCompleted(Round round);
}