package model.deck;

import lombok.*;
import model.card.Card;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscardPile {
    private List<Card> discardedCards;
}
