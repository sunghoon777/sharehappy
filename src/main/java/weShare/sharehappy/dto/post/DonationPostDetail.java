package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationPostDetail {
    private Long id;
    private String title;
    private String content;
    private Long targetAmount;
    private Long currentAmount;
    private Double fundPercentage;
    private Date startdate;
    private Date enddate;
    private String categoryName;
    private String categoryKrName;
    private String organizationName;
    private String organizationIntroduce;
}
