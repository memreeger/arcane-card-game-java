package factory;

import abst.IDeckCreator;
import enums.DeckType;
import services.deck.create.AlchemySetDeckCreator;
import services.deck.create.ElementSetDeckCreator;
import services.deck.create.QuantumSetDeckCreator;

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
