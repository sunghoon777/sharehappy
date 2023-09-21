package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import weShare.sharehappy.entity.DonationPostImage;

public interface SpringDataDonationPostImageRepository extends JpaRepository<DonationPostImage,Long> {
}
