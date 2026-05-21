package factory;

import abst.IDeckCreator;
import enums.DeckType;
import services.deckService.create.AlchemySetDeckCreator;
import services.deckService.create.ElementSetDeckCreator;
import services.deckService.create.QuantumSetDeckCreator;

public class DeckFactory {
    public static IDeckCreator createDeck(DeckType type) {
        switch (type) {
            case ALCHEMY_SET:
                return new AlchemySetDeckCreator();

            case ELEMENT_SET:
                return new ElementSetDeckCreator();

            case QUANTUM_SET:
                return new QuantumSetDeckCreator();

            default:
                throw new IllegalArgumentException("Yanlış bir giriş yaptınız");
        }

    }
}
