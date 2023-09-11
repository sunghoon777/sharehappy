package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.Donor;

public interface SpringDataDonorRepsitory extends JpaRepository<Donor,Long> {

}
