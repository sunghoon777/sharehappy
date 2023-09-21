package weShare.sharehappy.service.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import weShare.sharehappy.Exception.AwsS3DeleteFilesException;
import weShare.sharehappy.vo.AwsSettingInfo;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AwsS3FileManagementService{

    private AwsSettingInfo awsSettingInfo;

    @PostConstruct
    public void init() throws IOException {
        Resource resource = new ClassPathResource("setting/aws.setting");
        ObjectMapper objectMapper = new ObjectMapper();
        awsSettingInfo = objectMapper.readValue(resource.getFile(), AwsSettingInfo.class);
    }


    public void deleteFile(String parentPath,String fileName) {
        AWSCredentials credentials = new BasicAWSCredentials(awsSettingInfo.getAccessKey(), awsSettingInfo.getSecretKey());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.valueOf(awsSettingInfo.getClientRegion())).build();
        String s3Key = parentPath+"/"+fileName;
        s3Client.deleteObject(awsSettingInfo.getBucketName(), s3Key);
    }


    public void cleanDirectory(String parentPath) {
        AWSCredentials credentials = new BasicAWSCredentials(awsSettingInfo.getAccessKey(), awsSettingInfo.getSecretKey());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.valueOf(awsSettingInfo.getClientRegion())).build();
        //parentPath 아래의 모든 파일 정보를 얻기
        List<S3ObjectSummary> objectSummaryList = new ArrayList<>();
        objectSummaryList.addAll(s3Client.listObjects(awsSettingInfo.getBucketName(),parentPath).getObjectSummaries());
        //삭제 작업
        Set<String> keySet = new HashSet<>(); //예외 처리용 s3key set
        List<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>(); //삭제할 s3key들을 담을 리스트
        //삭제할 파일의 s3Key를 set과 리스트에 담는다
        for(S3ObjectSummary s3ObjectSummary : objectSummaryList){
            String key = s3ObjectSummary.getKey();
            keyList.add(new DeleteObjectsRequest.KeyVersion(key));
            keySet.add(key);
        }
        //성공적으로 삭제한 object List 리턴
        List<DeleteObjectsResult.DeletedObject> deletedObjectList =  s3Client.deleteObjects(new DeleteObjectsRequest(awsSettingInfo.getBucketName()).withKeys(keyList)).getDeletedObjects();
        for(int i=0;i<deletedObjectList.size();i++){
            keySet.remove(deletedObjectList.get(i).getKey());
        }
        //만약 keySet이 0이 아니라면 삭제 실패한 파일 있다는 것, exception 객체에 해당 keySet을 저장한다.
        if(keySet.size() != 0){
            throw new AwsS3DeleteFilesException(keySet);
        }
    }

    public String storeFile(InputStream inputStream, String parentPath, String fileName, Long contentLength) {
        AWSCredentials credentials = new BasicAWSCredentials(awsSettingInfo.getAccessKey(), awsSettingInfo.getSecretKey());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.valueOf(awsSettingInfo.getClientRegion())).build();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        String s3Key = parentPath+"/"+fileName;
        s3Client.putObject(new PutObjectRequest(awsSettingInfo.getBucketName(),s3Key,inputStream,metadata));
        return awsSettingInfo.getS3url()+"/"+s3Key;
    }
}
