package enums;

import java.util.EnumSet;

public enum SpecialCardType {

    PHILOSOPHERS_STONE(
            "Philosopher's Stone",
            "Doubles the score of the current hand (one-time use)"
    ),

    TRANSMUTATION(
            "Transmutation",
            "Swap one card in hand with a random card from deck (doesn't count toward discard limit)"
    ),

    ELEMENTAL_FUSION(
            "Elemental Fusion",
            "If you have at least 2 cards of the same type, treat them as 4 of that type for scoring this round"
    ),

    CATALYST(
            "Catalyst",
            "Increase the multiplier of any \"pair\" scoring by +1 (×2 becomes ×3)"
    ),

    PERIODIC_BOOST(
            "Periodic Boost",
            "Add +2 to the numeric value of all cards in current hand"
    ),

    NOBLE_GAS(
            "Noble Gas",
            "Lock one card in place - it cannot be discarded but its value counts twice for scoring"
    ),

    ISOTOPE_DECAY(
            "Isotope Decay",
            "Discard your entire hand and draw 4 new cards without using discard limit (one-time)"
    ),

    ELECTRON_BOND(
            "Electron Bond",
            "Create a pair between two different card types - they count as a matching pair for scoring"
    ),

    QUANTUM_ENTANGLEMENT(
            "Quantum Entanglement",
            "Triple the score of one card from the current hand"
    ),

    SUPERPOSITION(
            "Superposition",
            "Play two scoring patterns simultaneously - take the better result"
    ),

    GLUON_BIND(
            "Gluon Bind",
            "Merge two cards into one with combined numeric value (max 9), " +
                    "draw one card in its place. " +
                    "(If different types are merged, the new card takes the type of the first selected card)"
    ),

    PHOTON_BURST(
            "Photon Burst",
            "Reveal the next 3 cards in deck, you may swap one with a card in hand"
    );

    public static final EnumSet<SpecialCardType> ALCHEMY_SPECIAL_SET =
            EnumSet.of(
                    PHILOSOPHERS_STONE,
                    TRANSMUTATION,
                    ELEMENTAL_FUSION,
                    CATALYST
            );

    public static final EnumSet<SpecialCardType> ELEMENT_SPECIAL_SET =
            EnumSet.of(
                    PERIODIC_BOOST,
                    NOBLE_GAS,
                    ISOTOPE_DECAY,
                    ELECTRON_BOND
            );

    public static final EnumSet<SpecialCardType> QUANTUM_SPECIAL_SET =
            EnumSet.of(
                    QUANTUM_ENTANGLEMENT,
                    SUPERPOSITION,
                    GLUON_BIND,
                    PHOTON_BURST
            );

    private final String name;
    private final String description;

    SpecialCardType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}