package services.gameCardService;

import abst.IGameCardService;
import abst.daoAbst.gameCard.IGameCardReadable;
import abst.daoAbst.gameCard.IGameCardWriteable;
import dao.gameCardDao.GameCardDao;
import dto.gameCardDto.GameCardRequestDto;
import dto.gameCardDto.GameCardResponseDto;
import enums.CardLocation;
import model.card.Card;
import model.card.NumberCard;
import model.card.SpecialCard;
import model.deck.Deck;
import model.hand.Hand;

import java.util.ArrayList;
import java.util.List;

public class GameCardService implements IGameCardService {

    private static GameCardService instance;

    private final IGameCardReadable readDao;
    private final IGameCardWriteable writeDao;

    private GameCardService() {
        this.readDao = GameCardDao.getInstance();
        this.writeDao = GameCardDao.getInstance();
    }

    public static GameCardService getInstance() {
        if (instance == null) {
            instance = new GameCardService();
        }
        return instance;
    }

    @Override
    public void saveGameCards(List<GameCardRequestDto> requestDtos) {
        writeDao.saveAll(requestDtos);
    }

    @Override
    public List<GameCardResponseDto> getCardsByGameSessionId(int gameSessionId) {
        return readDao.findByGameSessionId(gameSessionId);
    }

    @Override
    public List<GameCardResponseDto> getCardsByLocation(int gameSessionId, CardLocation location) {
        return readDao.findByGameSessionIdAndLocation(gameSessionId, location);
    }

    @Override
    public void moveCard(int gameCardId, CardLocation location, int positionIndex) {
        writeDao.updateLocation(gameCardId, location, positionIndex);
    }

    @Override
    public void markSpecialCardAsUsed(int gameCardId) {
        writeDao.updateUsedStatus(gameCardId, true);
    }

    @Override
    public void deleteCardsByGameSessionId(int gameSessionId) {
        writeDao.deleteByGameSessionId(gameSessionId);
    }

    @Override
    public void saveGameStateCards(
            int gameSessionId,
            Deck deck,
            Hand hand,
            List<Card> discardedCards
    ) {
        if (gameSessionId <= 0) {
            throw new IllegalArgumentException("Game session id must be valid.");
        }

        List<GameCardRequestDto> requestDtos = new ArrayList<>();

        int positionIndex = 0;

        for (Card card : deck.getCards()) {
            requestDtos.add(
                    convertCardToRequestDto(
                            gameSessionId,
                            card,
                            CardLocation.DECK,
                            positionIndex++
                    )
            );
        }

        positionIndex = 0;

        for (Card card : hand.getCards()) {
            requestDtos.add(
                    convertCardToRequestDto(
                            gameSessionId,
                            card,
                            CardLocation.HAND,
                            positionIndex++
                    )
            );
        }

        positionIndex = 0;

        for (Card card : discardedCards) {
            requestDtos.add(
                    convertCardToRequestDto(
                            gameSessionId,
                            card,
                            CardLocation.DISCARD,
                            positionIndex++
                    )
            );
        }

        writeDao.deleteByGameSessionId(gameSessionId);
        writeDao.saveAll(requestDtos);
    }

    private GameCardRequestDto convertCardToRequestDto(
            int gameSessionId,
            Card card,
            CardLocation location,
            int positionIndex
    ) {
        GameCardRequestDto requestDto = new GameCardRequestDto();

        requestDto.setGameSessionId(gameSessionId);
        requestDto.setCardId(card.getId());
        requestDto.setDeckType(card.getDeckType());
        requestDto.setCardType(card.getCardType());
        requestDto.setLocation(location);
        requestDto.setPositionIndex(positionIndex);

        if (card instanceof NumberCard numberCard) {
            requestDto.setCardValue((int) numberCard.getValue());
            requestDto.setSpecialCardType(null);
            requestDto.setUsed(false);
        }

        if (card instanceof SpecialCard specialCard) {
            requestDto.setCardValue(null);
            requestDto.setSpecialCardType(specialCard.getSpecialCardType());
            requestDto.setUsed(specialCard.isUsed());
        }

        return requestDto;
    }
}