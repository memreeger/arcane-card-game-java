package abst.daoAbst.gameSession;

import dto.gameSessionDto.GameSessionRequestDto;
import dto.gameSessionDto.GameSessionResponseDto;

public interface IGameSessionWriteable {
    GameSessionResponseDto save(GameSessionRequestDto requestDto);

    void update(GameSessionResponseDto responseDto);

    void markGameAsFinished(int gameSessionId, boolean playerWon);
}
