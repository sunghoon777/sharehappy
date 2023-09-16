package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

public interface UserRepository {
    User findByEmail(String email);
}
