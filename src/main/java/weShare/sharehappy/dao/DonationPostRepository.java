package weShare.sharehappy.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import weShare.sharehappy.constant.PostSortCriteria;
import weShare.sharehappy.entity.DonationPost;

import java.util.List;

public interface DonationPostRepository {

    DonationPost save(DonationPost donationPost);
    DonationPost findById(Long id);
    Long counDonationPost();
    List<DonationPost> findByCategorySortPostSortCriteria(PostSortCriteria postSortCriteria, int page, int size,String categoryName);


}
