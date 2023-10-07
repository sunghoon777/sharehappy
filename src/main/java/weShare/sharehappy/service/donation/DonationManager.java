package weShare.sharehappy.service.donation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weShare.sharehappy.Exception.donation.*;
import weShare.sharehappy.Exception.post.NoExistingDonationPost;
import weShare.sharehappy.Exception.user.NoExistingUserException;
import weShare.sharehappy.constant.DonationStatus;
import weShare.sharehappy.constant.PageSize;
import weShare.sharehappy.dao.DonationInfoRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dao.DonorRepository;
import weShare.sharehappy.dto.donation.DonationInfoSummary;
import weShare.sharehappy.dto.donation.DonationPrepareRequest;
import weShare.sharehappy.dto.donation.DonationPrepareResponse;
import weShare.sharehappy.dto.donation.DonationVerifyRequest;
import weShare.sharehappy.entity.DonationInfo;
import weShare.sharehappy.entity.DonationPost;
import weShare.sharehappy.entity.Donor;
import weShare.sharehappy.service.random.RandomStringService;
import weShare.sharehappy.vo.DonationKeySettingInfo;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationManager {
    private final DonationInfoRepository donationInfoRepository;
    private final DonationPostRepository donationPostRepository;
    private final DonorRepository donorRepository;
    private final RandomStringService randomStringService;
    private DonationKeySettingInfo keyInfo;

    @Autowired
    public DonationManager(DonationInfoRepository donationInfoRepository, DonationPostRepository donationPostRepository
            , DonorRepository donorRepository, RandomStringService randomStringService){
        this.donationInfoRepository = donationInfoRepository;
        this.donationPostRepository = donationPostRepository;
        this.donorRepository = donorRepository;
        this.randomStringService = randomStringService;
    }

    @PostConstruct
    public void init() throws IOException {
        Resource resource = new ClassPathResource("setting/donationKey.setting");
        ObjectMapper objectMapper = new ObjectMapper();
        keyInfo = objectMapper.readValue(resource.getFile(), DonationKeySettingInfo.class);
    }

    public List<DonationInfoSummary> getUserDonationInfo(int page,String email){
        Donor donor = donorRepository.findByEmail(email).orElseThrow(() -> new NoExistingUserException());
        List<DonationStatus> list = Arrays.asList(DonationStatus.PAID,DonationStatus.CANCELLED);
        int pageSize = PageSize.MY_DONATION_LIST_SIZE.getSize();
        List<DonationInfo> donationInfos =  donationInfoRepository.findAllByUserIdAndDonationStatusWithDonationPost(page,pageSize,donor.getId(),list);
        List<Long> postIds = donationInfos.stream().map(d -> d.getDonationPost().getId()).collect(Collectors.toList());
        List<String> organizationNames = donationPostRepository.getOrganizationNamesByPostIds(postIds);
        List<DonationInfoSummary> donationInfoSummaries = new ArrayList<>();
        for(int i=0;i<donationInfos.size();i++){
            DonationInfoSummary donationInfoSummary = donationInfos.get(i).changeToDonationInfoSummary(organizationNames.get(i));
            donationInfoSummaries.add(donationInfoSummary);
        }
        return donationInfoSummaries;
    }

    @Transactional
    public DonationPrepareResponse prepareDonation(DonationPrepareRequest request, String email){
        DonationPost donationPost = donationPostRepository.findById(request.getPostId()).orElseThrow(() -> new NoExistingDonationPost());
        Donor donor = donorRepository.findByEmail(email).orElseThrow(() -> new NoExistingUserException());
        //주문 번호 생성, 만약 DB안에 주문정보 있으면 다시 주문번호 생성, 극히 드물게 2,3번은 실행될듯함
        String donationOrderId = "";
        Optional<DonationInfo> temp;
        do{
            donationOrderId = randomStringService.getRandomDonationOrderId(request.getPostId(),donor.getId());
            temp = donationInfoRepository.findByOrderId(donationOrderId);
        }while(temp.isPresent());
        //결제 정보를 DB에 저장함
        DonationInfo donationInfo = new DonationInfo(donationOrderId,request.getAmount(), LocalDateTime.now(), DonationStatus.READY,donationPost,donor);
        try {
            donationInfoRepository.save(donationInfo);
            //이 catch문 안에서 insert문이 실행되게 flush하였음 만약 나중에 insert 오류가 생기면 DataAccessException을 StoreDonationInfoFailException로 변환하지 못하기 떄문ㄴ
            donationInfoRepository.flush();
        }catch (DataAccessException dataAccessException){
            throw new StoreDonationInfoFailException(email,donationInfo.getDonationRequestDate(), donationPost.getId(),donationOrderId);
        }
        //아이엠포트 클라이언트 생성
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        PrepareData prepareData = new PrepareData(donationOrderId,request.getAmount());
        try {
            //결제 정보 사전 등록, 사전 등록 한 것은 이후 클라이언트 js로 실제로 결제할 때 사전 등록 금액과 실제 결제 금액을 대조해본다. 일치안할시 결제창도 안띄어지게 만듬
            client.postPrepare(prepareData);
        } catch (IOException e) {
            throw new PrepareDonationFailException(email,donationInfo.getDonationRequestDate(), donationPost.getId(),donationOrderId);
        } catch (IamportResponseException e) {
            throw new PrepareDonationFailException(email,donationInfo.getDonationRequestDate(), donationPost.getId(),donationOrderId,e.getHttpStatusCode());
        }
        return new DonationPrepareResponse(donationPost.getTitle(),donationPost.getOrganization().getName(),
                donor.getNickname(),donor.getEmail(),donationOrderId,prepareData.getAmount());

    }

    @Transactional
    public void verifyDonation(DonationVerifyRequest request){
        DonationInfo donationInfo = donationInfoRepository.findByOrderIdWithDonorAndDonationPost(request.getOrderId()).orElseThrow(()-> new NoExistingDonationInfoException());
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        try {
            //pg사에 client에서 얻어온 pg전용주문정보를 통해 결제 정보 요청, 이후 검증
            IamportResponse<Payment> response = client.paymentByImpUid(request.getPgOrderId());
            Payment payment = response.getResponse();
            //결제 성공, 상태 변경 후 바로 flush로 DB에 반영
            if(payment.getMerchantUid().equals(donationInfo.getOrderId()) && payment.getAmount().compareTo(payment.getAmount()) == 0){
                try {
                    donationInfo.orderConfirm(payment.getImpUid(),LocalDateTime.now());
                    donationInfoRepository.flush();
                }catch (DataAccessException e){
                    //상태 갱신 실패시 fail pay 로직 실행
                    failPayment(request.getPgOrderId(),donationInfo);
                    throw new DonationStatusUpdateFailException(donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                            donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),DonationStatus.PAID);
                }
            }
            //잘못된 결제이면 fail pay 로직 실행
            else{
                failPayment(request.getPgOrderId(),donationInfo);
            }
        } catch (IamportResponseException e) {
            int statusCode = e.getHttpStatusCode();
            //status Code가 500일 때에만 pg사 서버 내부 문제라 fail pay로직 실행해서 결제 취소해야함 그 외에는 아예 pgOrderId 자체가 잘못된것
            if(statusCode == 500){
                failPayment(request.getPgOrderId(),donationInfo);
            }
            throw new VerifyDonationFailException(request.getPgOrderId(),donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),e.getHttpStatusCode());
        } catch (IOException e) {
            //io Exception이 일어나면 fail pay 로직 실행해서 결제 취소했음, pgOrderId가 잘못됬는지 아님 다른 내부적인 문제인지는 명확히는 알 수 없기 때문에 결제 취소는 함
            failPayment(request.getPgOrderId(),donationInfo);
            throw new VerifyDonationFailException(request.getPgOrderId(),donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId());
        }
    }

    @Transactional
    public void failPayment(String pgOrderId, DonationInfo donationInfo){
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        try {
            //pg사에도 결제 취소 요청
            client.cancelPaymentByImpUid(new CancelData(pgOrderId,true));
        } catch (IamportResponseException e) {
            throw new CancleDonationFailException(pgOrderId,donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),e.getHttpStatusCode());
        } catch (IOException e) {
            throw new CancleDonationFailException(pgOrderId,donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId());
        }
        try {
            //결제 실패 상태 갱신
            donationInfo.orderFail(pgOrderId,LocalDateTime.now());
            donationInfoRepository.flush();
        }catch (DataAccessException e){
            throw new DonationStatusUpdateFailException(donationInfo.getDonor().getEmail(),donationInfo.getDonationRequestDate(),
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),DonationStatus.FAILED);
        }
    }



}
