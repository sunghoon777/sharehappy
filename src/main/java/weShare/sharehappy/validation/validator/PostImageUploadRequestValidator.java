package weShare.sharehappy.validation.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import weShare.sharehappy.dto.post.DonationPostImageUploadRequest;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class PostImageUploadRequestValidator implements Validator {

    private HashSet<String> imageFileContentType = new HashSet<>(Arrays.asList("image/jpeg","image/png","image/gif","image/bmp","image/tiff","image/webp","image/svg+xml"));

    @Override
    public boolean supports(Class<?> clazz) {
        return DonationPostImageUploadRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DonationPostImageUploadRequest request = (DonationPostImageUploadRequest) target;
        boolean isEmpty = request.getImageFiles().stream().anyMatch(file -> file.isEmpty());
        if(isEmpty){
            errors.rejectValue("imageFiles","emptyFile");
        }
        boolean isNotSupportContentype = request.getImageFiles().stream().anyMatch(file -> !imageFileContentType.contains(file));
        if(isNotSupportContentype){
            errors.rejectValue("imageFiles","notSupportContentType");
        }
    }
}
