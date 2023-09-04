package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import weShare.sharehappy.entity.DonationPost;

public interface SpringDataDonationPostRepository extends JpaRepository<DonationPost,Long> {

    @Query(value = "select p from DonationPost p join fetch p.organization where p.categoryName = :categoryName")
    Slice<DonationPost> findDonationPosts(Pageable pageable, String categoryName);

    @Query(value = "select p from DonationPost p join fetch p.organization")
    Slice<DonationPost> findDonationPosts(Pageable pageable);

}
