package sharehappy.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sharehappy.dto.DonationPostCategoryResponse;
import sharehappy.service.DonationPostCategory.CategoryInfoProvider;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryInfoProvider categoryInfoProvider;

    @GetMapping("/main")
    public String getForm(Model model){
        List<DonationPostCategoryResponse> list = categoryInfoProvider.getCategoryListInfo();
        model.addAttribute("categoryList",list);
        return "main/Main";
    }

}
