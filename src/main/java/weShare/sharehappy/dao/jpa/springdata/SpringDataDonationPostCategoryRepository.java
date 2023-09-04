package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.DonationPostCategory;

public interface SpringDataDonationPostCategoryRepository extends JpaRepository<DonationPostCategory,String> {
    DonationPostCategory findByName(String name);

}
