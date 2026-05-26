package abst.daoAbst.gameSession;

import dto.gameSessionDto.GameSessionResponseDto;

import java.util.List;

public interface IGameSessionReadable {
    GameSessionResponseDto findById(int id);

    List<GameSessionResponseDto> findByUserId(int userId);

    GameSessionResponseDto findLastActiveSessionByUserId(int userId);
}
