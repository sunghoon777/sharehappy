package weShare.sharehappy.controller.main;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryResponse;
import weShare.sharehappy.service.DonationPostCategory.CategoryInfoProvider;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private final CategoryInfoProvider categoryInfoProvider;

    @GetMapping("/main")
    public String getForm(Model model){
        List<DonationPostCategoryResponse> list = categoryInfoProvider.getCategoryListInfo();
        model.addAttribute("categoryList",list);
        return "main/Main";
    }


}
