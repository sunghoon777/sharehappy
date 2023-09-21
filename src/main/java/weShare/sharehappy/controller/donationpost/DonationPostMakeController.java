package weShare.sharehappy.controller.donationpost;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.Exception.ExceedImageCountException;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.*;
import weShare.sharehappy.dto.postCategory.DonationPostCategoryInfo;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.donationpostcategory.DonationPostCategoryInfoProvider;
import weShare.sharehappy.service.donationpost.DonationPostManager;
import weShare.sharehappy.service.file.LocalFileManagementService;
import weShare.sharehappy.service.message.MessageInfoProvider;
import weShare.sharehappy.validation.validator.DonationPostMakeReqValidator;
import weShare.sharehappy.validation.validator.PostImageReqValidator;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class DonationPostMakeController {

    private final MessageInfoProvider messageInfoProvider;
    private final LocalFileManagementService localFileManagementService;
    private final PostImageReqValidator postImageReqValidator;
    private final DonationPostCategoryInfoProvider categoryInfoProvider;
    private final DonationPostMakeReqValidator donationPostMakeReqValidator;
    private final DonationPostManager donationPostManager;
    @Value("${temp.post.image.store}")
    private String imageStoreClassPath; //클래스 패스 저장 경로
    private String imageStoreRealPath; //실제 저장 경로
    private String userImageRequestPath;//사용자 요청 저장 경로

    @PostConstruct
    public void init() throws IOException {
        Resource postImageClassPath = new ClassPathResource(imageStoreClassPath);
        imageStoreRealPath = postImageClassPath.getFile().getAbsolutePath();
        userImageRequestPath = imageStoreClassPath.substring(imageStoreClassPath.lastIndexOf("/temp"));
    }

    @Autowired
    public DonationPostMakeController(MessageInfoProvider messageInfoProvider,
                                      LocalFileManagementService localFileManagementService, PostImageReqValidator postImageReqValidator,
                                      DonationPostCategoryInfoProvider categoryInfoProvider, DonationPostMakeReqValidator donationPostMakeReqValidator,
                                      DonationPostManager donationPostManager){
        this.messageInfoProvider = messageInfoProvider;
        this.localFileManagementService = localFileManagementService;
        this.postImageReqValidator = postImageReqValidator;
        this.categoryInfoProvider = categoryInfoProvider;
        this.donationPostMakeReqValidator = donationPostMakeReqValidator;
        this.donationPostManager = donationPostManager;
    }

    @InitBinder("donationPostImageUploadRequest")
    public void initImageUpload(WebDataBinder binder){
        binder.addValidators(postImageReqValidator);
    }

    @InitBinder("donationPostMakeRequest")
    public void initPostMake(WebDataBinder binder){
        binder.addValidators(donationPostMakeReqValidator);
    }

    @ExceptionHandler(ExceedImageCountException.class)
    public ResponseEntity<Object> exceedImageCountExHandle(Exception exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/donationPost/make/form")
    public String getDonationPostMakeForm(Model model, HttpSession httpSession){
        //임시 저장소에 있는 파일들 삭제
        String email = ((UserSummary)httpSession.getAttribute(SessionKey.USER_AUTH.name())).getEmail();
        String directory = localFileManagementService.mkdir(imageStoreRealPath,email);
        localFileManagementService.cleanDirectory(directory);
        List<DonationPostCategoryInfo> categoryInfoList= categoryInfoProvider.getAllCategory();
        categoryInfoList.remove(0);
        model.addAttribute("categoryList",categoryInfoList);
        return "donationPost/PostMakeForm";
    }

    @PostMapping("/donationPost/temp/image/upload")
    public ResponseEntity<Object> uploadTempImage(@Validated @ModelAttribute("donationPostImageUploadRequest") DonationPostImageUploadRequest request, BindingResult bindingResult, HttpSession httpSession) throws IOException {
        String email = ((UserSummary)httpSession.getAttribute(SessionKey.USER_AUTH.name())).getEmail();
        String directory = localFileManagementService.mkdir(imageStoreRealPath,email); // 사용자의 저장 경로는 이미지파일저장경로/이메일 에 저장됨
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
        MultipartFile multipartFile = request.getImageFile();
        String fileName = localFileManagementService.storeFile(multipartFile.getInputStream(),directory,multipartFile.getOriginalFilename());
        String imageUri = userImageRequestPath+"/"+email+"/"+fileName;
        return new ResponseEntity<>(new DonationPostImageUploadResponse(imageUri),HttpStatus.OK);
    }

    @PostMapping("/donationPost/make/post")
    public ResponseEntity<Object> makePost(@Validated @ModelAttribute("donationPostMakeRequest") DonationPostMakeRequest request,BindingResult bindingResult,HttpSession httpSession){
        if(bindingResult.hasErrors()){
            ApiValidationErrorResponse response = new ApiValidationErrorResponse();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                String fieldName = fieldError.getField();
                String message = messageInfoProvider.getMessage(fieldError.getCodes(),fieldError.getArguments(),"");
                response.addFieldErrorInfo(new FieldErrorInfo(fieldName,message));
            }
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        String email = ((UserSummary)httpSession.getAttribute(SessionKey.USER_AUTH.name())).getEmail();
        String directory = localFileManagementService.mkdir(imageStoreRealPath,email);
        String content = request.getContent();
        List<File> fileList = localFileManagementService.getDirectoryFiles(directory).stream().filter(file -> content.contains(file.getName())).collect(Collectors.toList());
        //모금함 만들기
        donationPostManager.makePost(request,fileList,email);
        //로컬 이미지 파일 삭제
        localFileManagementService.cleanDirectory(directory);
        return ResponseEntity.ok().build();
    }


}
