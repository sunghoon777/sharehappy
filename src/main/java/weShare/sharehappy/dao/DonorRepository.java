package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.entity.Organization;

public interface DonorRepository {

    Donor findByEmail(String email);
    Donor save(Donor donor);

}
