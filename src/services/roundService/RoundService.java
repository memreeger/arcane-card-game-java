package services.roundService;

import abst.IRoundService;
import enums.DifficultyType;
import model.round.Round;

public class RoundService implements IRoundService {

    private static RoundService instance;

    private RoundService() {
    }

    public static RoundService getInstance() {

        if (instance == null) {
            instance = new RoundService();
        }

        return instance;
    }

    @Override
    public Round createRound(int roundNumber, DifficultyType difficulty) {

        int targetScore;

        switch (difficulty) {

            case EASY:
                targetScore = 40;
                break;

            case MEDIUM:
                targetScore = 60;
                break;

            case HARD:
                targetScore = 80;
                break;

            case EXTREME:
                targetScore = 100;
                break;

            default:
                throw new IllegalArgumentException("Invalid difficulty.");
        }

        return new Round(
                roundNumber,
                targetScore,
                0,
                difficulty,
                false
        );
    }

    @Override
    public void setPlayerScore(Round round, int score) {

        round.setPlayerScore(score);

        if (score >= round.getTargetScore()) {
            round.setCompleted(true);
        }
    }

    @Override
    public boolean isRoundCompleted(Round round) {
        return round.isCompleted();
    }
}