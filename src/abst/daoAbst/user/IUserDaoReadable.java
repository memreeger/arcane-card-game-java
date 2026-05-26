package abst.daoAbst.user;

import model.user.User;

public interface IUserDaoReadable {
    User findByUsername(String username);

    boolean existsByUsername(String username);


}
