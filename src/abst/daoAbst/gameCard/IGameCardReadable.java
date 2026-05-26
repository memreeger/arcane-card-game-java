package abst.daoAbst.gameCard;

import dto.gameCardDto.GameCardResponseDto;
import enums.CardLocation;

import java.util.List;

public interface IGameCardReadable {
    List<GameCardResponseDto> findByGameSessionId(int gameSessionId);

    List<GameCardResponseDto> findByGameSessionIdAndLocation(int gameSessionId, CardLocation location);

    GameCardResponseDto findById(int id);
}
