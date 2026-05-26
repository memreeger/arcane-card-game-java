package services.userService;

import abst.IUserService;
import dao.userDao.UserDao;
import dto.userDto.UserRequestDto;
import dto.userDto.UserResponseDto;
import model.user.User;

public class UserService implements IUserService {

    private final UserDao userDao = UserDao.getInstance();

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {

        validateRequest(requestDto);

        if (userDao.existsByUsername(requestDto.getUserName())) {
            throw new IllegalArgumentException("This username already exists.");
        }

        User user = new User(
                requestDto.getUserName(),
                requestDto.getPassword()
        );

        userDao.save(user);

        User savedUser = userDao.findByUsername(requestDto.getUserName());

        return convertToResponseDto(savedUser);
    }

    @Override
    public UserResponseDto login(UserRequestDto requestDto) {

        validateRequest(requestDto);

        User user = userDao.findByUsername(requestDto.getUserName());

        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        return convertToResponseDto(user);
    }

    @Override
    public boolean existsByUsername(String username) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank.");
        }

        return userDao.existsByUsername(username);
    }

    private void validateRequest(UserRequestDto requestDto) {

        if (requestDto == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        if (requestDto.getUserName() == null || requestDto.getUserName().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank.");
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank.");
        }
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();

        responseDto.setId(user.getId());
        responseDto.setUserName(user.getUsername());
        responseDto.setCreatedAt(user.getCreatedAt());

        return responseDto;
    }
}