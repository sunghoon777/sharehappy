package weShare.sharehappy.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryInfo;
import weShare.sharehappy.entity.DonationPostCategory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DonationPostCategoryInfoProvider {

    private final DonationPostCategoryRepository repository;

    public List<DonationPostCategoryInfo> getAllCategory(){
        return repository.findAllSortByCategoryOrderAsc().stream().map(entity -> entity.changeToResponse()).collect(Collectors.toList());
    }

}
