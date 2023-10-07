package weShare.sharehappy.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.user.NoExistingUserException;
import weShare.sharehappy.Exception.auth.RandomCodeMismatchException;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.findpassword.FindPasswordRequest1;
import weShare.sharehappy.dto.findpassword.FindPasswordRequest2;
import weShare.sharehappy.service.random.RandomCodeSender;
import weShare.sharehappy.service.message.MessageInfoProvider;
import weShare.sharehappy.service.user.UserManager;
import weShare.sharehappy.vo.PasswordForgotUser;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/findPassword")
public class FindPasswordController {

    private final RandomCodeSender randomCodeSender;
    private final MessageInfoProvider messageInfoProvider;
    private final BCryptPasswordEncoder encoder;
    private final UserManager userManager;

    @GetMapping("/process1/form")
    public String getProcess1Form(){
        return "/findpassword/Process1Form";
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> messagingExHandle(NoExistingUserException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RandomCodeMismatchException.class)
    public ResponseEntity<Object> randomCodeMismatchExHandle(RandomCodeMismatchException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/process1")
    public String process1(@Validated @RequestBody FindPasswordRequest1 request,BindingResult bindingResult, HttpSession httpSession) throws MessagingException{
        if(bindingResult.hasErrors()){
            throw new NoExistingUserException();
        }
        String randomCode = randomCodeSender.sendRandomCode(request.getEmail());
        PasswordForgotUser forgotUser = new PasswordForgotUser(request.getEmail(),encoder.encode(randomCode));
        httpSession.setAttribute(SessionKey.PASSWORD_FORGOT_USER.name(),forgotUser);
        return "/findpassword/Process2Form";
    }

    @PostMapping("/process2")
    public String process2(@Validated @RequestBody FindPasswordRequest2 request, BindingResult bindingResult, HttpSession httpSession, Model model) throws MessagingException{
        Optional<Object> optionalForgotUser = Optional.ofNullable(httpSession.getAttribute(SessionKey.PASSWORD_FORGOT_USER.name()));
        if(optionalForgotUser.isEmpty()){
            return "redirect:/process1/form";
        }
        PasswordForgotUser forgotUser = (PasswordForgotUser) optionalForgotUser.get();
        if(bindingResult.hasErrors() || !encoder.matches(request.toString(),forgotUser.getEncodingRandomcode())){
            throw new RandomCodeMismatchException();
        }
        String newPassword = userManager.changePasswordForForgotUser(forgotUser);
        httpSession.invalidate();
        model.addAttribute("newPassword",newPassword);
        return "/findpassword/SuccessForm";
    }


}
