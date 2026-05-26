package abst.daoAbst;

import model.user.User;

public interface IUserDaoReadable {
    User findByUsername(String username);

    boolean existsByUsername(String username);


}
