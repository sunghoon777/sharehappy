package weShare.sharehappy.Exception.donation;

import java.time.LocalDateTime;

public class PrepareDonationFailException extends DonationException {
    private String donateUseremail;
    private LocalDateTime donateDate;
    private Long donatePostId;
    private String orderId;
    private int httpStatusCode;

    public PrepareDonationFailException(String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId, int httpStatusCode) {
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
        this.httpStatusCode = httpStatusCode;
    }

    public PrepareDonationFailException(String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId) {
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
    }
}
