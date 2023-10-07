package weShare.sharehappy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.dto.donation.DonationInfoSummary;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Entity
public class DonationInfo {

    @Id
    private String orderId;
    @Column(unique = true)
    private String pgOrderId;
    private BigDecimal amount;
    private LocalDateTime donationRequestDate;
    private LocalDateTime donationCompleteDate;
    private LocalDateTime donationFailDate;
    private LocalDateTime donationCancleDate;
    @Enumerated(EnumType.STRING)
    private DonationStatus donationStatus;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private DonationPost donationPost;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Donor donor;

    public DonationInfo(String orderId, BigDecimal amount, LocalDateTime donationRequestDate, DonationStatus donationStatus, DonationPost donationPost, Donor donor) {
        this.orderId = orderId;
        this.amount = amount;
        this.donationRequestDate = donationRequestDate;
        this.donationStatus = donationStatus;
        this.donationPost = donationPost;
        this.donor = donor;
    }

    public void orderConfirm(String pgOrderId,LocalDateTime donationCompleteDate){
        this.orderId = orderId;
        this.donationCompleteDate = donationCompleteDate;
        donationStatus = DonationStatus.PAID;
    }

    public void orderFail(String pgOrderId,LocalDateTime donationFailDate){
        this.orderId = orderId;
        this.donationFailDate = donationFailDate;
        donationStatus = DonationStatus.FAILED;
    }

    public void orderCancle(LocalDateTime donationCancleDate){
        this.donationCancleDate = donationCancleDate;
        donationStatus = DonationStatus.CANCELLED;
    }

    public DonationInfoSummary changeToDonationInfoSummary(String organizationName){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String completeDate = "";
        if(donationCompleteDate != null){
            completeDate = donationCompleteDate.format(formatter);
        }
        String cancleDate = "";
        if(donationCompleteDate != null){
            cancleDate = donationCancleDate.format(formatter);
        }
        return new DonationInfoSummary(orderId,donationStatus.getKrName(),
                completeDate,cancleDate,donationPost.getId(),donationPost.getTitle(),organizationName);
    }
}
