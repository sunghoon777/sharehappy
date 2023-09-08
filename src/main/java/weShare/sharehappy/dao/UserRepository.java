package weShare.sharehappy.dao;

import weShare.sharehappy.entity.User;

public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
}
