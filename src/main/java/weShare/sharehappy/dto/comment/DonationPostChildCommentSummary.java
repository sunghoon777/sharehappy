package weShare.sharehappy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DonationPostChildCommentSummary {
    private Long commentId;
    private String userName;
    private String date;
    private String content;
}
