package weShare.sharehappy.Exception.donation;

import java.time.LocalDateTime;

public class StoreDonationInfoFailException extends DonationException{
    private String donateUseremail;
    private LocalDateTime donateDate;
    private Long donatePostId;
    private String orderId;

    public StoreDonationInfoFailException(String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId) {
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
    }
}
