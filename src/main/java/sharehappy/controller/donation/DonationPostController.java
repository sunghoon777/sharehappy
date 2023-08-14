package sharehappy.controller.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sharehappy.dto.DonationPostListRequest;
import sharehappy.dto.ErrorResponse;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/donationPost")
public class DonationPostController {

    @Autowired
    private MessageSource errorMessageSource;

    @GetMapping("/list")
    public ResponseEntity<Object> listDonationPost(DonationPostListRequest request, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessageSource.getMessage("DefaultBadRequestErrorMessage",null, Locale.KOREA)));
        }
        return ResponseEntity.ok().build();
    }

}
