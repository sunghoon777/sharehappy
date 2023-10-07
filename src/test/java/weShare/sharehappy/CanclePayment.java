package weShare.sharehappy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import weShare.sharehappy.vo.DonationKeySettingInfo;

import java.io.IOException;

public class CanclePayment {

    @Test
    public void 결제취소용메서드() throws IOException, IamportResponseException {
        Resource resource = new ClassPathResource("setting/donationKey.setting");
        ObjectMapper objectMapper = new ObjectMapper();
        DonationKeySettingInfo keyInfo = objectMapper.readValue(resource.getFile(), DonationKeySettingInfo.class);

    }
}
