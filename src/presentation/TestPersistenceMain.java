package presentation;

import abst.IGameCardService;
import abst.IGameSessionService;
import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;
import enums.DeckType;
import enums.DifficultyType;
import services.gameCardService.GameCardService;
import services.gameSession.GameSessionService;

public class TestPersistenceMain {

    public static void main(String[] args) {

        IGameSessionService gameSessionService = GameSessionService.getInstance();
        IGameCardService gameCardService = GameCardService.getInstance();

        GameSessionRequestDto requestDto = new GameSessionRequestDto();

        requestDto.setUserId(1); // DB'de var olan user id
        requestDto.setDeckType(DeckType.ALCHEMY_SET);
        requestDto.setDifficulty(DifficultyType.EASY);
        requestDto.setCurrentRoundNumber(1);
        requestDto.setTotalScore(0);
        requestDto.setTotalTargetScore(40);
        requestDto.setTotalDiscardCount(0);
        requestDto.setGameOver(false);
        requestDto.setPlayerWon(false);

        System.out.println("Creating game session...");

        GameSessionResponseDto session =
                gameSessionService.createGameSession(requestDto);

        System.out.println("Created:");
        System.out.println(session);

        System.out.println("\nUpdating session...");

        session.setCurrentRoundNumber(2);
        session.setTotalScore(55);
        session.setTotalTargetScore(100);
        session.setTotalDiscardCount(2);

        gameSessionService.updateGameSession(session);

        System.out.println(gameSessionService.getGameSessionById(session.getId()));

        System.out.println("\nFinding user sessions...");
        gameSessionService.getGameSessionsByUserId(1)
                .forEach(System.out::println);

        System.out.println("\nFinishing session...");
        gameSessionService.finishGameSession(session.getId(), true);

        System.out.println(gameSessionService.getGameSessionById(session.getId()));

        System.out.println("\nTest completed.");
    }
}