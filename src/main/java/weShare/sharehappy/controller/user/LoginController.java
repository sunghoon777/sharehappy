package weShare.sharehappy.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.ExistingUserException;
import weShare.sharehappy.Exception.NoExistingUserException;
import weShare.sharehappy.Exception.PasswordMismatchException;
import weShare.sharehappy.constant.CookieKey;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.login.LoginRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.User;
import weShare.sharehappy.service.MessageInfoProvider;
import weShare.sharehappy.service.UserAuthProvider;

import javax.servlet.http.*;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private MessageInfoProvider messageInfoProvider;
    private UserAuthProvider userAuthProvider;

    @ExceptionHandler(NoExistingUserException.class)
    public ResponseEntity<Object> noExistingUserExHandle(NoExistingUserException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<Object> passwordMismatchExHandle(PasswordMismatchException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.CONFLICT);
    }

    @GetMapping("/form")
    public String getForm(Model model, @RequestParam(required = false, defaultValue = "") String redirectURL){
        model.addAttribute("redirectURL",redirectURL);
        return "/login/LoginForm";
    }

    @PostMapping
    public ResponseEntity<Object> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult bindingResult,
                                        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //검증에 실패해도 이메일 저장 쿠키는 저장함
        Cookie cookie = new Cookie(CookieKey.EMAIL_REMEMBER.name(), loginRequest.getEmail());
        cookie.setPath("/login/form");
        cookie.setMaxAge(60*60*24*30);
        httpServletResponse.addCookie(cookie);
        if(bindingResult.hasErrors()){
            ApiValidationErrorResponse response = new ApiValidationErrorResponse();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                String fieldName = fieldError.getField();
                String message = messageInfoProvider.getMessage(fieldError.getCodes(),fieldError.getArguments());
                Object rejectValue = fieldError.getRejectedValue();
                response.addFieldErrorInfo(new FieldErrorInfo(fieldName,message));
                response.addRejectValueInfo(new RejectValueInfo(fieldName,rejectValue));
            }
            for(ObjectError objectError : bindingResult.getGlobalErrors()){
                String message = messageInfoProvider.getMessage(objectError.getCodes(), objectError.getArguments());
                response.addGlobalErrorInfo(message);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        UserSummary userSummary = userAuthProvider.login(loginRequest);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionKey.USER_AUTH.name(),userSummary);
        session.setMaxInactiveInterval(60*30);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findPassword1/form")
    public String findPassword1(){
        return "/login/FindPassword1Form";
    }


}
