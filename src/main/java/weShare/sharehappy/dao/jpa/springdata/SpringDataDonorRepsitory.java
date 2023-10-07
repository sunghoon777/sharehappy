package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

import java.util.Optional;

public interface SpringDataDonorRepsitory extends JpaRepository<Donor,Long> {
    Optional<Donor> findByEmail(String email);
}
