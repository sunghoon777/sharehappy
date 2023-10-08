package weShare.sharehappy.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import weShare.sharehappy.constant.PostSortCriteria;
import weShare.sharehappy.entity.DonationPost;
import java.util.List;
import java.util.Optional;

public interface DonationPostRepository {

    DonationPost save(DonationPost donationPost);
    Optional<DonationPost> findByIdWithOrganizationAndImages(Long id);
    Optional<DonationPost> findById(Long id);
    Long counDonationPost();
    List<DonationPost> findAllByCategoryNameWithOrganizationAndImages(PostSortCriteria postSortCriteria, int page, int size,String categoryName);
    List<String> getOrganizationNamesByPostIds(List<Long> postIds);
    List<DonationPost> findAllByEmailWithOrganizationAndImages(int page,int size, String email);

}
