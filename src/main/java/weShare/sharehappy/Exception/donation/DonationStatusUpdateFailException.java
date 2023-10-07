package weShare.sharehappy.Exception.donation;

import weShare.sharehappy.constant.DonationStatus;

import java.time.LocalDateTime;

public class DonationStatusUpdateFailException extends DonationException{
    private String donateUseremail;
    private LocalDateTime donateDate;
    private Long donatePostId;
    private String orderId;
    private DonationStatus updateTryStatus;

    public DonationStatusUpdateFailException(String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId, DonationStatus updateTryStatus) {
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
        this.updateTryStatus = updateTryStatus;
    }

    public String getDonateUseremail() {
        return donateUseremail;
    }

    public LocalDateTime getDonateDate() {
        return donateDate;
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
