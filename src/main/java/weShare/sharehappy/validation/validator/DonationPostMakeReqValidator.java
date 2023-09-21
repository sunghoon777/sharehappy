package weShare.sharehappy.validation.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.dto.post.DonationPostMakeRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DonationPostMakeReqValidator implements Validator {
    private HashSet<String> imageFileContentType = new HashSet<>(Arrays.asList("image/jpeg","image/png","image/gif","image/bmp","image/tiff","image/webp","image/svg+xml"));

    @Override
    public boolean supports(Class<?> clazz) {
        return DonationPostMakeRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DonationPostMakeRequest request = (DonationPostMakeRequest) target;
        LocalDate localDate = request.getEndDate();
        if(localDate == null || !localDate.isAfter(LocalDate.now())){
            errors.rejectValue("endDate","invalidDate");
        }
        MultipartFile multipartFile = request.getThumbnail();
        if(multipartFile == null){
            errors.rejectValue("thumbnail","NotNull");
            return;
        }
        if(multipartFile.isEmpty()){
            errors.rejectValue("thumbnail","emptyFile");
        }
        if(!imageFileContentType.contains(multipartFile.getContentType())){
            errors.rejectValue("thumbnail","notSupportContentType");
        }
    }
}
