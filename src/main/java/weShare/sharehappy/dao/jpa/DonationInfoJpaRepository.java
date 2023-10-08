package weShare.sharehappy.dao.jpa;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.dao.DonationInfoRepository;
import weShare.sharehappy.dao.jpa.springdata.SpringDataDonationInfoRepository;
import weShare.sharehappy.entity.DonationInfo;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class DonationInfoJpaRepository implements DonationInfoRepository {

    SpringDataDonationInfoRepository repository;

    @Override
    public DonationInfo save(DonationInfo donationInfo) {
        return repository.save(donationInfo);
    }

    @Override
    public List<DonationInfo> findAllByUserIdAndDonationStatusWithDonationPost(int page, int size,Long userId, List<DonationStatus> donationStatusList) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return repository.findAllByUserIdAndDonationStatusWithDonationPost(pageRequest,userId,donationStatusList).getContent();
    }

    @Override
    public Optional<DonationInfo> findByOrderIdWithDonorAndDonationPost(String orderId) {
        return repository.findByOrderIdWithDonorAndDonationPost(orderId);
    }

    @Override
    public Optional<DonationInfo> findByOrderId(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public List<DonationInfo> findAllByPostIdAndDonationStatusWithDonorAndDonationPost(Long postId,DonationStatus donationStatus) {
        return repository.findAllByPostIdAndDonationStatusWithDonorAndDonationPost(postId,donationStatus);
    }

    @Override
    public Boolean existsNotRefundDonationInDonationPost(Long postId, DonationStatus donationStatus, LocalDateTime refundLimitDate) {
        PageRequest oneSizepageReq = PageRequest.of(0,1);
        return repository.findFirstNotRefundDonationInDonationPost(oneSizepageReq,postId,donationStatus,refundLimitDate).isPresent();
    }

    @Override
    public int updateDonationStatusByPostIdAndDonationStatus(DonationStatus updateDonationStatus, Long postId, DonationStatus donationStatus) {
        return repository.updateDonationStatusByPostIdAndDonationStatus(updateDonationStatus,postId,donationStatus);
    }

    @Override
    public int updateDonationsStatusInOrderIds(DonationStatus updateDonationStatus, List<String> orderIds) {
        return repository.updateDonationsStatusInOrderIds(updateDonationStatus,orderIds);
    }


}
