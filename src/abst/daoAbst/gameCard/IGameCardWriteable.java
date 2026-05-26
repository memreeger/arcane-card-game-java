package abst.daoAbst.gameCard;

import dto.gameCardDto.GameCardRequestDto;
import enums.CardLocation;

import java.util.List;

public interface IGameCardWriteable {
    void save(GameCardRequestDto requestDto);

    void saveAll(List<GameCardRequestDto> requestDtos);

    void updateLocation(int gameCardId, CardLocation location, int positionIndex);

    void updateUsedStatus(int gameCardId, boolean used);

    void deleteByGameSessionId(int gameSessionId);
}
