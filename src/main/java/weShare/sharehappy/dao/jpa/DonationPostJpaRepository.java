package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.constant.PostSortCriteria;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationPostRepository;
import weShare.sharehappy.entity.DonationPost;
import java.util.List;

@AllArgsConstructor
@Repository
public class DonationPostJpaRepository implements DonationPostRepository {

    private final SpringDataDonationPostRepository repository;

    @Override
    public DonationPost save(DonationPost donationPost) {
        return null;
    }

    @Override
    public DonationPost findById(Long id) {
        return null;
    }

    @Override
    public Long counDonationPost() {
        return repository.count();
    }

    @Override
    public List<DonationPost> findByCategorySortPostSortCriteria(PostSortCriteria postSortCriteria, int page, int size,String categoryName) {
        Slice<DonationPost> slice = null;
        PageRequest pageRequest = PageRequest.of(page,size);
        switch (postSortCriteria){
            case ASCENDING_FUND_PERCENTAGE:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,"fundPercentage"));
                break;
            case DESCENDING_FUND_PERCENTAGE:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"fundPercentage"));
                break;
            case ASCENDING_CURRENT_AMOUNT:_AMOUNT:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,"currentAmount"));
                break;
            case DESCENDING_CURRENT_AMOUNT:_AMOUNT:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"currentAmount"));
                break;
            case DESCENDING_REG_DATE:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"regdate"));
                break;
            case ASCENDING_END_DATE:
                pageRequest = PageRequest.of(page,size,Sort.by(Sort.Direction.ASC,"enddate"));
                break;
        }
        if(categoryName.equals("all")){
            slice = repository.findDonationPosts(pageRequest);
        }
        else{
            slice = repository.findDonationPosts(pageRequest,categoryName);
        }
        return slice.getContent();
    }

}
