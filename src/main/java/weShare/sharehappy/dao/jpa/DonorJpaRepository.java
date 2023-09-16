package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonorRepsitory;
import weShare.sharehappy.entity.Donor;


@Repository
@AllArgsConstructor
public class DonorJpaRepository implements DonorRepository {

    SpringDataDonorRepsitory donorRepository;

    @Override
    public Donor findByEmail(String email) {
        return donorRepository.findByEmail(email);
    }

    @Override
    public Donor save(Donor donor) {
        return donorRepository.save(donor);
    }
}
