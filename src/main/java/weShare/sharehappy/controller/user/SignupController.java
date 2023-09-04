package weShare.sharehappy.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.dto.signup.SignupRequest;


@Controller
@RequestMapping("/signup")
public class SignupController {

    @GetMapping("/form")
    public String getForm(){
        return "signup/SignupForm";
    }

    @PostMapping
    public String signup(@Validated @ModelAttribute SignupRequest signupRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/form";
        }
        return "ss";
    }

}
