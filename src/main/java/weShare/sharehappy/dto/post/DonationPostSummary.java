package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationPostSummary {

    private Long postId;
    private String organizationName;
    private String title;
    private String thumbNailImageUrl;
    private Long currentAmount;
    private Double fundPercentage;


}
