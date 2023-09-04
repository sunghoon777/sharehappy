package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationPostCategoryRepository;
import weShare.sharehappy.entity.DonationPostCategory;
import java.util.List;

@AllArgsConstructor
@Repository
public class DonationPostCategoryJpaRepository implements DonationPostCategoryRepository{

    private final SpringDataDonationPostCategoryRepository repository;

    @Override
    public DonationPostCategory findByCategoryName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<DonationPostCategory> findAllSortByCategoryOrderAsc() {
        return repository.findAll(Sort.by(Sort.Direction.ASC,"categoryOrder"));
    }


}
