package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class DonationPostCommentsPageInfo {
    private Integer startPage;
    private Integer lastPage;
    private Integer currentPage;
    private List<DonationPostCommentSummary> commentSummaries;
    private HashSet<Long> commentAccessSet = new HashSet<>();

    public DonationPostCommentsPageInfo(Integer startPage, Integer lastPage, Integer currentPage, List<DonationPostCommentSummary> commentSummaries) {
        this.startPage = startPage;
        this.lastPage = lastPage;
        this.currentPage = currentPage;
        this.commentSummaries = commentSummaries;

    }

    public void constructCommentAcccessSet(String currentUserEmail){
        commentSummaries.stream().filter(c -> c.getEmail().equals(currentUserEmail)).forEach(c -> commentAccessSet.add(c.getCommentId()));
    }
}
