package dto.gameSessionDto;

import dto.baseDto.BaseGameSessionDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameSessionRequestDto extends BaseGameSessionDto {
    private int userId;

}
