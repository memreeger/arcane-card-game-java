package model.deck;

import enums.DeckType;
import lombok.*;
import model.card.Card;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Deck {
    private DeckType deckType;
    private List<Card> cards;

    @Override
    public String toString() {

        String result = "Deck Type: " + deckType + "\n\n";

        for (Card card : cards) {
            result += card + "\n";
        }
        result += "\nTotal Card Count: " + cards.size();

        return result;
    }
}
