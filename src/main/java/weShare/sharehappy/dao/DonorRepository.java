package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.Organization;

import java.util.Optional;

public interface DonorRepository {

    Optional<Donor> findByEmail(String email);
    Donor save(Donor donor);

}
