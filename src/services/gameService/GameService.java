package services.gameService;

import abst.*;
import dto.userDto.UserResponseDto;
import enums.DeckType;
import enums.DifficultyType;
import factory.DeckFactory;
import model.card.Card;
import model.deck.Deck;
import model.hand.Hand;
import model.round.Round;
import services.cardService.SpecialCardService;
import services.deckService.manipulate.DeckService;
import services.discardPileService.DiscardPileService;
import services.handService.HandService;
import services.roundService.RoundService;
import services.scoreService.ScoreService;


import java.util.List;

public class GameService implements IGameService {

    private Deck deck;
    private Hand hand;
    private DifficultyType difficulty;
    private Round currentRound;
    private UserResponseDto currentUser;
    private int currentRoundNumber = 1;
    private int totalScore = 0;
    private boolean gameOver = false;
    private int totalTargetScore = 0;
    private boolean playerWon = false;

    private static GameService instance;

    private int totalDiscardCount = 0;
    private static final int MAX_DISCARD_LIMIT = 6;
    private static final int MAX_ROUND = 4;

    private final IDeckShuffle deckShuffleService = DeckService.getInstance();
    private final IHandService handService = HandService.getInstance();
    private final IDiscardPileService discardPileService = DiscardPileService.getInstance();
    private final IRoundService roundService = RoundService.getInstance();
    private final IScoreService scoreService = ScoreService.getInstance();
    private final ISpecialCardService specialCardService = SpecialCardService.getInstance();

    private GameService() {
    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }


    @Override
    public void startGame(DeckType deckType, DifficultyType difficulty, UserResponseDto user) {

        this.currentUser = user;

        this.totalTargetScore = 0;
        this.totalDiscardCount = 0;
        this.playerWon = false;
        this.difficulty = difficulty;
        this.currentRoundNumber = 1;
        this.totalScore = 0;
        this.gameOver = false;

        IDeckCreator deckCreator = DeckFactory.createDeck(deckType);

        this.deck = deckCreator.deckCreate();

        deckShuffleService.shuffleDeck(deck);

        this.hand = handService.createHand(deck);

        this.currentRound = roundService.createRound(currentRoundNumber, difficulty);

        totalTargetScore += currentRound.getTargetScore();
    }

    @Override
    public void submitHand() {

        if (gameOver) {
            throw new IllegalStateException("Game is already over.");
        }

        // Base score
        int baseScore = scoreService.calculateScore(hand);

        // Special card effects
        int finalScore =
                specialCardService.applyEffects(
                        hand,
                        baseScore
                );

        // Round score set
        roundService.setPlayerScore(currentRound, finalScore);

        // Total score
        totalScore += finalScore;

        System.out.println("\n========= ROUND RESULT =========");
        System.out.println("Round Number: " + currentRoundNumber);
        System.out.println("Base Score: " + baseScore);
        System.out.println("Final Score: " + finalScore);
        System.out.println("Target Score: " + currentRound.getTargetScore());

        if (finalScore >= currentRound.getTargetScore()) {
            System.out.println("Round completed successfully.");
        } else {
            System.out.println("Round failed.");
        }

        // GAME FINISH
        if (currentRoundNumber == MAX_ROUND) {

            gameOver = true;

            System.out.println("\n========= GAME OVER =========");
            double playerAverage = totalScore / (double) MAX_ROUND;
            double targetAverage = totalTargetScore / (double) MAX_ROUND;

            playerWon = playerAverage >= targetAverage;

            System.out.println("\n========= GAME OVER =========");
            System.out.println("Total Score: " + totalScore);
            System.out.println("Total Target Score: " + totalTargetScore);

            System.out.println("Player Average: " + playerAverage);
            System.out.println("Target Average: " + targetAverage);

            if (playerWon) {
                System.out.println("YOU WIN!");
            } else {
                System.out.println("YOU LOSE!");
            }

            return;
        }

        // NEXT ROUND
        currentRoundNumber++;

        currentRound =
                roundService.createRound(
                        currentRoundNumber,
                        difficulty
                );

        totalTargetScore += currentRound.getTargetScore();

        // New hand
        this.hand = handService.createHand(deck);


        System.out.println("\nNext round started.");
    }

    @Override
    public boolean discardCardAndDrawNewCard(int cardIndex) {
        if (totalDiscardCount >= MAX_DISCARD_LIMIT) {
            System.out.println("\n ======================================== Maximum discard limit reached. ========================================");
            return false;
        }
        Card removedCard = handService.removeCard(hand, cardIndex);
        discardPileService.addCardByCard(removedCard);

        handService.addCard(hand, deck);

        totalDiscardCount++;

        return true;
    }

    @Override
    public int getRemainingDeckCardCount() {
        return deck.getCards().size();
    }

    @Override
    public int getDiscardedCardCount() {
        return discardPileService.getDiscardedCardCount();
    }

    @Override
    public Hand getCurrentHand() {
        return hand;
    }

    @Override
    public List<Card> getDiscardedCards() {
        return discardPileService.getDiscardedCards();
    }

    @Override
    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    @Override
    public int getCurrentTargetScore() {
        return currentRound.getTargetScore();
    }

    @Override
    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void showCurrentHand() {
        handService.showHand(hand);

    }

    @Override
    public void showDiscardedCards() {
        discardPileService.showDiscardedCards();
    }

    @Override
    public boolean isPlayerWon() {
        return playerWon;
    }

    @Override
    public void useSpecialCard(int specialCardIndex) {

        specialCardService.useSpecialCard(hand, deck, specialCardIndex);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return currentUser;
    }


}
