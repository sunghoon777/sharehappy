package weShare.sharehappy.Exception.donation;

import weShare.sharehappy.constant.DonationStatus;

import java.time.LocalDateTime;

public class DonationStatusUpdateFailException extends DonationException{
    private String donateUseremail;
    private LocalDateTime statusUpdateDate;
    private Long donatePostId;
    private String orderId;
    private DonationStatus updateTryStatus;

    public DonationStatusUpdateFailException(String donateUseremail, LocalDateTime statusUpdateDate, Long donatePostId, String orderId, DonationStatus updateTryStatus) {
        this.donateUseremail = donateUseremail;
        this.statusUpdateDate = statusUpdateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
        this.updateTryStatus = updateTryStatus;
    }

    public String getDonateUseremail() {
        return donateUseremail;
    }

    public LocalDateTime getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public Long getDonatePostId() {
        return donatePostId;
    }

    public String getOrderId() {
        return orderId;
    }

    public DonationStatus getUpdateTryStatus() {
        return updateTryStatus;
    }
}
