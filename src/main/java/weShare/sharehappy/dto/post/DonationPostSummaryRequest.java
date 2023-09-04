package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import weShare.sharehappy.constant.PostSortCriteria;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationPostSummaryRequest {

    @NotNull
    @PositiveOrZero
    private Integer page; //특정 페이지
    @NotEmpty
    private String categoryName;//게시판 카테고리
    @NotNull
    private PostSortCriteria postSortCriteria;//정렬 기준

}
