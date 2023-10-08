package weShare.sharehappy.controller.my;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weShare.sharehappy.Exception.donation.*;
import weShare.sharehappy.Exception.post.CancleDonationsInPostException;
import weShare.sharehappy.Exception.post.DonationPostDeletionNotAllowedException;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.donation.DonationInfoSummary;
import weShare.sharehappy.dto.error.SimpleErrorResponse;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.donation.DonationManager;
import weShare.sharehappy.service.donationpost.DonationPostManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class MyController {

    private final DonationManager donationManager;
    private final DonationPostManager donationPostManager;
    private final MessageInfoProvider messageInfoProvider;

    @ExceptionHandler(NoMoreDonationInfoException.class)
    public ResponseEntity<Object> noMoreDonationInfoExHandle(NoMoreDonationInfoException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEqualsDonationDonorException.class)
    public ResponseEntity<Object> notEqualsDonationDonorExHandle(NotEqualsDonationDonorException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DonationNotPaidStatus.class)
    public ResponseEntity<Object> donationNotPaidStatusExHandle(DonationNotPaidStatus exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DonationStatusUpdateFailException.class)
    public ResponseEntity<Object> donationStatusUpdateFailExHandle(DonationStatusUpdateFailException exception){
        String message = "결제를 환불하는데 실패하였니다. 다시 시도해주세요";
        return new ResponseEntity<>(message,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CancleDonationFailException.class)
    public ResponseEntity<Object> cancleDonationFailExHandle(CancleDonationFailException exception){
        String message = "결제를 환불하는데 실패하였니다. 다시 시도해주세요";
        return new ResponseEntity<>(message,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RefundExpiredException.class)
    public ResponseEntity<Object> refundExpiredExHandle(RefundExpiredException exception){
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DonationPostDeletionNotAllowedException.class)
    public ResponseEntity<Object> donationPostDeletionNotAllowedExHandle(DonationPostDeletionNotAllowedException exception){
        String message =  exception.getPostDeleteCriteria().getDiscription();
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CancleDonationsInPostException.class)
    public ResponseEntity<Object> cancleDonationsInPostExHandle(CancleDonationsInPostException exception){
        List<String> cancleOrderIds= exception.getCancleOrderIds();
        /*
        실제로 환불된 상태의 주문 내역을 CANCLED 상태로 남기는 작업을 수행
        여기에서도 exception이 발생하면 로그를 남기기
         */
        try {
            donationManager.updateDonationsStatusInOrderIds(DonationStatus.CANCELLED,cancleOrderIds);
        }catch (DataAccessException e){
            //로그 남기기
        }
        String message = messageInfoProvider.getMessage(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new SimpleErrorResponse(message),HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/myPage")
    public String getMyPage(HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        if(userSummary.getIsOrganization()){
            return "/my/MyPageOrganization";
        }
        else{
            return "/my/MyPageDonor";
        }
    }

    @GetMapping("/myPage/myDonations")
    public ResponseEntity<Object> getMyDonations(@RequestParam(value = "page") Integer page,HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        List<DonationInfoSummary> donationInfoSummries = donationManager.getUserDonationInfo(page,userSummary.getEmail());
        return new ResponseEntity<>(donationInfoSummries, HttpStatus.OK);
    }

    @GetMapping("/myPage/myDonationPosts")
    public ResponseEntity<Object> getMyDonationPosts(@RequestParam(value = "page") Integer page,HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        List<DonationPostSummary> donationPostSummries = donationPostManager.getDonationPosts(page,userSummary.getEmail());
        return new ResponseEntity<>(donationPostSummries, HttpStatus.OK);
    }


    @DeleteMapping("/myPage/myDonations/cancle")
    public ResponseEntity<Object> getMyDonations(@RequestParam(value = "orderId")String orderId,HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        donationManager.canclePayment(orderId, userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/myPage/myPost/delete")
    public ResponseEntity<Object> deleteMyPost(@RequestParam(value = "postId")Long postId,HttpSession httpSession){
        UserSummary userSummary = (UserSummary) httpSession.getAttribute(SessionKey.USER_AUTH.name());
        donationPostManager.deletePost(postId,userSummary.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
