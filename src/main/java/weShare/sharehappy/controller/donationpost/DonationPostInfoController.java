package weShare.sharehappy.controller.donationpost;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.post.NoExistingDonationPost;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.comment.DonationPostCommentsPageInfo;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.DonationPostDetail;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.comment.DonationPostCommentManager;
import weShare.sharehappy.service.donationpost.DonationPostManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class DonationPostInfoController {

    private final DonationPostManager postManager;
    private final DonationPostCommentManager commentManager;
    private final MessageInfoProvider messageInfoProvider;

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> numberFormatExceptionExHandle(HttpServletRequest request){
        String message = messageInfoProvider.getMessage(NoExistingDonationPost.class.getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //기부 게시판 간단 정보 리스트 API
    @GetMapping("/donationPost/donationPosts")
    public ResponseEntity<Object> getDonationPosts(@Validated @ModelAttribute DonationPostSummaryRequest request, BindingResult bindingResult){
        //검증 오류
        if(bindingResult.hasErrors()){
            ApiValidationErrorResponse response = new ApiValidationErrorResponse();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                String fieldName = fieldError.getField();
                String message = messageInfoProvider.getMessage(fieldError.getCodes(),fieldError.getArguments(),"");
                Object rejectValue = fieldError.getRejectedValue();
                response.addFieldErrorInfo(new FieldErrorInfo(fieldName,message));
                response.addRejectValueInfo(new RejectValueInfo(fieldName,rejectValue));
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        List<DonationPostSummary> postSummaryList = postManager.getDonationPosts(request);
        return new ResponseEntity<>(postSummaryList,HttpStatus.OK);
    }

    @GetMapping("/donationPost/{id}")
    public String getDonationPost(Model model, @PathVariable(name = "id")Long id,HttpServletRequest httpServletRequest){
        DonationPostDetail donationPostDetail = postManager.getDonation(id);
        DonationPostCommentsPageInfo commentsPageInfo = commentManager.getPostLastPageComments(id);
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null && httpSession.getAttribute(SessionKey.USER_AUTH.name()) != null){
            UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
            model.addAttribute("userSummary",userSummary);
            commentsPageInfo.constructCommentAcccessSet(userSummary.getEmail());
        }
        model.addAttribute("donationPostDetail",donationPostDetail);
        model.addAttribute("commentsPageInfo",commentsPageInfo);
        return "donationPost/PostView";
    }

}
