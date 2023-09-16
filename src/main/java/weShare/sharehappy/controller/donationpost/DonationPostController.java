package weShare.sharehappy.controller.donationpost;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import weShare.sharehappy.Exception.ExceedImageCountException;
import weShare.sharehappy.Exception.FileStoreException;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.DonationPostImageUploadRequest;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.DonationPostInfoProvider;
import weShare.sharehappy.service.LocalFileManagementService;
import weShare.sharehappy.service.MessageInfoProvider;
import weShare.sharehappy.validation.validator.PostImageUploadRequestValidator;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;



@Controller
@RequestMapping("/donationPost")
@Slf4j
public class DonationPostController {

    private final DonationPostInfoProvider postInfoProvider;
    private final MessageInfoProvider messageInfoProvider;
    private final PostImageUploadRequestValidator validator;
    private final LocalFileManagementService localFileManagementService;
    @Value("${post.image.store}")
    private String postImageDirectory;

    @Autowired
    public DonationPostController(DonationPostInfoProvider postInfoProvider, MessageInfoProvider messageInfoProvider, PostImageUploadRequestValidator validator, LocalFileManagementService localFileManagementService){
        this.postInfoProvider = postInfoProvider;
        this.messageInfoProvider = messageInfoProvider;
        this.validator = validator;
        this.localFileManagementService = localFileManagementService;
    }


    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(validator);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> maxUploadSizeExceededtExHandle(MaxUploadSizeExceededException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ExceedImageCountException.class, SizeLimitExceededException.class})
    public ResponseEntity<Object> exceedImageCountExHandle(Exception exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStoreException.class)
    public ResponseEntity<Object> fileStoreExHandle(FileStoreException exception){
        String code[] = new String[]{exception.getClass().getSimpleName()};
        Object arguments[] = new Object[]{exception.getFileName()};
        String message = messageInfoProvider.getMessage(code,arguments);
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //기부 게시판 간단 정보 리스트 API
    @RequestMapping("/donationPosts")
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
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        List<DonationPostSummary> postSummaryList = postInfoProvider.getDonationPosts(request);
        return new ResponseEntity<>(postSummaryList,HttpStatus.OK);
    }

    @GetMapping("/make/form")
    public String getDonationPostMakeForm(HttpSession httpSession){
        httpSession.setAttribute("currentImageCount",Integer.valueOf(0));
        return "donationPost/PostMakeForm";
    }

    @PostMapping("/image/upload")
    public ResponseEntity<Object> uploadImage(@ModelAttribute DonationPostImageUploadRequest request,HttpSession httpSession) {

        return new ResponseEntity<Object>("https://sharehappy.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B6%801.jpg",HttpStatus.OK);
    }

}
