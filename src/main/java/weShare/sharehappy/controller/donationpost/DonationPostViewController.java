package weShare.sharehappy.controller.donationpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.post.DonationPostDetail;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.service.donationpost.DonationPostManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import java.util.List;

@Controller
@AllArgsConstructor
public class DonationPostViewController {

    private final DonationPostManager postManager;
    private final MessageInfoProvider messageInfoProvider;

    //기부 게시판 간단 정보 리스트 API
    @RequestMapping("/donationPost/donationPosts")
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

    @RequestMapping("/donationPost/{id}")
    public String getDonationPost(Model model, @PathVariable(name = "id")Long id){
        DonationPostDetail donationPostDetail = postManager.getDonation(id);

        return "";
    }

}
