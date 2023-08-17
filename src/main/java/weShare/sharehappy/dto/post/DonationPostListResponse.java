package weShare.sharehappy.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import weShare.sharehappy.dto.paging.DonationPostPagingInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//기부 게시글 리스트 요청 응답 dto
public class DonationPostListResponse {

    private List<DonationPostListInfo> donationPostListInfoList; 
    private DonationPostPagingInfo donationPostPagingInfo;

}
