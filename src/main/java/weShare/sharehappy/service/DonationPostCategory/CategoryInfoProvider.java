package weShare.sharehappy.service.DonationPostCategory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weShare.sharehappy.dao.jdbc.JdbcDonationPostCategoryRepository;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryInfoProvider {

    private final JdbcDonationPostCategoryRepository repository;

    public List<DonationPostCategoryResponse> getCategoryListInfo(){
        return repository.selectAll().stream().map(entity -> new DonationPostCategoryResponse(entity.getName(),entity.getIconUrl())).collect(Collectors.toList());
    }

}
