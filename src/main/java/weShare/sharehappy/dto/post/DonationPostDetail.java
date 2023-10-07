package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationPostDetail {
    private Long id;
    private String title;
    private String content;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private Double fundPercentage;
    private String startdate;
    private String enddate;
    private String categoryName;
    private String categoryKrName;
    private String organizationName;
    private String organizationIntroduce;
}
