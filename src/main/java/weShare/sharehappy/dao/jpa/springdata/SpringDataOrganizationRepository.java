package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.Organization;

public interface SpringDataOrganizationRepository extends JpaRepository<Organization,Long> {
    Organization findByEmail(String email);
}
