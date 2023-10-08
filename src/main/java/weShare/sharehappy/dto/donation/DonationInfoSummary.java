package weShare.sharehappy.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DonationInfoSummary {

    private String orderId;
    private BigDecimal amount;
    private String donationStatusKrName;
    private String donationCompleteDate;
    private String donationCancleDate;
    private String postTitle;
    private String organizationName;

}
