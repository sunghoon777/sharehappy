package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.User;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
