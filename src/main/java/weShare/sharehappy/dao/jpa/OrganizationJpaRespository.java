package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.OrganizationRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataOrganizationRepository;
import weShare.sharehappy.entity.Organization;

@Repository
@AllArgsConstructor
public class OrganizationJpaRespository implements OrganizationRepository {

    SpringDataOrganizationRepository repository;

    @Override
    public Organization findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Organization save(Organization organization) {
        return repository.save(organization);
    }
}
