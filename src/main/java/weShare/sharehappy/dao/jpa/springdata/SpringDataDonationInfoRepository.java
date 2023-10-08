package weShare.sharehappy.dao.jpa.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.entity.DonationInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpringDataDonationInfoRepository extends JpaRepository<DonationInfo,String> {

    @Query(value = "select d from DonationInfo d join fetch d.donationPost " +
            "where d.donor.id = :userId and d.donationStatus in :donationStatusList " +
            "order by case when d.donationCancleDate is not null then d.donationCancleDate " +
            "else d.donationCompleteDate end desc ",
            countQuery = "select count(d) from DonationInfo d where d.donor.id = :userId and d.donationStatus in :donationStatusList")
    Slice<DonationInfo> findAllByUserIdAndDonationStatusWithDonationPost(Pageable pageable, Long userId, List<DonationStatus> donationStatusList);

    @Query(value = "select d from DonationInfo d join fetch d.donor join fetch d.donationPost where d.orderId = :orderId")
    Optional<DonationInfo> findByOrderIdWithDonorAndDonationPost(String orderId);

    @Query(value = "select d from DonationInfo d join fetch d.donor join fetch d.donationPost where d.donationPost.id = :postId and d.donationStatus = :donationStatus")
    List<DonationInfo> findAllByPostIdAndDonationStatusWithDonorAndDonationPost(Long postId,DonationStatus donationStatus);

    @Query(value = "select d from DonationInfo d where d.donationPost.id = :postId and d.donationStatus = :donationStatus and d.donationCompleteDate > :refundLimitDate")
    Optional<DonationInfo> findFirstNotRefundDonationInDonationPost(Pageable oneSizepageReq, Long postId, DonationStatus donationStatus, LocalDateTime refundLimitDate);

    @Modifying
    @Query("update DonationInfo d set d.donationStatus = :updateDonationStatus where d.donationPost.id = :postId and d.donationStatus = :donationStatus")
    int updateDonationStatusByPostIdAndDonationStatus(DonationStatus updateDonationStatus,Long postId,DonationStatus donationStatus);

    @Modifying
    @Query("update DonationInfo d set d.donationStatus = :updateDonationStatus where d.orderId in :orderIds")
    int updateDonationsStatusInOrderIds(DonationStatus updateDonationStatus,List<String> orderIds);


}
