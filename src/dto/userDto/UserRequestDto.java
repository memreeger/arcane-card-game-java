package dto.userDto;

import dto.baseDto.BaseUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                ", user name = '" + getUserName() + '\'' +
                ", paswword = '" + getPassword() + '\'' +
                '}';
    }
}
