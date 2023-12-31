package weShare.sharehappy.validation.validator;


import org.springframework.stereotype.Component;
import weShare.sharehappy.dto.signup.DonorSignupRequest;
import weShare.sharehappy.validation.annotation.PasswordConfirm;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm,Object> {
    @Override
    public void initialize(PasswordConfirm constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        DonorSignupRequest signupRequest = (DonorSignupRequest)value;
        Optional<String> password = Optional.ofNullable(signupRequest.getPassword());
        Optional<String> passwordConfirm = Optional.ofNullable(signupRequest.getPasswordConfirm());
        if(password.isPresent() && passwordConfirm.isPresent() && password.equals(passwordConfirm)){
            return true;
        }
        return false;
    }
}
