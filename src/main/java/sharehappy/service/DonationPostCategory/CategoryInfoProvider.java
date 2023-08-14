package sharehappy.service.DonationPostCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharehappy.dao.DonationPostCategoryDao;
import sharehappy.dto.DonationPostCategoryResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryInfoProvider {

    @Autowired
    private DonationPostCategoryDao repository;

    public List<DonationPostCategoryResponse> getCategoryListInfo(){
        return repository.selectAll().stream().map(entity -> new DonationPostCategoryResponse(entity.getName(),entity.getIconUrl())).collect(Collectors.toList());
    }

}
