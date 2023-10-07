package weShare.sharehappy.Exception.donation;

import java.time.LocalDateTime;

public class CancleDonationFailException extends DonationException{
    private String pgOrderId;
    private String donateUseremail;
    private LocalDateTime donateDate;
    private Long donatePostId;
    private String orderId;
    private int httpStatusCode;

    public CancleDonationFailException(String pgOrderId, String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId, int httpStatusCode) {
        this.pgOrderId = pgOrderId;
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
        this.httpStatusCode = httpStatusCode;
    }

    public CancleDonationFailException(String pgOrderId, String donateUseremail, LocalDateTime donateDate, Long donatePostId, String orderId) {
        this.pgOrderId = pgOrderId;
        this.donateUseremail = donateUseremail;
        this.donateDate = donateDate;
        this.donatePostId = donatePostId;
        this.orderId = orderId;
    }
}
