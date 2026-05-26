package abst;

import dto.gameCardDto.GameCardRequestDto;
import dto.gameCardDto.GameCardResponseDto;
import enums.CardLocation;
import model.card.Card;
import model.deck.Deck;
import model.hand.Hand;

import java.util.List;

public interface IGameCardService {
    void saveGameCards(List<GameCardRequestDto> requestDtos);

    List<GameCardResponseDto> getCardsByGameSessionId(int gameSessionId);

    List<GameCardResponseDto> getCardsByLocation(int gameSessionId, CardLocation location);

    void moveCard(int gameCardId, CardLocation location, int positionIndex);

    void markSpecialCardAsUsed(int gameCardId);

    void deleteCardsByGameSessionId(int gameSessionId);

    void saveGameStateCards(int gameSessionId, Deck deck, Hand hand, List<Card> discardedCards);
}

