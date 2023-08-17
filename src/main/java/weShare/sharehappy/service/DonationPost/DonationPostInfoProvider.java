package weShare.sharehappy.service.DonationPost;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.Exception.NoExistingDonationPostCategoryException;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dto.paging.DonationPostPagingInfo;
import weShare.sharehappy.dto.post.DonationPostListRequest;
import weShare.sharehappy.dto.post.DonationPostListResponse;
import weShare.sharehappy.entity.DonationPost;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DonationPostInfoProvider {

    private final DonationPostCategoryRepository categoryRepository;
    private final DonationPostRepository postRepository;

    @Transactional
    public List<DonationPostListResponse> getDonationPostListInfo(DonationPostListRequest donationPostListRequest, DonationPostPagingInfo pagingInfo){
        if(categoryRepository.selectCategory(donationPostListRequest.getCategory()).isEmpty()){
            throw new NoExistingDonationPostCategoryException();
        }
        List<DonationPost> list = new ArrayList<>();
        switch (donationPostListRequest.getPostSortCriteria()){
            case ASCENDING_END_DATE:
                break;
            case DESCENDING_REG_DATE:
                break;
            case ASCENDING_TARGET_AMOUNT:
                break;
            case DESCENDING_TARGET_AMOUNT:
                break;
            case ASCENDING_FUND_PERCENTAGE:
                break;
            case DESCENDING_FUND_PERCENTAGE:
                break;
        }
        //to list
        return null;
    }

}
