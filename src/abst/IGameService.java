package abst;

import dto.userDto.UserResponseDto;
import enums.DeckType;

import enums.DifficultyType;
import model.card.Card;
import model.hand.Hand;

import java.util.List;

public interface IGameService {

    void startGame(DeckType deckType, DifficultyType difficulty, UserResponseDto user);

    void showCurrentHand();

    boolean discardCardAndDrawNewCard(int cardIndex);

    void showDiscardedCards();

    void submitHand();

    boolean isGameOver();

    boolean isPlayerWon();

    List<Card> getDiscardedCards();

    Hand getCurrentHand();

    int getDiscardedCardCount();

    int getCurrentRoundNumber();

    int getCurrentTargetScore();

    int getTotalScore();

    int getRemainingDeckCardCount();

    void useSpecialCard(int specialCardIndex);

    UserResponseDto getCurrentUser();
}