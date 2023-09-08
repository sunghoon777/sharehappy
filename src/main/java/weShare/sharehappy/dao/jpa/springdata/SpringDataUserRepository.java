package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.User;

public interface SpringDataUserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

}
