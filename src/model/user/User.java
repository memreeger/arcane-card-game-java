package model.user;

import lombok.*;
import model.hand.Hand;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private Hand hand;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.hand = null;

    }


}
