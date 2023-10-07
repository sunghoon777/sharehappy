package weShare.sharehappy.controller.my;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weShare.sharehappy.constant.SessionKey;
import weShare.sharehappy.dto.donation.DonationInfoSummary;
import weShare.sharehappy.dto.user.UserSummary;
import weShare.sharehappy.service.donation.DonationManager;
import weShare.sharehappy.service.message.MessageInfoProvider;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class MyController {

    private final DonationManager donationManager;
    private final MessageInfoProvider messageInfoProvider;

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


}
