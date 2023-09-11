package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

public interface UserRepository {
    Donor save(Donor donor);
    User findByEmail(String email);
}
