package weShare.sharehappy.controller.donationpost;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.Exception.ExceedImageCountException;
import weShare.sharehappy.Exception.FileStoreException;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.DonationPostImageUploadRequest;
import weShare.sharehappy.dto.post.DonationPostImageUploadResponse;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.DonationPostInfoProvider;
import weShare.sharehappy.service.LocalFileManagementService;
import weShare.sharehappy.service.MessageInfoProvider;
import weShare.sharehappy.validation.validator.PostImageReqValidator;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/donationPost")
@Slf4j
public class DonationPostController {

    private final DonationPostInfoProvider postInfoProvider;
    private final MessageInfoProvider messageInfoProvider;
    private final LocalFileManagementService localFileManagementService;
    private final PostImageReqValidator postImageReqValidator;
    @Value("${temp.post.image.store}")
    private String imageStoreClassPath;
    private String imageStoreRealPath;
    private String userImageRequestPath;

    @PostConstruct
    public void init() throws IOException {
        Resource postImageClassPath = new ClassPathResource(imageStoreClassPath);
        imageStoreRealPath = postImageClassPath.getFile().getAbsolutePath();
        userImageRequestPath = imageStoreClassPath.substring(imageStoreClassPath.lastIndexOf("/temp"));
    }

    @Autowired
    public DonationPostController(DonationPostInfoProvider postInfoProvider, MessageInfoProvider messageInfoProvider,
                                  LocalFileManagementService localFileManagementService,PostImageReqValidator postImageReqValidator){
        this.postInfoProvider = postInfoProvider;
        this.messageInfoProvider = messageInfoProvider;
        this.localFileManagementService = localFileManagementService;
        this.postImageReqValidator = postImageReqValidator;
    }

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(postImageReqValidator);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> maxUploadSizeExceededtExHandle(MaxUploadSizeExceededException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ExceedImageCountException.class)
    public ResponseEntity<Object> exceedImageCountExHandle(Exception exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStoreException.class)
    public ResponseEntity<Object> fileStoreExHandle(FileStoreException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
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

        return "donationPost/PostMakeForm";
    }

    @PostMapping("/image/upload")
    public ResponseEntity<Object> uploadImage(@Validated @ModelAttribute DonationPostImageUploadRequest request, BindingResult bindingResult, HttpSession httpSession) throws IOException {
        String email = ((UserSummary)httpSession.getAttribute(SessionKey.USER_AUTH.name())).getEmail();
        String directory = localFileManagementService.mkdir(imageStoreRealPath,email);
        int imageCount = localFileManagementService.countDirectoryFiles(directory);
        if(imageCount+1 > 20){
            throw new ExceedImageCountException();
        }
        if(bindingResult.hasErrors()){
            ApiValidationErrorResponse response = new ApiValidationErrorResponse();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                String fieldName = fieldError.getField();
                String message = messageInfoProvider.getMessage(fieldError.getCodes(),fieldError.getArguments(),"");
                response.addFieldErrorInfo(new FieldErrorInfo(fieldName,message));
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        String fileName = localFileManagementService.storeRandomNameFile(request.getImageFile(),directory);
        String imageUri = userImageRequestPath+"/"+email+"/"+fileName;
        return new ResponseEntity<>(new DonationPostImageUploadResponse(imageUri),HttpStatus.OK);
    }

}
