package weShare.sharehappy.validation.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import weShare.sharehappy.dto.donation.DonationPrepareRequest;
import java.math.BigDecimal;

@Component
public class DonationPrepareReqValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DonationPrepareRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DonationPrepareRequest donationPrepareRequest = (DonationPrepareRequest)target;
        Long postId = donationPrepareRequest.getPostId();
        if(postId == null || postId <= 0){
            errors.rejectValue("postId","InValid");
        }
        BigDecimal amount = donationPrepareRequest.getAmount();
        //기부 금액은 최소 1000원에서 100만원까지만 설정하였음
        if(amount == null || amount.compareTo(new BigDecimal("1000")) < 0 || amount.compareTo(new BigDecimal("1000000")) > 0 || amount.scale() != 0){
            errors.rejectValue("amount","WrongAmount");
        }
    }
}
