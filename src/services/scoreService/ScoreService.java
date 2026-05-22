package services.scoreService;

import abst.IScoreService;
import enums.CardType;
import model.card.Card;
import model.card.NumberCard;
import model.hand.Hand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreService implements IScoreService {
    private static ScoreService instance;

    private ScoreService() {
    }

    public static ScoreService getInstance() {
        if (instance == null) {
            instance = new ScoreService();
        }
        return instance;
    }

    @Override
    public int calculateScore(Hand hand) {

        List<NumberCard> numberCards = new ArrayList<>();

        for (Card card : hand.getCards()) {
            if (card instanceof NumberCard numberCard) {
                numberCards.add(numberCard);
            }
        }

        if (numberCards.isEmpty()) {
            return 0;
        }

        Map<CardType, List<NumberCard>> groupedCards = new HashMap<>();

        for (NumberCard numberCard : numberCards) {
            groupedCards
                    .computeIfAbsent(numberCard.getCardType(), k -> new ArrayList<>())
                    .add(numberCard);
        }

        int total = 0;

        for (NumberCard card : numberCards) {
            total += card.getValue();
        }

        // FOUR OF A KIND
        if (numberCards.size() == 4 && groupedCards.size() == 1) {
            return total * 10;
        }

        // ONE OF EACH TYPE
        if (numberCards.size() == 4 && groupedCards.size() == 4) {
            return total * 5;
        }

        // PAIR / PAIRS
        int score = 0;
        boolean hasPair = false;

        for (List<NumberCard> sameTypeCards : groupedCards.values()) {

            if (sameTypeCards.size() == 2) {
                hasPair = true;

                int pairSum = 0;

                for (NumberCard card : sameTypeCards) {
                    pairSum += card.getValue();
                }

                score += pairSum * 2;
            } else {
                for (NumberCard card : sameTypeCards) {
                    score += card.getValue();
                }
            }
        }

        if (hasPair) {
            return score;
        }

        return total;
    }
}
