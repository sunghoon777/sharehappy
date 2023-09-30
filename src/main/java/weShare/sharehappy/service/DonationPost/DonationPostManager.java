package weShare.sharehappy.service.donationpost;


import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import weShare.sharehappy.Exception.*;
import weShare.sharehappy.constant.PageSize;
import weShare.sharehappy.dao.DonationPostCategoryRepository;
import weShare.sharehappy.dao.DonationPostImageRepository;
import weShare.sharehappy.dao.DonationPostRepository;
import weShare.sharehappy.dao.OrganizationRepository;
import weShare.sharehappy.dto.post.DonationPostDetail;
import weShare.sharehappy.dto.post.DonationPostMakeRequest;
import weShare.sharehappy.dto.post.DonationPostSummary;
import weShare.sharehappy.dto.post.DonationPostSummaryRequest;
import weShare.sharehappy.entity.DonationPost;
import weShare.sharehappy.entity.DonationPostImage;
import weShare.sharehappy.entity.Organization;
import weShare.sharehappy.service.file.AwsS3FileManagementService;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DonationPostManager {

    private final DonationPostRepository postRepository;
    private final DonationPostCategoryRepository categoryRepository;
    private final OrganizationRepository organizationRepository;
    private final DonationPostImageRepository donationPostImageRepository;
    private final AwsS3FileManagementService awsS3FileManagementService;

    public List<DonationPostSummary> getDonationPosts(DonationPostSummaryRequest request){
        Optional.ofNullable(categoryRepository.findByCategoryName(request.getCategoryName()))
                .orElseThrow(()->new NoExistingDonationPostCategory());
        List<DonationPost> list = postRepository.findAllByCategoryNameWithOrganizationAndImages(
                request.getPostSortCriteria(), request.getPage(), PageSize.MAIN_PAGE_SIZE.getSize(),request.getCategoryName());
        if(list.size() == 0){
            throw new NoMoreDonationPostException();
        }
        return list.stream().map(post -> post.changeToLisInfo()).collect(Collectors.toList());
    }

    public DonationPostDetail getDonation(Long id){
        DonationPost donationPost = postRepository.findByIdWithOrganizationAndImages(id).orElseThrow(()->new NoExistingDonationPost());
        return donationPost.changeToDonationPostDetail(categoryRepository.findByCategoryName(donationPost.getCategoryName()).getKrName());
    }

    public Long coutDonationPost(){
        return postRepository.counDonationPost();
    }

    //트랜잭션 작업, database insert 이미지 s3
    /*
    -> insert post
    -> store aws s3 images : image/postid/file_name
    -> insert image
     */
    @Transactional
    public void makePost(DonationPostMakeRequest request,List<File> fileList,String email){
        //카테고리 존재 확인
        Optional.ofNullable(categoryRepository.findByCategoryName(request.getCategory()))
                .orElseThrow(()->new NoExistingDonationPostCategory());
        //이메일 존재 확인
        Organization organization = Optional.ofNullable(organizationRepository.findByEmail(email))
                .orElseThrow(()->new NoExistingUserException());
        //모금합 추가
        DonationPost donationPost = postRepository
                .save(new DonationPost(request.getTitle(),request.getContent(),request.getTargetAmount(),0L, LocalDate.now(),request.getEndDate(),request.getCategory(),organization));
        //aws s3에 저장
        List<String> imageUrlList = new ArrayList<>(); //content 이미지 url
        String thumbNailUrl = ""; // 썸네일 이미지 url
        //저장할 부모 path 지정
        String parentPath = getAwsS3ImageParentPath(donationPost.getId());
        try {
            //content안에 있는 파일 aws s3에 저장
            for(File file : fileList){
                //저장
                String imageUrl = awsS3FileManagementService.storeFile(FileUtils.openInputStream(file),parentPath,file.getName(),file.length());
                imageUrlList.add(imageUrl);
            }
            //content 기존 local url도 새 url로 바꾸어준다
            String updateContent = changeCotentUrl(fileList.stream().map(f->f.getName()).collect(Collectors.toList()),
                    imageUrlList,donationPost.getContent());
            donationPost.changeContent(updateContent);
            //썸네일 파일 aws s3에 저장
            MultipartFile thumbnail = request.getThumbnail();
            thumbNailUrl = awsS3FileManagementService.storeFile(thumbnail.getInputStream(),parentPath,thumbnail.getOriginalFilename(),thumbnail.getSize());
            //이미지 엔티티 생성
            List<DonationPostImage> donationPostImageList = imageUrlList.stream()
                    .map(url-> new DonationPostImage(url, LocalDateTime.now(),false,donationPost))
                    .collect(Collectors.toList());
            donationPostImageList.add(new DonationPostImage(thumbNailUrl,LocalDateTime.now(),true,donationPost));
            donationPostImageRepository.save(donationPostImageList);
        }catch (DataAccessException e){
            //rollback 작업 이미 저장된 파일은 삭제함
            awsS3FileManagementService.cleanDirectory(parentPath);
            List<String> fileNames = fileList.stream().map(f -> f.getName()).collect(Collectors.toList());
            fileNames.add(request.getThumbnail().getOriginalFilename());
            throw e;
        }catch (Exception e){
            //rollback 작업 이미 저장된 파일은 삭제함
            awsS3FileManagementService.cleanDirectory(parentPath);
            List<String> fileNames = fileList.stream().map(f -> f.getName()).collect(Collectors.toList());
            fileNames.add(request.getThumbnail().getOriginalFilename());
            throw new AwsS3StoreFilesException(fileNames);
        }
    }

    //content 안에 기존 local url를 새 url로 바꾸어준다
    private String changeCotentUrl(List<String> fileNames,List<String> imageUrlList,String content){
        String pattern = "\"(.*?)\"";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String matchedGroup = matcher.group(); // "로 둘러싸인 문자열 내용
            for(int i=0;i<fileNames.size();i++){
                if(matchedGroup.contains(fileNames.get(i))){
                    matcher.appendReplacement(buffer,"\""+imageUrlList.get(i)+"\"");
                }
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private String getAwsS3ImageParentPath(Long postId){
        return "image/"+postId;
    }

}
