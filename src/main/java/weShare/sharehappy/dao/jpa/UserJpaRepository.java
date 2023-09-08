package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.UserRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataUserRepository;
import weShare.sharehappy.entity.User;

@Repository
@AllArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final SpringDataUserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
