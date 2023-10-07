package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationPostSummary {

    private Long postId;
    private String organizationName;
    private String title;
    private String thumbNailImageUrl;
    private BigDecimal currentAmount;
    private Double fundPercentage;


}
