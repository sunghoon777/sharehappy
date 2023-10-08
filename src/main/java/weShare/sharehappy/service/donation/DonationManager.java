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
import weShare.sharehappy.Exception.post.CancleDonationsInPostException;
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
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.Duration;
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

    @Transactional
    public List<DonationInfoSummary> getUserDonationInfo(int page,String email){
        Donor donor = donorRepository.findByEmail(email).orElseThrow(() -> new NoExistingUserException());
        List<DonationStatus> list = Arrays.asList(DonationStatus.PAID,DonationStatus.CANCELLED);
        int pageSize = PageSize.MY_DONATION_LIST_SIZE.getSize();
        List<DonationInfo> donationInfos =  donationInfoRepository.findAllByUserIdAndDonationStatusWithDonationPost(page,pageSize,donor.getId(),list);
        if(donationInfos.size() == 0){
            throw new NoMoreDonationInfoException();
        }
        List<Long> postIds = donationInfos.stream().map(d -> d.getDonationPost().getId()).collect(Collectors.toList());
        donationPostRepository.getOrganizationNamesByPostIds(postIds);
        List<DonationInfoSummary> donationInfoSummaries = donationInfos.stream()
                .map(d -> d.changeToDonationInfoSummary(d.getDonationPost().getOrganization().getName())).collect(Collectors.toList());
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
        LocalDateTime requestDate = LocalDateTime.now();
        DonationInfo donationInfo = new DonationInfo(donationOrderId,request.getAmount(), requestDate, DonationStatus.READY,donationPost,donor);
        try {
            donationInfoRepository.save(donationInfo);
        }catch (DataAccessException dataAccessException){
            throw new StoreDonationInfoFailException(email,requestDate, donationPost.getId(),donationOrderId);
        }
        //아이엠포트 클라이언트 생성
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        PrepareData prepareData = new PrepareData(donationOrderId,request.getAmount());
        try {
            //결제 정보 사전 등록, 사전 등록 한 것은 이후 클라이언트 js로 실제로 결제할 때 사전 등록 금액과 실제 결제 금액을 대조해본다. 일치안할시 결제창도 안띄어지게 만듬
            client.postPrepare(prepareData);
        } catch (IOException e) {
            throw new PrepareDonationFailException(email,requestDate, donationPost.getId(),donationOrderId);
        } catch (IamportResponseException e) {
            throw new PrepareDonationFailException(email,requestDate, donationPost.getId(),donationOrderId,e.getHttpStatusCode());
        }
        return new DonationPrepareResponse(donationPost.getTitle(),donationPost.getOrganization().getName(),
                donor.getNickname(),donor.getEmail(),donationOrderId,prepareData.getAmount());

    }

    @Transactional
    public void verifyDonation(DonationVerifyRequest request){
        DonationInfo donationInfo = donationInfoRepository.findByOrderIdWithDonorAndDonationPost(request.getOrderId()).orElseThrow(()-> new NoExistingDonationInfoException());
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        LocalDateTime statusUpdateDate = LocalDateTime.now();
        try {
            //pg사에 client에서 얻어온 pg전용주문정보를 통해 결제 정보 요청, 이후 검증
            IamportResponse<Payment> response = client.paymentByImpUid(request.getPgOrderId());
            Payment payment = response.getResponse();
            //결제 성공, 상태 변경 후 바로 flush로 DB에 반영
            if(payment.getMerchantUid().equals(donationInfo.getOrderId()) && payment.getAmount().compareTo(payment.getAmount()) == 0){
                try {
                    donationInfo.orderConfirm(payment.getImpUid(),statusUpdateDate);
                    donationInfo.getDonationPost().plusCurrentAmount(donationInfo.getAmount());
                }catch (DataAccessException e){
                    //상태 갱신 실패시 fail pay 로직 실행
                    failPayment(request.getPgOrderId(),donationInfo);
                    throw new DonationStatusUpdateFailException(donationInfo.getDonor().getEmail(),statusUpdateDate,
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
            throw new VerifyDonationFailException(request.getPgOrderId(),donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),e.getHttpStatusCode());
        } catch (IOException e) {
            //io Exception이 일어나면 fail pay 로직 실행해서 결제 취소했음, pgOrderId가 잘못됬는지 아님 다른 내부적인 문제인지는 명확히는 알 수 없기 때문에 결제 취소는 함
            failPayment(request.getPgOrderId(),donationInfo);
            throw new VerifyDonationFailException(request.getPgOrderId(),donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId());
        }
    }

    @Transactional
    public void failPayment(String pgOrderId, DonationInfo donationInfo){
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        LocalDateTime statusUpdateDate = LocalDateTime.now();
        try {
            //pg사에도 결제 취소 요청
            client.cancelPaymentByImpUid(new CancelData(pgOrderId,true));
        } catch (IamportResponseException e) {
            throw new CancleDonationFailException(pgOrderId,donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),e.getHttpStatusCode());
        } catch (IOException e) {
            throw new CancleDonationFailException(pgOrderId,donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId());
        }
        try {
            //결제 실패 상태 갱신
            donationInfo.orderFail(pgOrderId,statusUpdateDate);
        }catch (DataAccessException e){
            throw new DonationStatusUpdateFailException(donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),DonationStatus.FAILED);
        }
    }

    @Transactional
    public void canclePayment(String orderId,String email){
        DonationInfo donationInfo = donationInfoRepository.findByOrderIdWithDonorAndDonationPost(orderId).orElseThrow(()->new NoExistingDonationInfoException());
        if(!donationInfo.getDonor().getEmail().equals(email)){
            throw new NotEqualsDonationDonorException();
        }
        if(donationInfo.getDonationStatus() != DonationStatus.PAID){
            throw new DonationNotPaidStatus();
        }
        //환불은 기부 결제일 이후 7일 이내에만 가능함
        LocalDateTime statusUpdateDate = LocalDateTime.now();
        Duration duration = Duration.between(statusUpdateDate,donationInfo.getDonationCompleteDate());
        if(Math.abs(duration.toDays()) > 7){
            throw new RefundExpiredException();
        }
        //결제 취소 상태 갱신
        try {
            donationInfo.orderCancle(statusUpdateDate);
            donationInfo.getDonationPost().minusCurrentAmount(donationInfo.getAmount());
        }catch (DataAccessException exception){
            throw new DonationStatusUpdateFailException(email,statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),DonationStatus.CANCELLED);
        }
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        try {
            //pg사에도 결제 취소 요청
            client.cancelPaymentByImpUid(new CancelData(donationInfo.getPgOrderId(),true));
        } catch (IamportResponseException e) {
            throw new CancleDonationFailException(donationInfo.getPgOrderId(),donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId(),e.getHttpStatusCode());
        } catch (IOException e) {
            throw new CancleDonationFailException(donationInfo.getPgOrderId(),donationInfo.getDonor().getEmail(),statusUpdateDate,
                    donationInfo.getDonationPost().getId(),donationInfo.getOrderId());
        }
    }

    @Transactional
    public void cancleDonationsInPost(Long postId){
        List<DonationInfo> donationInfos = donationInfoRepository.findAllByPostIdAndDonationStatusWithDonorAndDonationPost(postId,DonationStatus.PAID);
        IamportClient client = new IamportClient(keyInfo.getAccessKey(),keyInfo.getSecretKey());
        int i = 0;
        //결제 상태 한 번에 업데이트
        donationInfoRepository.updateDonationStatusByPostIdAndDonationStatus(DonationStatus.CANCELLED,postId,DonationStatus.PAID);
        //pg사에 주문번호로 환불 요청하기
        try {
            for(i=0;i<donationInfos.size();i++){
                DonationInfo donationInfo = donationInfos.get(i);
                donationInfo.getDonationPost().minusCurrentAmount(donationInfo.getAmount());
                client.cancelPaymentByImpUid(new CancelData(donationInfo.getPgOrderId(),true));
            }
        }
        /*
        만약 여러 기부 내역에 대해서 pg사에 환불 요청 도중에 exception이 발생한다고 가정하면
        이미 pg사에 환불 요청된 기부 내역들은 실제로는 환불된 상태더라도 트랜잭션 rollback으로 인해 PAID 상태로 남게 된다
        그래서 해당 실제로 환불된 상태의 주문 내역을 exceptionHandler에서 나중에 CANCLED 상태로 남기기 위하여 실제 환불된 기부 내역의 주문번호 리스트를 exception 필드안에 저장한다.
         */
        catch (IamportResponseException e){
            String cancleFailedOrderId = donationInfos.get(i).getOrderId();
            List<String> cancleOrderIds = donationInfos.subList(0,i).stream().map(d -> d.getOrderId()).collect(Collectors.toList());
            throw new CancleDonationsInPostException(postId,LocalDateTime.now(),cancleFailedOrderId,cancleOrderIds);
        }catch (IOException e){
            String cancleFailedOrderId = donationInfos.get(i).getOrderId();
            List<String> cancleOrderIds = donationInfos.subList(0,i).stream().map(d -> d.getOrderId()).collect(Collectors.toList());
            throw new CancleDonationsInPostException(postId,LocalDateTime.now(),cancleFailedOrderId,cancleOrderIds);
        }
    }

    @Transactional
    public void updateDonationsStatusInOrderIds(DonationStatus donationStatus,List<String> orderIds){
        donationInfoRepository.updateDonationsStatusInOrderIds(donationStatus,orderIds);
    }


}
