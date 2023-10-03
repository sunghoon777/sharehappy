package weShare.sharehappy.controller.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.WrongAccessDonationPostCommentException;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.comment.*;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.comment.DonationPostCommentManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class DonationPostCommentCrudController {

    DonationPostCommentManager commentManager;
    MessageInfoProvider messageInfoProvider;

    @ExceptionHandler(WrongAccessDonationPostCommentException.class)
    public ResponseEntity<Object> wrongAccessToCommentExHandle(WrongAccessDonationPostCommentException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.FORBIDDEN);
    }

    @PostMapping("/donationPostComment/add")
    public ResponseEntity<Object> addComment(@Validated @RequestBody DonationPostCommentAddRequest addRequest, BindingResult bindingResult, HttpSession httpSession){
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
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        commentManager.addComment(addRequest,userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/donationPostComment/remove")
    public ResponseEntity<Object> removeComment(@RequestParam(name = "commentId")Long commentId,HttpSession httpSession){
        System.out.println("ssf");
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        commentManager.removeComment(commentId,userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/donationPostComment/update")
    public ResponseEntity<Object> updateComment(@Validated @RequestBody DonationPostCommentUpdateRequest updateRequest,BindingResult bindingResult, HttpSession httpSession){
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
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        commentManager.updateComment(updateRequest,userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/donationPostComment/reply/add")
    public ResponseEntity<Object> addComment(@Validated @RequestBody DonationPostCommentReplyAddRequest addRequest, BindingResult bindingResult, HttpSession httpSession){
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
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        commentManager.addChildComment(addRequest,userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
