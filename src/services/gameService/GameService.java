package services.gameService;

import abst.*;
import dto.gameCardDto.GameCardResponseDto;
import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;
import dto.userDto.UserResponseDto;
import enums.CardLocation;
import enums.DeckType;
import enums.DifficultyType;
import factory.DeckFactory;
import model.card.Card;
import model.card.NumberCard;
import model.card.SpecialCard;
import model.deck.Deck;
import model.hand.Hand;
import model.round.Round;
import services.cardService.SpecialCardService;
import services.deckService.manipulate.DeckService;
import services.discardPileService.DiscardPileService;
import services.gameCardService.GameCardService;
import services.gameSession.GameSessionService;
import services.handService.HandService;
import services.roundService.RoundService;
import services.scoreService.ScoreService;


import java.util.ArrayList;
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
    private final IGameCardService gameCardService = GameCardService.getInstance();
    private final IGameSessionService gameSessionService = GameSessionService.getInstance();
    private GameSessionResponseDto currentGameSession;

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
        discardPileService.clearDiscardPile();

        IDeckCreator deckCreator = DeckFactory.createDeck(deckType);
        this.deck = deckCreator.deckCreate();

        deckShuffleService.shuffleDeck(deck);

        this.hand = handService.createHand(deck);

        this.currentRound = roundService.createRound(currentRoundNumber, difficulty);

        totalTargetScore += currentRound.getTargetScore();
        GameSessionRequestDto requestDto = new GameSessionRequestDto();

        requestDto.setUserId(currentUser.getId());
        requestDto.setDeckType(deckType);
        requestDto.setDifficulty(difficulty);
        requestDto.setCurrentRoundNumber(currentRoundNumber);
        requestDto.setTotalScore(totalScore);
        requestDto.setTotalTargetScore(totalTargetScore);
        requestDto.setTotalDiscardCount(totalDiscardCount);
        requestDto.setGameOver(gameOver);
        requestDto.setPlayerWon(playerWon);

        currentGameSession =
                gameSessionService.createGameSession(requestDto);

        gameCardService.saveGameStateCards(
                currentGameSession.getId(),
                deck,
                hand,
                discardPileService.getDiscardedCards()
        );
    }

    @Override
    public void submitHand() {

        if (gameOver) {
            throw new IllegalStateException("Game is already over.");
        }

        int baseScore = scoreService.calculateScore(hand);

        int finalScore = specialCardService.applyEffects(hand, baseScore);

        roundService.setPlayerScore(currentRound, finalScore);

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

        if (currentRoundNumber == MAX_ROUND) {

            gameOver = true;

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

            currentGameSession.setCurrentRoundNumber(currentRoundNumber);
            currentGameSession.setTotalScore(totalScore);
            currentGameSession.setTotalTargetScore(totalTargetScore);
            currentGameSession.setTotalDiscardCount(totalDiscardCount);
            currentGameSession.setGameOver(gameOver);
            currentGameSession.setPlayerWon(playerWon);

            gameSessionService.updateGameSession(currentGameSession);

            gameCardService.saveGameStateCards(
                    currentGameSession.getId(),
                    deck,
                    hand,
                    discardPileService.getDiscardedCards()
            );

            return;
        }

        currentRoundNumber++;

        currentRound = roundService.createRound(
                currentRoundNumber,
                difficulty
        );

        totalTargetScore += currentRound.getTargetScore();

        this.hand = handService.createHand(deck);

        currentGameSession.setCurrentRoundNumber(currentRoundNumber);
        currentGameSession.setTotalScore(totalScore);
        currentGameSession.setTotalTargetScore(totalTargetScore);
        currentGameSession.setTotalDiscardCount(totalDiscardCount);
        currentGameSession.setGameOver(gameOver);
        currentGameSession.setPlayerWon(playerWon);

        gameSessionService.updateGameSession(currentGameSession);

        gameCardService.saveGameStateCards(
                currentGameSession.getId(),
                deck,
                hand,
                discardPileService.getDiscardedCards()
        );

        System.out.println("\nNext round started.");
    }

    @Override
    public boolean discardCardAndDrawNewCard(int cardIndex) {

        if (totalDiscardCount >= MAX_DISCARD_LIMIT) {
            System.out.println("\nMaximum discard limit reached.");
            return false;
        }

        Card removedCard = handService.removeCard(hand, cardIndex);
        discardPileService.addCardByCard(removedCard);

        handService.addCard(hand, deck);

        totalDiscardCount++;

        currentGameSession.setTotalDiscardCount(totalDiscardCount);

        gameSessionService.updateGameSession(currentGameSession);

        gameCardService.saveGameStateCards(
                currentGameSession.getId(),
                deck,
                hand,
                discardPileService.getDiscardedCards()
        );

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

        gameCardService.saveGameStateCards(
                currentGameSession.getId(),
                deck,
                hand,
                discardPileService.getDiscardedCards()
        );
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return currentUser;
    }

    @Override
    public void loadGame(int gameSessionId, UserResponseDto user) {

        this.currentUser = user;

        currentGameSession =
                gameSessionService.getGameSessionById(gameSessionId);

        if (currentGameSession == null) {
            throw new IllegalArgumentException("Game session not found.");
        }

        this.currentRoundNumber = currentGameSession.getCurrentRoundNumber();
        this.totalScore = currentGameSession.getTotalScore();
        this.totalTargetScore = currentGameSession.getTotalTargetScore();
        this.totalDiscardCount = currentGameSession.getTotalDiscardCount();
        this.gameOver = currentGameSession.isGameOver();
        this.playerWon = currentGameSession.isPlayerWon();
        this.difficulty = currentGameSession.getDifficulty();

        this.currentRound =
                roundService.createRound(
                        currentRoundNumber,
                        difficulty
                );

        List<GameCardResponseDto> deckCards =
                gameCardService.getCardsByLocation(
                        gameSessionId,
                        CardLocation.DECK
                );

        List<GameCardResponseDto> handCards =
                gameCardService.getCardsByLocation(
                        gameSessionId,
                        CardLocation.HAND
                );

        List<GameCardResponseDto> discardCards =
                gameCardService.getCardsByLocation(
                        gameSessionId,
                        CardLocation.DISCARD
                );

        this.deck = new Deck(
                currentGameSession.getDeckType(),
                convertDtoListToCards(deckCards)
        );

        this.hand = new Hand(
                convertDtoListToCards(handCards)
        );

        discardPileService.clearDiscardPile();

        for (Card card : convertDtoListToCards(discardCards)) {
            discardPileService.addCardByCard(card);
        }
    }

    private List<Card> convertDtoListToCards(List<GameCardResponseDto> dtoList) {

        List<Card> cards = new ArrayList<>();

        for (GameCardResponseDto dto : dtoList) {

            if (dto.getSpecialCardType() != null) {

                SpecialCard specialCard =
                        new SpecialCard(
                                dto.getDeckType(),
                                dto.getSpecialCardType()
                        );

                specialCard.setId(dto.getCardId());
                specialCard.setUsed(dto.isUsed());

                cards.add(specialCard);

            } else {

                NumberCard numberCard =
                        new NumberCard(
                                dto.getCardValue().byteValue(),
                                dto.getCardType(),
                                dto.getDeckType()
                        );

                numberCard.setId(dto.getCardId());

                cards.add(numberCard);
            }
        }

        return cards;
    }


}
