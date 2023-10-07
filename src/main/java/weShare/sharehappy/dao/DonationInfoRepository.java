package weShare.sharehappy.dao;

import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.entity.DonationInfo;

import java.util.List;
import java.util.Optional;

public interface DonationInfoRepository {
    DonationInfo save(DonationInfo donationInfo);
    List<DonationInfo> findAllByUserIdAndDonationStatusWithDonationPost(int page, int size,Long userId, List<DonationStatus> donationStatusList);
    Optional<DonationInfo> findByOrderIdWithDonorAndDonationPost(String orderId);
    Optional<DonationInfo> findByOrderId(String orderId);
    void flush();

}
