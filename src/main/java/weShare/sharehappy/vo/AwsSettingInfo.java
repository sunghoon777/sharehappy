package weShare.sharehappy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AwsSettingInfo {
    private String s3url;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String clientRegion;
}
