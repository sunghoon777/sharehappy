package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DonationPostCommentsPageInfo {
    private Integer startPage;
    private Integer lastPage;
    private Integer currentPage;
    private List<DonationPostCommentSummary> commentSummaries;
}
