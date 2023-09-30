package weShare.sharehappy.dao;

import weShare.sharehappy.entity.DonationPostCategory;

import java.util.List;

public interface DonationPostCategoryRepository {

    DonationPostCategory findByCategoryName(String name);
    List<DonationPostCategory> findAllSortByCategoryOrderAsc();

}
