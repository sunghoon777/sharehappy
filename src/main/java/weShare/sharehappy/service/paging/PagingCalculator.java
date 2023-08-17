package weShare.sharehappy.service.paging;

import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.NoExistingDonationPostCategoryException;
import weShare.sharehappy.Exception.NoMorePostException;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dto.paging.DonationPostPagingInfo;

@Service
public class PagingCalculator {

    private final DonationPostCategoryRepository donationPostCategoryRepository;
    private final DonationPostRepository donationPostRepository;

    public PagingCalculator(DonationPostCategoryRepository donationPostCategoryRepository, DonationPostRepository donationPostRepository) {
        this.donationPostCategoryRepository = donationPostCategoryRepository;
        this.donationPostRepository = donationPostRepository;
    }

    public static final int POST_PAGE_SIZE = 12;
    public static final int COMMENT_PAGE_SIZE = 12;

    //특정 page, 카테고리에 따른 paging 정보 제공
    public DonationPostPagingInfo calculateDonationPost(long page, String category){
        if(donationPostCategoryRepository.selectCategory(category).isEmpty()){
            throw new NoExistingDonationPostCategoryException();
        }
        int pageSize = POST_PAGE_SIZE; //페이지 사이즈
        //카테고리에 따른 총 게시글 개수 가져오기
        long totalCount = category.equals("전체")?donationPostRepository.countDonationPost():donationPostRepository.countDonationPost(category);
        long start = (page-1)*POST_PAGE_SIZE+1; // 게시글 시작 번호
        long last = page*POST_PAGE_SIZE; // 게시글 끝 번호
        //시작 번호가 총 게시글 개수를 넘어가면 예외 발생
        if(start > totalCount){
            throw new NoMorePostException();
        }
        //마지막 번호가 총 개시글 개수를 넘어가면 페이지 크기는 totalCount-start+1
        if(last > totalCount){
            pageSize = (int) (totalCount-start+1);
        }
        return new DonationPostPagingInfo(page,pageSize,totalCount);
    }

}
