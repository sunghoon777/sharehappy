package weShare.sharehappy.controller.donation;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weShare.sharehappy.dto.paging.DonationPostPagingInfo;
import weShare.sharehappy.dto.post.DonationPostListRequest;
import weShare.sharehappy.dto.post.DonationPostListResponse;
import weShare.sharehappy.service.DonationPost.DonationPostInfoProvider;
import weShare.sharehappy.service.paging.PagingCalculator;

import java.util.List;


@AllArgsConstructor
@Controller
@RequestMapping("/donationPost")
public class DonationPostController {

    private final DonationPostInfoProvider donationPostInfoProvider;
    private final PagingCalculator pagingCalculator;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<DonationPostListResponse>> getDonationPosts(@Validated @ModelAttribute DonationPostListRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DonationPostPagingInfo pagingInfo = pagingCalculator.calculateDonationPost(request.getPage(),request.getCategory());
        List<DonationPostListResponse> list = donationPostInfoProvider.getDonationPostListInfo(request,pagingInfo);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }


}
