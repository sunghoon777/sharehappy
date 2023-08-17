package weShare.sharehappy.dao;

import weShare.sharehappy.entity.DonationPostCategory;

import java.util.List;
import java.util.Optional;

public interface DonationPostCategoryRepository {

    public List<DonationPostCategory> selectAll();

    public  Optional<DonationPostCategory> selectCategory(String categoryName);

}
