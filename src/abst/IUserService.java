package abst;

import dto.userDto.UserRequestDto;
import dto.userDto.UserResponseDto;

public interface IUserService {


    UserResponseDto register(UserRequestDto requestDto);

    UserResponseDto login(UserRequestDto requestDto);

    boolean existsByUsername(String username);

}
