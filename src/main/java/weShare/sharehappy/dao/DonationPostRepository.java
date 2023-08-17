package weShare.sharehappy.dao;

import weShare.sharehappy.constant.SortingOrder;
import weShare.sharehappy.entity.DonationPost;

import java.util.List;

public interface DonationPostRepository {

    public Long countDonationPost();

    public Long countDonationPost(String category);

    public List<DonationPost> selectOrderByRegDate(String category, SortingOrder sortingOrder);

    public List<DonationPost> selectOrderByEndDate(String category,SortingOrder sortingOrder);

    public List<DonationPost> selectOrderByTargetAmount(String category, SortingOrder sortingOrder);

    public List<DonationPost> selectOrderByFundPercentageDESC(String category, SortingOrder sortingOrder);

}
