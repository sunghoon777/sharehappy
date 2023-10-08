package weShare.sharehappy.dao;

import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.entity.DonationInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DonationInfoRepository {
    DonationInfo save(DonationInfo donationInfo);
    List<DonationInfo> findAllByUserIdAndDonationStatusWithDonationPost(int page, int size,Long userId, List<DonationStatus> donationStatusList);
    Optional<DonationInfo> findByOrderIdWithDonorAndDonationPost(String orderId);
    Optional<DonationInfo> findByOrderId(String orderId);
    List<DonationInfo> findAllByPostIdAndDonationStatusWithDonorAndDonationPost(Long postId,DonationStatus donationStatus);
    Boolean existsNotRefundDonationInDonationPost(Long postId, DonationStatus donationStatus, LocalDateTime refundLimitDate);
    int updateDonationStatusByPostIdAndDonationStatus(DonationStatus updateDonationStatus,Long postId,DonationStatus donationStatus);
    int updateDonationsStatusInOrderIds(DonationStatus updateDonationStatus,List<String> orderIds);
}
