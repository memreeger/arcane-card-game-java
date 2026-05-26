package dto.userDto;

import dto.baseDto.BaseUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto extends BaseUserDto {

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + getId() +
                ", user name = '" + getUserName() + '\'' +
                ", created at " + getCreatedAt() +
                '}';
    }
}
