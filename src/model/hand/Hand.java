package model.hand;

import lombok.*;
import model.card.Card;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hand {
    private List<Card> cards = new ArrayList<>();
}
