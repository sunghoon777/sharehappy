package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
