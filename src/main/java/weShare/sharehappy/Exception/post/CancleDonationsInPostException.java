package weShare.sharehappy.Exception.post;

import weShare.sharehappy.Exception.donation.DonationException;

import java.time.LocalDateTime;
import java.util.List;

public class CancleDonationsInPostException extends DonationException {
    private Long postId;
    private LocalDateTime cancleTryDate;
    private String cancleFailOrderId;
    private List<String> cancleOrderIds; //pg사에는 이미 취소 요청이 된 주문들의 주문번호 리스트 이후 해당 주문번호는 cancle 상태로 만들어야함

    public CancleDonationsInPostException(Long postId, LocalDateTime cancleTryDate, String cancleFailOrderId, List<String> cancleOrderIds) {
        this.postId = postId;
        this.cancleTryDate = cancleTryDate;
        this.cancleFailOrderId = cancleFailOrderId;
        this.cancleOrderIds = cancleOrderIds;
    }

    public Long getPostId() {
        return postId;
    }

    public LocalDateTime getCancleTryDate() {
        return cancleTryDate;
    }

    public String getCancleFailOrderId() {
        return cancleFailOrderId;
    }

    public List<String> getCancleOrderIds() {
        return cancleOrderIds;
    }
}
