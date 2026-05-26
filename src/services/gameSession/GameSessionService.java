package services.gameSession;

import abst.IGameSessionService;
import abst.daoAbst.gameSession.IGameSessionReadable;
import abst.daoAbst.gameSession.IGameSessionWriteable;
import dao.gameSessionDao.GameSessionDao;
import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;

import java.util.List;

public class GameSessionService implements IGameSessionService {

    private static GameSessionService instance;

    private final IGameSessionReadable gameSessionReadableDao =
            GameSessionDao.getInstance();

    private final IGameSessionWriteable gameSessionWriteableDao =
            GameSessionDao.getInstance();

    private GameSessionService() {
    }

    public static GameSessionService getInstance() {

        if (instance == null) {
            instance = new GameSessionService();
        }

        return instance;
    }

    @Override
    public GameSessionResponseDto createGameSession(GameSessionRequestDto requestDto) {

        validateRequestDto(requestDto);

        return gameSessionWriteableDao.save(requestDto);
    }

    @Override
    public void updateGameSession(GameSessionResponseDto responseDto) {

        if (responseDto == null) {
            throw new IllegalArgumentException("Game session response cannot be null.");
        }

        if (responseDto.getId() <= 0) {
            throw new IllegalArgumentException("Game session id must be valid.");
        }

        gameSessionWriteableDao.update(responseDto);
    }

    @Override
    public GameSessionResponseDto getGameSessionById(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Game session id must be valid.");
        }

        return gameSessionReadableDao.findById(id);
    }

    @Override
    public List<GameSessionResponseDto> getGameSessionsByUserId(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("User id must be valid.");
        }

        return gameSessionReadableDao.findByUserId(userId);
    }

    @Override
    public GameSessionResponseDto getLastActiveSessionByUserId(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("User id must be valid.");
        }

        return gameSessionReadableDao.findLastActiveSessionByUserId(userId);
    }

    @Override
    public void finishGameSession(int gameSessionId, boolean playerWon) {

        if (gameSessionId <= 0) {
            throw new IllegalArgumentException("Game session id must be valid.");
        }

        gameSessionWriteableDao.markGameAsFinished(
                gameSessionId,
                playerWon
        );
    }

    private void validateRequestDto(GameSessionRequestDto requestDto) {

        if (requestDto == null) {
            throw new IllegalArgumentException("Game session request cannot be null.");
        }

        if (requestDto.getUserId() <= 0) {
            throw new IllegalArgumentException("User id must be valid.");
        }

        if (requestDto.getDeckType() == null) {
            throw new IllegalArgumentException("Deck type cannot be null.");
        }

        if (requestDto.getDifficulty() == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }

        if (requestDto.getCurrentRoundNumber() <= 0) {
            throw new IllegalArgumentException("Current round number must be valid.");
        }

        if (requestDto.getTotalScore() < 0) {
            throw new IllegalArgumentException("Total score cannot be negative.");
        }

        if (requestDto.getTotalTargetScore() < 0) {
            throw new IllegalArgumentException("Total target score cannot be negative.");
        }

        if (requestDto.getTotalDiscardCount() < 0) {
            throw new IllegalArgumentException("Total discard count cannot be negative.");
        }
    }
}