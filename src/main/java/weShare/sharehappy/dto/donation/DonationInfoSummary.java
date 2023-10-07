package weShare.sharehappy.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DonationInfoSummary {

    private String orderId;
    private String donationStatusKrName;
    private String donationCompleteDate;
    private String donationCancleDate;
    private Long postId;
    private String postTitle;
    private String organizationName;

}
