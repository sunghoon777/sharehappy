package weShare.sharehappy.dao;

import weShare.sharehappy.entity.Organization;

public interface OrganizationRepository {

    Organization findByEmail(String email);
    Organization save(Organization organization);

}
