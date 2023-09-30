package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DonationPostCommentSummary {
    private Long commentId;
    private String userName;
    private String date;
    private String content;
    private Integer childCommentCount;
}
