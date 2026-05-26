package abst;

import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;

import java.util.List;

public interface IGameSessionService {
    GameSessionResponseDto createGameSession(GameSessionRequestDto requestDto);

    void updateGameSession(GameSessionResponseDto responseDto);

    GameSessionResponseDto getGameSessionById(int id);

    List<GameSessionResponseDto> getGameSessionsByUserId(int userId);

    GameSessionResponseDto getLastActiveSessionByUserId(int userId);

    void finishGameSession(int gameSessionId, boolean playerWon);
}
