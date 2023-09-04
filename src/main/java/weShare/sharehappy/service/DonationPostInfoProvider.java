package weShare.sharehappy.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.NoExistingDonationPostCategory;
import weShare.sharehappy.Exception.NoMoreDonationPostException;
import weShare.sharehappy.constant.PageSize;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.entity.DonationPost;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DonationPostInfoProvider {

    private final DonationPostRepository postRepository;
    private final DonationPostCategoryRepository categoryRepository;
    public List<DonationPostSummary> getDonationPosts(DonationPostSummaryRequest request){
        Optional.ofNullable(categoryRepository.findByCategoryName(request.getCategoryName()))
                .orElseThrow(()->new NoExistingDonationPostCategory());
        List<DonationPost> list = postRepository.findByCategorySortPostSortCriteria(
                request.getPostSortCriteria(), request.getPage(), PageSize.MAIN_PAGE_SIZE.getSize(),request.getCategoryName());
        if(list.size() == 0){
            throw new NoMoreDonationPostException();
        }
        return list.stream().map(post -> post.changeToLisInfo()).collect(Collectors.toList());
    }

    public Long coutDonationPost(){
        return postRepository.counDonationPost();
    }

}
