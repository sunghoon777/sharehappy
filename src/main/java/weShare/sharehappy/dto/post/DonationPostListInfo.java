package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//기부 게시글 목록을 보여주기 위해서 간단한 정보만을 담는 dto
public class DonationPostListInfo {

    private String postId;
    private String organizationName;
    private String title;
    private String imageUrl;
    private String targetAmount;
    private String currentAmount;


}
