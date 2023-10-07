package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import weShare.sharehappy.entity.DonationPost;

import java.util.List;
import java.util.Optional;

public interface SpringDataDonationPostRepository extends JpaRepository<DonationPost,Long> {

    @Query(value = "select p from DonationPost p join fetch p.organization where p.categoryName = :categoryName")
    Slice<DonationPost> findAllByCategoryNameWithOrganization(Pageable pageable, String categoryName);

    @Query(value = "select p from DonationPost p join fetch p.organization")
    Slice<DonationPost> findAllWithOrganization(Pageable pageable);

    @Query(value = "select p from DonationPost p join fetch p.organization join fetch p.images where p.id = :postId")
    Optional<DonationPost> findByIdWithOrganizationAndImages(Long postId);

    @Query(value = "select p from DonationPost p join fetch p.organization where p.id in :postIds")
    List<DonationPost> findAllByPostIdsWithDonationPost(List<Long> postIds);

}
