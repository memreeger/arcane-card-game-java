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

        List<Card> cards = hand.getCards();

        Map<CardType, List<NumberCard>> groupedCards = new HashMap<>();

        // Kartları type'a göre grupla
        for (Card card : cards) {

            if (card instanceof NumberCard numberCard) {

                CardType type = card.getCardType();

                groupedCards
                        .computeIfAbsent(type, k -> new ArrayList<>())
                        .add(numberCard);
            }
        }

        // FOUR OF A KIND
        if (groupedCards.size() == 1) {

            int total = 0;

            for (List<NumberCard> list : groupedCards.values()) {
                for (NumberCard card : list) {
                    total += card.getValue();
                }
            }

            return total * 10;
        }

        // ONE OF EACH TYPE
        if (groupedCards.size() == 4) {

            int total = 0;

            for (List<NumberCard> list : groupedCards.values()) {
                for (NumberCard card : list) {
                    total += card.getValue();
                }
            }

            return total * 5;
        }

        // ONE PAIR
        for (Map.Entry<CardType, List<NumberCard>> entry : groupedCards.entrySet()) {

            List<NumberCard> sameTypeCards = entry.getValue();

            if (sameTypeCards.size() == 2) {

                int pairSum = 0;
                int otherSum = 0;

                // Pair toplamı
                for (NumberCard card : sameTypeCards) {
                    pairSum += card.getValue();
                }

                // Diğer kartlar
                for (Map.Entry<CardType, List<NumberCard>> otherEntry : groupedCards.entrySet()) {

                    if (otherEntry.getKey() != entry.getKey()) {

                        for (NumberCard card : otherEntry.getValue()) {
                            otherSum += card.getValue();
                        }
                    }
                }

                return (pairSum * 2) + otherSum;
            }
        }

        // DİĞER TÜM DURUMLAR
        int total = 0;

        for (List<NumberCard> list : groupedCards.values()) {
            for (NumberCard card : list) {
                total += card.getValue();
            }
        }

        return total;
    }
}
