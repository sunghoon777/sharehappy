package weShare.sharehappy.controller.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import weShare.sharehappy.Exception.comment.NoExistingCommentsInPage;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.comment.DonationPostCommentsPageInfo;
import weShare.sharehappy.dto.comment.DonationPostCommentsRequest;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.comment.DonationPostCommentManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class DonationPostCommentInfoController {

    DonationPostCommentManager commentManager;
    MessageInfoProvider messageInfoProvider;

    @ExceptionHandler(NoExistingCommentsInPage.class)
    public String  noExistingCommentsInPageExHandle(NoExistingCommentsInPage exception, HttpServletResponse response){
        return "redirect:/donationPostComment/comments/lastPage";
    }

    @GetMapping("/donationPostComment/childComments")
    public ResponseEntity<Object> getChildComments(@RequestParam(name = "commentId")Long id){
        return new ResponseEntity<>(commentManager.getChildComments(id),HttpStatus.OK);
    }

    @GetMapping("/donationPostComment/comments")
    public ResponseEntity<Object> getComments(@Validated @ModelAttribute DonationPostCommentsRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest){
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
        DonationPostCommentsPageInfo commentsPageInfo = commentManager.getPostComments(request);
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null && httpSession.getAttribute(SessionKey.USER_AUTH.name()) != null){
            UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
            commentsPageInfo.constructCommentAcccessSet(userSummary.getEmail());
        }
        return new ResponseEntity<>(commentsPageInfo,HttpStatus.OK);
    }

    @GetMapping("/donationPostComment/comments/lastPage")
    public ResponseEntity<Object> getLastPageComments(@RequestParam(name = "postId")Long postId,HttpServletRequest httpServletRequest){
        DonationPostCommentsPageInfo commentsPageInfo = commentManager.getPostLastPageComments(postId);
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null && httpSession.getAttribute(SessionKey.USER_AUTH.name()) != null){
            UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
            commentsPageInfo.constructCommentAcccessSet(userSummary.getEmail());
        }
        return new ResponseEntity<>(commentsPageInfo,HttpStatus.OK);
    }

}
