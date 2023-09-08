package weShare.sharehappy.controller.donationpost;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.service.DonationPostInfoProvider;
import weShare.sharehappy.service.MessageInfoProvider;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/donationPost")
public class DonationPostController {

    private final DonationPostInfoProvider postInfoProvider;
    private final MessageInfoProvider messageInfoProvider;

    //기부 게시판 간단 정보 리스트 API
    @RequestMapping(value = "/donationPosts")
    public ResponseEntity<Object> getDonationPosts(@Validated @ModelAttribute DonationPostSummaryRequest request, BindingResult bindingResult){
        //검증 오류 있으면
        if(bindingResult.hasErrors()){
            ApiValidationErrorResponse response = new ApiValidationErrorResponse();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                String fieldName = fieldError.getField();
                String message = messageInfoProvider.getMessage(fieldError.getCodes(),fieldError.getArguments(),"");
                Object rejectValue = fieldError.getRejectedValue();
                response.addFieldErrorInfo(new FieldErrorInfo(fieldName,message));
                response.addRejectValueInfo(new RejectValueInfo(fieldName,rejectValue));
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        List<DonationPostSummary> postSummaryList = postInfoProvider.getDonationPosts(request);
        return new ResponseEntity<>(postSummaryList,HttpStatus.OK);
    }

}
