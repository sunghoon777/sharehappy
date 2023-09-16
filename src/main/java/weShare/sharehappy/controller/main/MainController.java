package weShare.sharehappy.controller.main;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import weShare.sharehappy.constant.PostSortCriteria;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryInfo;
import weShare.sharehappy.service.DonationPostCategoryInfoProvider;
import weShare.sharehappy.service.DonationPostInfoProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class MainController {

    private final DonationPostCategoryInfoProvider categoryInfoProvider;
    private final DonationPostInfoProvider postInfoProvider;

    @GetMapping("/")
    public String welcome(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String getForm(Model model, HttpServletRequest httpServletRequest){
        List<DonationPostCategoryInfo> categoryInfoList= categoryInfoProvider.getAllCategory();
        DonationPostSummaryRequest request = new DonationPostSummaryRequest(0,"all", PostSortCriteria.DESCENDING_FUND_PERCENTAGE);
        Long postCount = postInfoProvider.coutDonationPost();
        List<DonationPostSummary> postSummaryList = postInfoProvider.getDonationPosts(request);
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null && httpSession.getAttribute(SessionKey.USER_AUTH.name()) != null){
            model.addAttribute("userSummary",httpSession.getAttribute(SessionKey.USER_AUTH.name()));
        }
        model.addAttribute("postCount",postCount);
        model.addAttribute("categoryList",categoryInfoList);
        model.addAttribute("postSummaryList",postSummaryList);
        model.addAttribute("postSortCriteriaList",PostSortCriteria.values());
        return "main/Main";
    }

}
