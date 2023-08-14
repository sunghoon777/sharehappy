package sharehappy.service.DonationPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharehappy.Exception.NoExistingDonationPostCategoryException;
import sharehappy.Exception.NoMoreDonationPostException;
import sharehappy.dao.DonationPostCategoryDao;
import sharehappy.dao.DonationPostDao;
import sharehappy.dto.DonationPostListRequest;
import sharehappy.dto.DonationPostListResponse;
import sharehappy.dto.Range;
import sharehappy.service.Range.RangeCalculator;

import java.util.List;

@Service
public class DonationPostInfoProvider {

    @Autowired
    private DonationPostCategoryDao categoryRepository;
    @Autowired
    private DonationPostDao postRepository;
    @Autowired
    private RangeCalculator rangeCalculator;


    @Transactional
    public List<DonationPostListResponse> getDonationPostListInfo(DonationPostListRequest donationPostListRequest) throws NoMoreDonationPostException,NoExistingDonationPostCategoryException{
        //counter 올바른 값인지 검사
        long count = postRepository.countDonationPost();
        Range range = rangeCalculator.calculateDonationPostRange(donationPostListRequest.getGroup());
        if(range.getStart() > count){
            throw new NoMoreDonationPostException();
        }

    }

}
