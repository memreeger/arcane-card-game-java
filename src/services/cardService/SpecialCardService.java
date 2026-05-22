package services.cardService;

import abst.ISpecialCardService;
import enums.DeckType;
import enums.SpecialCardType;
import model.card.Card;
import model.card.NumberCard;
import model.card.SpecialCard;
import model.hand.Hand;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SpecialCardService implements ISpecialCardService {

    private static SpecialCardService instance;

    private SpecialCardService() {
    }

    public static SpecialCardService getInstance() {

        if (instance == null) {
            instance = new SpecialCardService();
        }

        return instance;
    }

    @Override
    public int applyEffects(Hand hand, int currentScore) {

        DeckType deckType = getDeckTypeFromHand(hand);

        EnumSet<SpecialCardType> validSpecialCards = getSpecialCardSetByDeckType(deckType);

        int finalScore = currentScore;

        for (Card card : hand.getCards()) {

            if (card instanceof SpecialCard specialCard) {

                if (specialCard.isUsed()) {
                    continue;
                }

                SpecialCardType specialCardType = specialCard.getSpecialCardType();

                if (!validSpecialCards.contains(specialCardType)) {
                    continue;
                }

                int scoreBeforeEffect = finalScore;

                switch (specialCardType) {

                    // ALCHEMY SET
                    case PHILOSOPHERS_STONE:
                        finalScore = finalScore * 2;
                        break;

                    case ELEMENTAL_FUSION:
                        finalScore = applyElementalFusion(hand, finalScore);
                        break;

                    case CATALYST:
                        finalScore = calculateScoreWithPairMultiplier(hand, 3);
                        break;

                    case TRANSMUTATION:
                        // This card requires deck interaction, so it will be handled later in GameService.
                        break;


                    // ELEMENT SET
                    case PERIODIC_BOOST:
                        finalScore = calculateScoreWithValueBonus(hand, 2);
                        break;

                    case NOBLE_GAS:
                        finalScore = finalScore + getHighestNumberCardValue(hand);
                        break;

                    case ELECTRON_BOND:
                        finalScore = applyElectronBond(hand, finalScore);
                        break;

                    case ISOTOPE_DECAY:
                        // This card requires replacing the entire hand, so it will be handled later in GameService.
                        break;


                    // QUANTUM SET
                    case QUANTUM_ENTANGLEMENT:
                        finalScore = finalScore + (getHighestNumberCardValue(hand) * 2);
                        break;

                    case SUPERPOSITION:
                        finalScore = Math.max(finalScore, calculateBestPossibleSimpleScore(hand));
                        break;

                    case GLUON_BIND:
                        // This card requires merging cards, so it will be handled later in GameService.
                        break;

                    case PHOTON_BURST:
                        // This card requires deck preview/swap, so it will be handled later in GameService.
                        break;
                }

                if (finalScore != scoreBeforeEffect) {
                    specialCard.setUsed(true);
                }
            }
        }

        return finalScore;
    }

    private DeckType getDeckTypeFromHand(Hand hand) {

        if (hand.getCards().isEmpty()) {
            throw new IllegalStateException("Hand is empty.");
        }

        return hand.getCards().get(0).getDeckType();
    }

    private EnumSet<SpecialCardType> getSpecialCardSetByDeckType(DeckType deckType) {

        return switch (deckType) {
            case ALCHEMY_SET -> SpecialCardType.ALCHEMY_SPECIAL_SET;
            case ELEMENT_SET -> SpecialCardType.ELEMENT_SPECIAL_SET;
            case QUANTUM_SET -> SpecialCardType.QUANTUM_SPECIAL_SET;
        };
    }

    private List<NumberCard> getNumberCards(Hand hand) {

        List<NumberCard> numberCards = new ArrayList<>();

        for (Card card : hand.getCards()) {
            if (card instanceof NumberCard numberCard) {
                numberCards.add(numberCard);
            }
        }

        return numberCards;
    }

    private int getNumberCardTotal(Hand hand) {

        int total = 0;

        for (NumberCard numberCard : getNumberCards(hand)) {
            total += numberCard.getValue();
        }

        return total;
    }

    private int getHighestNumberCardValue(Hand hand) {

        int highestValue = 0;

        for (NumberCard numberCard : getNumberCards(hand)) {
            if (numberCard.getValue() > highestValue) {
                highestValue = numberCard.getValue();
            }
        }

        return highestValue;
    }

    private boolean hasAtLeastTwoSameType(Hand hand) {

        List<NumberCard> numberCards = getNumberCards(hand);

        for (int i = 0; i < numberCards.size(); i++) {
            for (int j = i + 1; j < numberCards.size(); j++) {
                if (numberCards.get(i).getCardType() == numberCards.get(j).getCardType()) {
                    return true;
                }
            }
        }

        return false;
    }

    private int applyElementalFusion(Hand hand, int currentScore) {

        if (hasAtLeastTwoSameType(hand)) {
            return getNumberCardTotal(hand) * 10;
        }

        return currentScore;
    }

    private int calculateScoreWithValueBonus(Hand hand, int bonus) {

        int total = 0;

        for (NumberCard numberCard : getNumberCards(hand)) {
            total += numberCard.getValue() + bonus;
        }

        return total;
    }

    private int calculateScoreWithPairMultiplier(Hand hand, int pairMultiplier) {

        List<NumberCard> numberCards = getNumberCards(hand);

        int score = 0;
        boolean[] used = new boolean[numberCards.size()];

        for (int i = 0; i < numberCards.size(); i++) {

            if (used[i]) {
                continue;
            }

            boolean pairFound = false;

            for (int j = i + 1; j < numberCards.size(); j++) {

                if (!used[j] && numberCards.get(i).getCardType() == numberCards.get(j).getCardType()) {

                    score += (numberCards.get(i).getValue() + numberCards.get(j).getValue()) * pairMultiplier;

                    used[i] = true;
                    used[j] = true;
                    pairFound = true;

                    break;
                }
            }

            if (!pairFound && !used[i]) {
                score += numberCards.get(i).getValue();
                used[i] = true;
            }
        }

        return score;
    }

    private int applyElectronBond(Hand hand, int currentScore) {

        List<NumberCard> numberCards = getNumberCards(hand);

        if (numberCards.size() < 2) {
            return currentScore;
        }

        int highestPairBonus = 0;

        for (int i = 0; i < numberCards.size(); i++) {
            for (int j = i + 1; j < numberCards.size(); j++) {

                if (numberCards.get(i).getCardType() != numberCards.get(j).getCardType()) {

                    int possibleBonus = numberCards.get(i).getValue() + numberCards.get(j).getValue();

                    if (possibleBonus > highestPairBonus) {
                        highestPairBonus = possibleBonus;
                    }
                }
            }
        }

        return currentScore + highestPairBonus;
    }

    private int calculateBestPossibleSimpleScore(Hand hand) {

        int total = getNumberCardTotal(hand);

        int fourOfKindScore = total * 10;
        int oneOfEachTypeScore = total * 5;
        int normalScore = total;

        return Math.max(fourOfKindScore, Math.max(oneOfEachTypeScore, normalScore));
    }
}