package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate regdate;
    private LocalDate enddate;
    private String categoryName;
    private String thumbnailUrl;
    private String organizationName;
    private String organizationIntroduce;
}
