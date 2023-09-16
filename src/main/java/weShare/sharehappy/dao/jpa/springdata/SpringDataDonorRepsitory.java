package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.User;

public interface SpringDataDonorRepsitory extends JpaRepository<Donor,Long> {
    Donor findByEmail(String email);
}
