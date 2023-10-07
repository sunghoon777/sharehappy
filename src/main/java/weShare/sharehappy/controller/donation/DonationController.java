package weShare.sharehappy.controller.donation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import weShare.sharehappy.Exception.donation.*;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.donation.DonationPrepareRequest;
import weShare.sharehappy.dto.donation.DonationPrepareResponse;
import weShare.sharehappy.dto.donation.DonationVerifyRequest;
import weShare.sharehappy.dto.error.ApiValidationErrorResponse;
import weShare.sharehappy.dto.error.FieldErrorInfo;
import weShare.sharehappy.dto.error.RejectValueInfo;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.donation.DonationManager;
import weShare.sharehappy.service.donationpost.DonationPostManager;
import weShare.sharehappy.service.message.MessageInfoProvider;
import weShare.sharehappy.validation.validator.DonationPrepareReqValidator;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class DonationController{

    private final DonationPostManager postManager;
    private final DonationManager donationManager;
    private final MessageInfoProvider messageInfoProvider;
    private final DonationPrepareReqValidator donationPrepareReqValidator;

    @ExceptionHandler(StoreDonationInfoFailException.class)
    public ResponseEntity<Object> storeFailDonationInfoExHandle(StoreDonationInfoFailException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PrepareDonationFailException.class)
    public ResponseEntity<Object> requestFailPrepareDonationExHandle(PrepareDonationFailException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DonationStatusUpdateFailException.class)
    public ResponseEntity<Object> donationStatusUpdateFailExHandle(DonationStatusUpdateFailException exception){
        //결제를 실패하여 데이터베이스 상태가 FAILED로 갱신되는데 실패한 상황, 결제 취소는 된 상태임(환불은 한 상태)
        if(exception.getUpdateTryStatus() == DonationStatus.FAILED){
            String message = "결제를 실패했습니다.";
            return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //검증 후 올바른 결제인 것도 확인 데이터베이스 상태가 PAID로 갱신되는 데 실패한 상황, 결제 취소는 된 상태임(한불은 한 상태)
        else if(exception.getUpdateTryStatus() == DonationStatus.PAID){
            String message = "결제를 실패했습니다.";
            return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            String message = "결제를 실패했습니다.";
            return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(VerifyDonationFailException.class)
    public ResponseEntity<Object> getVerifyFailExHandle(VerifyDonationFailException exception){
        //검증 시도했지만 실패, 결제 취소는 된 상태임(환불은 한 상태)
        String message = "결제를 실패했습니다.";
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CancleDonationFailException.class)
    public ResponseEntity<Object> cancleFailExHandle(CancleDonationFailException exception){
        //검증 시도했지만 실패, 결제 취소조차 못한 상태(환불이 되지 않을 수도 있음)
        String message = "결제를 실패했습니다, 만약 환불이 되지 않았으면 사이트 공식 이메일로 문의를 해주세요";
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/donation/form")
    public String getDonation(@RequestParam Long postId, Model model, HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        model.addAttribute("userSummary",userSummary);
        DonationPostSummary donationPostSummary = postManager.getDonationSummary(postId);
        model.addAttribute("donationPostSummary",donationPostSummary);
        return "/donation/DonateForm";
    }

    @PostMapping("/donation/prepare")
    public ResponseEntity<Object> prepareDonation(@RequestBody DonationPrepareRequest request, BindingResult bindingResult, HttpSession httpSession){
        donationPrepareReqValidator.validate(request,bindingResult);
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
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        DonationPrepareResponse response = donationManager.prepareDonation(request, userSummary.getEmail());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/donation/verify")
    public ResponseEntity<Object> verifyDonation(@RequestBody DonationVerifyRequest request, BindingResult bindingResult){
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
        donationManager.verifyDonation(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
