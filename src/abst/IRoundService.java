package abst;

import enums.Difficulty;
import model.round.Round;

public interface IRoundService {

    Round createRound(int roundNumber, Difficulty difficulty);

    void setPlayerScore(Round round, int score);

    boolean isRoundCompleted(Round round);
}