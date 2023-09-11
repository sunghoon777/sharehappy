package weShare.sharehappy.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.ExistingUserException;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.signup.DonorSignupRequest;
import weShare.sharehappy.dto.signup.DonorSignupResponse;
import weShare.sharehappy.dto.user.DonorSummary;
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


    @GetMapping("/donor/form")
    public String getDonorForm(){
        return "signup/DonorSignupForm";
    }

    @GetMapping("/organization/form")
    public String getOrganizationForm(){
        return "";
    }


    @PostMapping("/donor")
    public ResponseEntity<Object> signup(@Validated @RequestBody DonorSignupRequest signupRequest, BindingResult bindingResult){
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
        DonorSummary userSummary = userManager.signUp(signupRequest);
        String message = messageInfoProvider.getMessage("success.signup");
        DonorSignupResponse response = new DonorSignupResponse(userSummary.getEmail(),userSummary.getNickname(),message);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
