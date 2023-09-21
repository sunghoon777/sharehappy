package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.DonationPostImageRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationPostImageRepository;
import weShare.sharehappy.entity.DonationPostImage;

import java.util.List;

@AllArgsConstructor
@Repository
public class DonationPostImageJpaRespository implements DonationPostImageRepository {

    SpringDataDonationPostImageRepository repository;

    @Override
    public List<DonationPostImage> save(List<DonationPostImage> donationPostImageList) {
        return repository.saveAll(donationPostImageList);
    }
}
