package weShare.sharehappy.dto.donation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationPrepareResponse {
    private String postTitle;
    private String organizationName;
    private String userName;
    private String email;
    private String donationOrderId;
    private BigDecimal amount;
}
