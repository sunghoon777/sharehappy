package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonorRepsitory;
import weShare.sharehappy.dao.jpa.springdata.SpringDataUserRepository;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

@Repository
@AllArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final SpringDataUserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
