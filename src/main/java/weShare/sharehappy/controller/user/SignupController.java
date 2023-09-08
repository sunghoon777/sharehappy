package weShare.sharehappy.controller.user;

import com.amazonaws.services.voiceid.model.ExistingEnrollmentAction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import weShare.sharehappy.Exception.ExistingUserException;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.signup.SignupRequest;
import weShare.sharehappy.dto.signup.SignupResponse;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.entity.User;
import weShare.sharehappy.service.MessageInfoProvider;
import weShare.sharehappy.service.UserManager;


@AllArgsConstructor
@Controller
@RequestMapping("/signup")
public class SignupController {

    private MessageInfoProvider messageInfoProvider;
    private UserManager userManager;

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> existingUserExHandle(ExistingUserException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.CONFLICT);
    }


    @GetMapping("/form")
    public String getForm(){
        return "signup/SignupForm";
    }


    @PostMapping
    public ResponseEntity<Object> signup(@Validated @RequestBody SignupRequest signupRequest, BindingResult bindingResult){
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
        UserSummary userSummary = userManager.signUp(signupRequest);
        String message = messageInfoProvider.getMessage("success.signup");
        SignupResponse response = new SignupResponse(userSummary.getEmail(),userSummary.getNickname(),message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
