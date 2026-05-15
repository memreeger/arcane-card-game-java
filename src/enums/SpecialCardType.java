package enums;

import java.util.EnumSet;

public enum SpecialCardType {
    PHILISOPHERS_STONE("Doubles the score of the current hand (one-time use)"),
    TRANSMUTATION(""),
    ELEMENTAL_FUSION(""),
    CATALYST(""),

    PERIODIC_BOOST(""),
    NOBLE_GAS(""),
    ISOTOPE_DECAY(""),
    ELECTRON_BOND(""),

    QUANTUM_ENTANGLEMENT(""),
    SUPERPOSITION(""),
    GLUON_BIND(""),
    PHOTON_BURST("");

    public static final EnumSet<SpecialCardType> ALCHEMY_SPECIAL_SET =
            EnumSet.of(PHILISOPHERS_STONE, TRANSMUTATION, ELEMENTAL_FUSION, CATALYST);

    public static final EnumSet<SpecialCardType> ELEMENT_SPECIAL_SET =
            EnumSet.of(PERIODIC_BOOST, NOBLE_GAS, ISOTOPE_DECAY, ELECTRON_BOND);

    public static final EnumSet<SpecialCardType> QUANTUM_SPECIAL_SET =
            EnumSet.of(QUANTUM_ENTANGLEMENT, SUPERPOSITION, GLUON_BIND, PHOTON_BURST);

    private String description;

    SpecialCardType(String description) {

        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}