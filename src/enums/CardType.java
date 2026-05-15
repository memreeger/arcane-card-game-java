package enums;
import java.util.EnumSet;


public enum CardType {

    FIRE, WATER, EARTH, AIR, // ALCHEMY SET
    HYDROGEN, OXYGEN, NITROGEN, CARBON_DIOXIDE, // Element SET
    QUARK, BOSON, GLUON, PHOTON; // QUANTUM SET

    public static final EnumSet<CardType> ALCHEMY_CARD_TYPES =
            EnumSet.of(FIRE, WATER, EARTH, AIR);

    public static final EnumSet<CardType> ELEMENT_CARD_TYPES =
            EnumSet.of(HYDROGEN, OXYGEN, NITROGEN, CARBON_DIOXIDE);

    public static final EnumSet<CardType> QUANTUM_CARD_TYPES =
            EnumSet.of(QUARK, BOSON, GLUON, PHOTON);
}

/*
package enums;

public enum CardType {
    FIRE,
    WATER,
    EARTH,
    AIR,

    HYDROGEN,
    OXYGEN,
    NITROGEN,
    CARBON_DIOXIDE,

    QUARK,
    BOSON,
    GLUON,
    PHOTON
}
 */