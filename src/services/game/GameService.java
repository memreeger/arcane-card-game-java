package services.game;

import abst.*;
import enums.DeckType;
import factory.DeckFactory;
import model.card.Card;
import model.deck.Deck;
import model.hand.Hand;
import services.deckService.manipulate.DeckService;
import services.discardPileService.DiscardPileService;
import services.handService.HandService;

import java.util.List;

public class GameService implements IGameService {

    private Deck deck;
    private Hand hand;

    private static GameService instance;

    private final IDeckShuffle deckShuffleService = DeckService.getInstance();
    private final IHandService handService = HandService.getInstance();
    private final IDiscardPileService discardPileService = DiscardPileService.getInstance();

    private GameService() {
    }

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }


    @Override
    public void startGame(DeckType deckType) {

        IDeckCreator deckCreator = DeckFactory.createDeck(deckType);

        this.deck = deckCreator.deckCreate();

        deckShuffleService.shuffleDeck(deck);

        this.hand = handService.createHand(deck);
    }

    @Override
    public Hand getCurrentHand() {
        return hand;
    }

    @Override
    public void showCurrentHand() {
        handService.showHand(hand);

    }

    @Override
    public void discardCardAndDrawNewCard(int cardIndex) {
        Card removeCard = handService.removeCard(hand, cardIndex);
        discardPileService.addCardByCard(removeCard);

        handService.addCard(hand, deck);

    }

    @Override
    public int getRemainingDeckCardCount() {
        return deck.getCards().size();
    }

    @Override
    public int getDiscardedCardCount() {
        return discardPileService.getDiscardedCardCount();
    }

    @Override
    public void showDiscardedCards() {
        discardPileService.showDiscardedCards();
    }

    @Override
    public List<Card> getDiscardedCards() {
        return discardPileService.getDiscardedCards();
    }


}
