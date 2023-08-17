package weShare.sharehappy.dto.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//기부 게시글 페이징을 위한 정보
public class DonationPostPagingInfo {

    private Long pageNumber; // 현재 페이지 번호
    private Integer pageSize; // 현재 페이지 크기
    private Long totalPostCount; //게시글의 총 개수

}
