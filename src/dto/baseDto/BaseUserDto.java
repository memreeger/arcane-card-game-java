package dto.baseDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class BaseUserDto {
    private int id;
    private String userName;
    private LocalDateTime createdAt;
}
