package sharehappy.service.Range;

import org.springframework.stereotype.Service;
import sharehappy.dto.Range;

@Service
public class RangeCalculator {
    
    //게시글들은 8개 단위로 불러오므로 상수를 8로 설정함
    //group 은 0(0~7), 1(8~15) .... 로 되어있음
    private static final int DONATION_POST_LIST_COUNT_PER_RANGE = 8;

    public Range calculateDonationPostRange(long group){
        long start = group*DONATION_POST_LIST_COUNT_PER_RANGE;
        long end = start+DONATION_POST_LIST_COUNT_PER_RANGE-1;
        return new Range(start,end);
    }
    
    
}
